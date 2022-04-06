package simulator.entity;

// store the static properties common to all predators
public class PredatorConfig {
    private static PredatorConfig theInstance = new PredatorConfig();

    private int maxOffspring, offspringEnergyLevel, energyToReproduce;
    private double gestationPeriod, maxSpeedHOD, maxSpeedHED, maxSpeedHOR, maintainSpeedTime;

    public PredatorConfig getInstance() {
        return theInstance;
    }
}
