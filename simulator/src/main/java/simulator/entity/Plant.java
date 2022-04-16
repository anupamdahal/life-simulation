package simulator.entity;

import java.util.Random;

public class Plant extends Entity {
    private PlantConfig plantConfig;
    private int age = 0; // in seconds
    private int x, y, diameter;

    static Random rand = new Random();
    
    public Plant(int x, int y, int diameter)
    {
        map = map.getInstance();
        plantConfig = plantConfig.getInstance();
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        type = EntityType.PLANT;
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
        if (diameter < plantConfig.getMaxSize() && age % 10 == 0) {
            // grow
            diameter = Math.max(diameter + (int)(plantConfig.getGrowthRate()), diameter);
        }
        if (diameter >= plantConfig.getMaxSize() && age % 3600 == 0) {
            // reset age counter
            age = 0;
            // produce a random number of seeds between 1 and
            // maxSeedNumber
            int numSeeds = rand.nextInt((plantConfig.getMaxSeedNumber() - 1) + 1) + 1;
            for (int i=0; i < numSeeds; i++) {
                // determine whether or not this seed germinates
                if (rand.nextDouble() < plantConfig.getSeedViability()) {
                    // this seed doesn't germinate
                    continue;
                }
                int newX = 0;
                int newY = 0;
                // throw the seed to a random spot inside the cast distance
                // retry until it lands inside the map bounds
                int j = 0;
                do {
                    double angle = 2.0 * Math.PI * rand.nextDouble();
                    double radius = plantConfig.getMaxSeedCastDistance() * rand.nextDouble();
                    newX = (int)(x + radius * Math.cos(angle));
                    newY = (int)(y + radius * Math.sin(angle));
                    j++;
                } while (!map.isPointInBounds(newX, newY) && j < 10);

                map.entities.add(new Plant(newX, newY, 1));
            }
        }
        return true;
    }
}
