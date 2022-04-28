package simulator.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.EnumSet;
import java.util.Random;

import simulator.Pair;

public class Grazer extends Animal {
    private GrazerConfig grazerConfig;

    static Random rand = new Random();

    private int secondsSpentEatingFood;

    public Grazer(int x, int y, int energy)
    {
        grazerConfig = grazerConfig.getInstance();
        this.x = x;
        this.y = y;
        this.energy = energy;
        type = EntityType.GRAZER;
    }

    // implement all grazer behaviors in here
    // return false if the grazer starved to death
    @Override
    public boolean update() {
        if (energy <= 0) {
            // starved to death
            map.entities.remove(this);
            return false;
        }

        // check for predators
        ArrayList<Pair<Double, Entity>> nearbyPredators = map.search(this, EnumSet.of(EntityType.PREDATOR), 10); // lowered search range to 10
        if (!nearbyPredators.isEmpty()) {
            //flee
            Entity nearestPredator = nearbyPredators.get(0).getValue();
            double smallestDistance = Double.MAX_VALUE;
            Iterator<Pair<Double, Entity>> iter = nearbyPredators.iterator();
            while (iter.hasNext()) {
                Pair<Double, Entity> predatorPair = iter.next();
                double distance = predatorPair.getKey();
                if (distance < smallestDistance) {
                    smallestDistance = distance;
                    nearestPredator = predatorPair.getValue();
                }
            }
            if (!this.fleeFrom(nearestPredator)) {
                // we've hit an obstacle, move in a random direction to avoid it
                this.moveRand();
            }
            return true;
        }
        
        // TODO: check for food
        ArrayList<Pair<Double, Entity>> nearbyPlants = map.search(this, EnumSet.of(EntityType.PLANT), 25);
        if (!nearbyPlants.isEmpty()) {
            Entity nearestPlant = nearbyPlants.get(0).getValue();
            double smallestDistance = Double.MAX_VALUE;
            Iterator<Pair<Double, Entity>> iter = nearbyPlants.iterator();
            while (iter.hasNext()) {
                Pair<Double, Entity> plantPair = iter.next();
                double distance = plantPair.getKey();
                Entity plant = plantPair.getValue();
                if (distance < smallestDistance) {
                    smallestDistance = distance;
                    nearestPlant = plant;
                }
            }
            // is the grazer within eating range of the nearest plant?
            if (smallestDistance <= 1) {
                secondsSpentEatingFood++;
                if (secondsSpentEatingFood % 60 == 0) {
                    energy += grazerConfig.getEnergyInputRate();
                }
                if (secondsSpentEatingFood % 300 == 0) {
                    // ate all the food
                    map.entities.remove(nearestPlant);
                    secondsSpentEatingFood = 0;
                }
                return true;
            }
            else {
                if (!this.moveTowards(nearestPlant)) {
                    this.moveRand();
                }
            }
        }
        else {
            // move in a random direction to find a plant
            this.moveRand();
            return true;
        }
        
        // reproduce
        if (energy >= grazerConfig.getEnergyToReproduce()) {
            // half the energy
            energy /= 2;
            // add a new grazer in a random direction 5 DU away
            int newX = 0;
            int newY = 0;
            do {
                double angle = 2.0 * Math.PI * rand.nextDouble();
                double radius = 5.0 * rand.nextDouble();
                newX = (int)(x + radius * Math.cos(angle));
                newY = (int)(y + radius * Math.sin(angle));
            } while (!map.isPointInBounds(newX, newY));

            map.entities.add(new Grazer(newX, newY, energy));
        }
        this.moveRand();
        return true;
    }
}
