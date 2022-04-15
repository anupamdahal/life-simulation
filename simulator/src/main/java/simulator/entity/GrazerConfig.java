package simulator.entity;

// store the static properties common to all grazers
public class GrazerConfig {
    private static GrazerConfig theInstance = new GrazerConfig();

    private int energyOutputRate, energyInputRate, energyToReproduce;
    private double maintainSpeedTime, maxSpeed;

    public static GrazerConfig getInstance() {
        return theInstance;
    }

    public double getMaintainSpeedTime(){
        return maintainSpeedTime;
    }

    public void setMaintainSpeedTime(double maintainSpeedTime){
        this.maintainSpeedTime = maintainSpeedTime;
    }

    public double getMaxSpeed(){
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed){
        this.maxSpeed = maxSpeed;
    }

    public int getEnergyInputRate(){
        return energyInputRate;
    }

    public void setEnergyInputRate(int energyInputRate){
        this.energyInputRate = energyInputRate;
    }

    public int getEnergyOutputRate(){
        return energyOutputRate;
    }

    public void setEnergyOutputRate(int energyOutputRate){
        this.energyOutputRate = energyOutputRate;
    }

    public int getEnergyToReproduce(){
        return energyToReproduce;
    }

    public void setEnergyToReproduce(int energyToReproduce){
        this.energyToReproduce = energyToReproduce;
    }
}
