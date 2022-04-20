package simulator.entity;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.util.Pair;
import java.util.Random;

public abstract class Animal extends Entity {
    private GrazerConfig grazerConfig;
    private PredatorConfig predatorConfig;
    private static Random rand = new Random();

    public int energy;
    public double maintainSpeedTime;
    public Boolean flee;
    public String genotype;

    public boolean moveTowards(int desiredX, int desiredY, Boolean flee) {
        // actual point the animal will attempt to move to considering its speed
        int x = 0, y = 0;
        // amount of energy the move will take
        int energyDrain = 0;
        // speed of the animal
        double speed = 0;
        // get the speed of the move and energy drain
        if (this.type == EntityType.GRAZER) {
            grazerConfig = grazerConfig.getInstance();
            if (energy < 25) {
                // slowed because of weakness
                speed = 10.0;
            }
            else if ((flee == true) && (maintainSpeedTime > 0)){
                speed = grazerConfig.getMaxSpeed();
                maintainSpeedTime =- 1;
            }
            else {
                speed = 0.75 * grazerConfig.getMaxSpeed();
            }
            energyDrain = (int)(speed * (double)grazerConfig.getEnergyOutputRate() / 5.0);
            flee = false;
        }
        // get the speed of the move and energy drain
        else if (this.type == EntityType.PREDATOR) {
            predatorConfig = predatorConfig.getInstance();
            if (this.genotype.contains("FF")) {
                speed = predatorConfig.getMaxSpeedHOD();
            }
            if (this.genotype.contains("Ff")) {
                speed = predatorConfig.getMaxSpeedHED();
            }
            if (this.genotype.contains("ff")) {
                speed = predatorConfig.getMaxSpeedHOR();
            }
            energyDrain = (int)(speed * (double)predatorConfig.getEnergyOutputRate() / 5.0);
        }
        else {
            return false; // you're trying to move something that can't move somehow
        }
        // calculate the actual point the animal will move to and the energy drain
        double distanceToDesiredPoint = Math.sqrt(Math.pow(desiredX, 2) + Math.pow(desiredY, 2));
        if (distanceToDesiredPoint > (double)speed) {
            // get the unit vector in the direction of the move and scale it by the speed
            double dirX = (double)(desiredX - this.x) / distanceToDesiredPoint;
            double dirY = (double)(desiredY - this.y) / distanceToDesiredPoint;
            x = this.x + (int)(dirX * speed);
            y = this.y + (int)(dirY * speed);
        }
        else {
            // scale the energy drain according to the distance moved
            energyDrain = (int)(energyDrain * distanceToDesiredPoint / speed);
        }
        // is the point in bounds?
        if (map.isPointInBounds(x, y)) {
            // is the path blocked by an obstacle?
            ArrayList<Pair<Double, Entity>> rocks = map.search(x, y, EntityType.OBSTACLE, 10);
            if (!rocks.isEmpty()) {
                Iterator<Pair<Double, Entity>> iter = rocks.iterator();
                while (iter.hasNext()) {
                    Obstacle rock = (Obstacle)iter.next().getValue();
                    if (rock.isBlocking(x, y)) {
                        // rock is blocking the path
                        return false;
                    }
                }
            }
            // make the move
            this.energy -= energyDrain;
            this.x = x;
            this.y = y;
            return true;
        }
        // point is out of bounds
        return false;
    }

    // move in a random direction
    public boolean moveRand() {
        // generate a vector with a uniformly random angle and large radius
        int newX = 0;
        int newY = 0;
        double angle = 2.0 * Math.PI * rand.nextDouble();
        double radius = 500.0 * rand.nextDouble();
        newX = (int)(this.x + radius * Math.cos(angle));
        newY = (int)(this.y + radius * Math.sin(angle));
        boolean retVal = false;
        // create a retry counter, so the program doesn't hang if something is
        // stuck somewhere it can't get out of somehow
        int i = 0;
        do {
            retVal = moveTowards(newX, newY, flee);
            i++;
        } while (retVal == false || i > 15);
        return retVal;
    }

    public boolean moveTowards(Entity target) {
        return moveTowards(target.getX(), target.getY(), flee);
    }

    // TODO: chase at max speed
    public boolean chase(Entity target) {
        return moveTowards(target);
    }

    public boolean fleeFrom(Entity target) {
        int newX = 0;
        int newY = 0;
        Boolean flee = true;
        newX = this.x - (target.x - this.x);
        newY = this.y - (target.y - this.y);
        return moveTowards(newX, newY, flee);
    }
}
