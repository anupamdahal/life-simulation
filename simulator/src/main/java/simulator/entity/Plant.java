package simulator.entity;

import simulator.Map;
import java.util.Random;

public class Plant extends Entity {
    private int age = 0; // in seconds
    private int x, y, diameter, maxSize, castDistance, maxSeedNumber;
    private double growthRate, seedViability;

    static Random rand = new Random();
    
    public Plant(int x, int y, int diameter, int maxSize, int castDistance,
        int maxSeedNumber, double growthRate, double seedViability)
    {
        map = map.getInstance();
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.maxSize = maxSize;
        this.castDistance = castDistance;
        this.maxSeedNumber = maxSeedNumber;
        this.growthRate = growthRate;
        this.seedViability = seedViability;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDiameter() {
        return diameter;
    }

    @Override
    public boolean update() {
        age += 1;
        if (diameter < maxSize && age % 10 == 0) {
            // grow
            diameter = Math.max(diameter + (int)(growthRate), diameter);
        }
        if (diameter >= maxSize && age % 3600 == 0) {
            // reset age counter
            age = 0;
            // produce a random number of seeds between 1 and
            // maxSeedNumber
            int numSeeds = rand.nextInt((maxSeedNumber - 1) + 1) + 1;
            for (int i=0; i < numSeeds; i++) {
                // determine whether or not this seed germinates
                if (rand.nextDouble() < seedViability) {
                    // this seed doesn't germinate
                    continue;
                }
                double angle = 2.0 * Math.PI * rand.nextDouble();
                double radius = castDistance * rand.nextDouble();
                int newX = (int)(x + radius * Math.cos(angle));
                int newY = (int)(y + radius * Math.sin(angle));
                map.entities.add(new Plant(newX, newY, 1, maxSize, castDistance,
                maxSeedNumber, growthRate, seedViability));
            }
        }
        return true;
    }
}
