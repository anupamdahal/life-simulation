package simulator.entity;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.util.Pair;

public class Grazer extends Entity {
    private int energy, energyInputRate, energyOutputRate, energyToReproduce;
    private double maintainSpeedTime, maxSpeed;

    public Grazer(int x, int y, int energy, double maintainSpeedTime, double maxSpeed,
        int energyInputRate, int energyOutputRate, int energyToReproduce)
    {
        this.x = x;
        this.y = y;
        this.energy = energy;
        this.maintainSpeedTime = maintainSpeedTime;
        this.maxSpeed = maxSpeed;
        this.energyInputRate = energyInputRate;
        this.energyOutputRate = energyOutputRate;
        this.energyToReproduce = energyToReproduce;
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
        if (energy >= energyToReproduce) {
            // half the energy
            energy /= 2;
            // add a new grazer
            // TODO: the new grazer should be spawned some small distance away from the original
            // TODO: need to add boundary checking in order for that to happen
            map.entities.add(new Grazer(x, y, energy, maintainSpeedTime, maxSpeed,
                energyInputRate, energyOutputRate, energyToReproduce));
        }
        return true;
    }
}
