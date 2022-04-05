package simulator.entity;

public class Plant extends Entity {
    private int x, y, diameter, maxSize, castDistance, maxSeedNumber;
    private double growthRate, seedViability;
    
    public Plant(int x, int y, int diameter, int maxSize, int castDistance,
        int maxSeedNumber, double growthRate, double seedViability)
    {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.maxSize = maxSize;
        this.castDistance = castDistance;
        this.maxSeedNumber = maxSeedNumber;
        this.growthRate = growthRate;
        this.seedViability = seedViability;
    }

}
