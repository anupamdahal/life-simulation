package simulator.entity;

// store the static properties common to all plants
public class PlantConfig {
    private static PlantConfig theInstance = new PlantConfig();

    private int maxSize, castDistance, maxSeedNumber;
    private double growthRate, seedViability;

    public PlantConfig getInstance() {
        return theInstance;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMaxSeedCastDistance() {
        return castDistance;
    }

    public void setMaxSeedCastDistance(int maxSeedCastDistance) {
        this.castDistance = maxSeedCastDistance;
    }

    public int getMaxSeedNumber() {
        return maxSeedNumber;
    }

    public void setMaxSeedNumber(int maxSeedNumber) {
        this.maxSeedNumber = maxSeedNumber;
    }

    public double getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(double growthRate) {
        this.growthRate = growthRate;
    }

    public double getSeedViability() {
        return seedViability;
    }

    public void setSeedViability(double seedViability) {
        this.seedViability = seedViability;
    }
}
