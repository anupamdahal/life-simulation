package simulator.entity;

// store the static properties common to all grazers
public class GrazerConfig {
    private static GrazerConfig theInstance = new GrazerConfig();

    private int energyOutputRate, energyInputRate, energyToReproduce;
    private double maintainSpeedTime, maxSpeed;

    public GrazerConfig getInstance() {
        return theInstance;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getEnergy(){
        return energy;
    }

    public double getMaintainSpeedTime(){
        return maintainSpeedTime;
    }

    public double getMaxSpeed(){
        return maxSpeed;
    }

    public int getEnergyInputRate(){
        return energyInputRate;
    }

    public int getEnergyOutputRate(){
        return energyOutputRate;
    }

    public int getEnergyToReproduce(){
        return energyToReproduce;
    } 
}
