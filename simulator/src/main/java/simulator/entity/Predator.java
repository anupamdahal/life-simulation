package simulator.entity;

public class Predator extends Entity {
    private int x, y, energy;
    private String genotype;
    private double maxSpeedHOD, maxSpeedHED, maxSpeedHOR, maintainSpeedTime;
    private int energyOutputRate, energyToReproduce;
    private int maxOffspring, offspringEnergyLevel;
    private double gestationPeriod;

    public Predator(int x, int y, int energy, String genotype,
        double maxSpeedHOD, double maxSpeedHED, double maxSpeedHOR,
        double maintainSpeedTime, int energyOutputRate, int energyToReproduce,
        int maxOffspring, int offspringEnergyLevel, double gestationPeriod)
    {
        this.x = x;
        this.y = y;
        this.energy = energy;
        this.genotype = genotype;
        this.maxSpeedHOD = maxSpeedHOD;
        this.maxSpeedHED = maxSpeedHED;
        this.maxSpeedHOR = maxSpeedHOR;
        this.maintainSpeedTime = maintainSpeedTime;
        this.energyOutputRate = energyOutputRate;
        this.energyToReproduce = energyToReproduce;
        this.maxOffspring = maxOffspring;
        this.offspringEnergyLevel = offspringEnergyLevel;
        this.gestationPeriod = gestationPeriod;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
