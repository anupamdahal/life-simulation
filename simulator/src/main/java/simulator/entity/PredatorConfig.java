package simulator.entity;

// store the static properties common to all predators
public class PredatorConfig {
    private static PredatorConfig theInstance = new PredatorConfig();

    private int energy, maxOffspring, offspringEnergyLevel, energyToReproduce, energyOutputRate;
    private double gestationPeriod, maxSpeedHOD, maxSpeedHED, maxSpeedHOR, maintainSpeedTime;
    private String genotype;

    public PredatorConfig getInstance() {
        return theInstance;
    }

    public int getEnergy(){
        return energy;
    }

    public void getEnergy(int energy){
        this.energy = energy;
    }

    public String getGenotype(){
        return genotype;
    }

    public void getGenotype(String genotype){
        this.genotype = genotype;
    }

    public double getMaxSpeedHOD(){
        return maxSpeedHOD;
    }

    public void getMaxSpeedHOD(double maxSpeedHOD){
        this.maxSpeedHOD = maxSpeedHOD;
    }

    public double getMaxSpeedHED(){
        return maxSpeedHED;
    }

    public void getMaxSpeedHED(double maxSpeedHED){
        this.maxSpeedHED = maxSpeedHED;
    }

    public double getMaxSpeedHOR(){
        return maxSpeedHOR;
    }

    public void getMaxSpeedHOR(double maxSpeedHOR){
        this.maxSpeedHOR = maxSpeedHOR;
    }

    public double getMaintainSpeedTime(){
        return maintainSpeedTime;
    }

    public void getMaintainSpeedTime(double maintainSpeedTime){
        this.maintainSpeedTime = maintainSpeedTime;
    }

    public int getEnergyOutputRate(){
        return energyOutputRate;
    }

    public void getEnergyOutputRate(int energyOutputRate){
        this.energyOutputRate = energyOutputRate;
    }

    public int getEnergyToReproduce(){
        return energyToReproduce;
    }

    public void getEnergyToReproduce(int energyToReproduce){
        this.energyToReproduce = energyToReproduce;
    }

    public int getMaxOffspring(){
        return maxOffspring;
    }

    public void getMaxOffspring(int maxOffspring){
        this.maxOffspring = maxOffspring;
    }

    public int getOffspringEnergyLevel(){
        return offspringEnergyLevel;
    }

    public void getOffspringEnergyLevel(int offspringEnergyLevel){
        this.offspringEnergyLevel = offspringEnergyLevel;
    }

    public double getGestationPeriod(){
        return gestationPeriod;
    }

    public void getGestationPeriod(int gestationPeriod){
        this.gestationPeriod = gestationPeriod;
    }
}
