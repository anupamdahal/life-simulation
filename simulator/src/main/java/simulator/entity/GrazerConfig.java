package simulator.entity;

// store the static properties common to all grazers
public class GrazerConfig {
    private static GrazerConfig theInstance = new GrazerConfig();

    private int energyOutputRate, energyInputRate, energyToReproduce;
    private double maintainSpeedTime, maxSpeed;

    public GrazerConfig getInstance() {
        return theInstance;
    }
}
