package simulator.entity;

import java.util.Random;

public class Predator extends Animal {
    private PredatorConfig predatorConfig;
    private int x, y;

    private static Random rand = new Random();

    public Predator(int x, int y, int energy, String genotype)
    {
        predatorConfig = predatorConfig.getInstance();
        this.x = x;
        this.y = y;
        this.energy = energy;
        this.genotype = genotype;
        type = EntityType.PREDATOR;
    }

    @Override
    public boolean update() {

        return true;
    }

    private String mixGenes(Predator partner) {
        String offspringGenes = "";
        // randomly select either allele of each gene from each parent
        for (int i = 0; i < 7; i+=3) {
            offspringGenes += this.genotype.charAt(i + rand.nextInt(2));
            offspringGenes += partner.genotype.charAt(i + rand.nextInt(2));
            offspringGenes += " ";
        }
        // reverse aA sS fF cases
        offspringGenes.replace("aA", "Aa");
        offspringGenes.replace("sS", "Ss");
        offspringGenes.replace("fF", "Ff");

        return offspringGenes;
    }

    public String getGenotype() {
        return genotype;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
