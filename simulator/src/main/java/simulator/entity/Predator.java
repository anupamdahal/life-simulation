package simulator.entity;

public class Predator extends Animal {
    private PredatorConfig predatorConfig;
    private int x, y;
    private String genotype;

    public Predator(int x, int y, int energy, String genotype)
    {
        predatorConfig = predatorConfig.getInstance();
        this.x = x;
        this.y = y;
        this.energy = energy;
        this.genotype = genotype;
        type = EntityType.PREDATOR;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
