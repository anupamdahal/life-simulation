package simulator.entity;

// store the static properties common to all predators
public class PredatorConfig {
    private static PredatorConfig theInstance = new PredatorConfig();

    private int maxOffspring, offspringEnergyLevel, energyToReproduce, energyOutputRate;
    private double gestationPeriod, maxSpeedHOD, maxSpeedHED, maxSpeedHOR, maintainSpeedTime;
    private String genotype;

    public static PredatorConfig getInstance() {
        return theInstance;
    }

    public String getGenotype(){
        return genotype;
    }

    public void setGenotype(String genotype){
        this.genotype = genotype;
    }

    public double getMaxSpeedHOD(){
        return maxSpeedHOD;
    }

    public void setMaxSpeedHOD(double maxSpeedHOD){
        this.maxSpeedHOD = maxSpeedHOD;
    }

    public double getMaxSpeedHED(){
        return maxSpeedHED;
    }

    public void setMaxSpeedHED(double maxSpeedHED){
        this.maxSpeedHED = maxSpeedHED;
    }

    public double getMaxSpeedHOR(){
        return maxSpeedHOR;
    }

    public void setMaxSpeedHOR(double maxSpeedHOR){
        this.maxSpeedHOR = maxSpeedHOR;
    }

    public double getMaintainSpeedTime(){
        return maintainSpeedTime;
    }

    public void setMaintainSpeedTime(double maintainSpeedTime){
        this.maintainSpeedTime = maintainSpeedTime;
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

    public int getMaxOffspring(){
        return maxOffspring;
    }

    public void setMaxOffspring(int maxOffspring){
        this.maxOffspring = maxOffspring;
    }

    public int getOffspringEnergyLevel(){
        return offspringEnergyLevel;
    }

    public void setOffspringEnergyLevel(int offspringEnergyLevel){
        this.offspringEnergyLevel = offspringEnergyLevel;
    }

    public double getGestationPeriod(){
        return gestationPeriod;
    }

    public void setGestationPeriod(int gestationPeriod){
        this.gestationPeriod = gestationPeriod;
    }
}
