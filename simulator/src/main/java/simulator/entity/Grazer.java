package simulator.entity;

public class Grazer extends Entity {
    private int x, y, energy, energyInputRate, energyOutputRate, energyToReproduce;
    private double maintainSpeedTime, maxSpeed;

    public Grazer(int x, int y, int energy, double maintainSpeedTime, double maxSpeed,
        int energyInputRate, int energyOutputRate, int energyToReproduce)
    {
        this.x = x;
        this.y = y;
        this.energy = energy;
        this.maintainSpeedTime = maintainSpeedTime;
        this.maxSpeed = maxSpeed;
        this.energyInputRate = energyInputRate;
        this.energyOutputRate = energyOutputRate;
        this.energyToReproduce = energyToReproduce;
    }
}
