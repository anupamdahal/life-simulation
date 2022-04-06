package simulator.entity;

// store the static properties common to all plants
public class PlantConfig {
    private static PlantConfig theInstance = new PlantConfig();

    private int maxSize, castDistance, maxSeedNumber;
    private double growthRate, seedViability;

    public PlantConfig getInstance() {
        return theInstance;
    }
}
