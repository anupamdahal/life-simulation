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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // implement all grazer behaviors in here
    // return false if the grazer starved to death
    @Override
    public boolean update() {
        if (energy <= 0) {
            // starved to death
            return false;
        }
        if (energy < 15) {
            // cannot move, but doesn't die yet
            return true;
        }
        // TODO: check for predators
        // TODO: check for food
        return true;
    }
}
