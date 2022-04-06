package simulator.entity;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.util.Pair;
import java.util.Random;

public class Grazer extends Animal {
    private GrazerConfig grazerConfig;

    static Random rand = new Random();

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
        if (energy < 25) {
            // unable to move more than 10 DU because of weakness
            // TODO: movement
            return true;
        }
        // TODO: check for predators
        ArrayList<Pair<Double, Entity>> nearbyPredators = map.search(this, EntityType.PREDATOR, 25);
        if (!nearbyPredators.isEmpty()) {
            //flee
            Entity nearestPredator;
            double smallestDistance = Double.MAX_VALUE;
            Iterator<Pair<Double, Entity>> iter = nearbyPredators.iterator();
            while (iter.hasNext()) {
                Pair<Double, Entity> predatorPair = iter.next();
                double distance = predatorPair.getKey();
                Entity predator = predatorPair.getValue();
                if (distance < smallestDistance) {
                    smallestDistance = distance;
                    nearestPredator = predator;
                }
            }
            return true;
        }
        
        // TODO: check for food
        ArrayList<Pair<Double, Entity>> nearbyPlants = map.search(this, EntityType.PLANT, 25);
        if (!nearbyPlants.isEmpty()) {
            Entity nearestPlant;
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
            if (smallestDistance <= 5) {
                // eat
            }
            else {
                // move towards the nearest plant
            }
        }
        else {
            // move in a random direction to find a plant
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
        return true;
    }
}
