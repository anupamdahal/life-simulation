package simulator.entity;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.util.Pair;
import java.util.Random;

public class Predator extends Animal {
    private PredatorConfig predatorConfig;
    private int x, y;
    public Predator attacker;

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
        if (energy <= 0) {
            // starved to death
            map.entities.remove(this);
            return false;
        }

        // check for predators
        ArrayList<Pair<Double, Entity>> nearbyPredators = map.search(this, EntityType.PREDATOR, 150);
        if (!nearbyPredators.isEmpty()) {
            Predator nearestPredator = (Predator)nearbyPredators.get(0).getValue();
            double smallestDistance = Double.MAX_VALUE;
            Iterator<Pair<Double, Entity>> iter = nearbyPredators.iterator();
            while (iter.hasNext()) {
                Pair<Double, Entity> predatorPair = iter.next();
                double distance = predatorPair.getKey();
                if (distance < smallestDistance) {
                    smallestDistance = distance;
                    nearestPredator = (Predator)predatorPair.getValue();
                }
            }
            // if the predator is HOD aggressive, attempt to kill the nearest predator
            // unless this predator is seeking a mate
            if (this.genotype.contains("AA")) {
                notifyAttacking(nearestPredator);
                if (!this.chase(nearestPredator)) {
                    this.moveRand();
                }
                // if we caught them, attack
                if (smallestDistance < 5.0) {
                    return combat(nearestPredator);
                }
                // if we didn't, we moved for this turn anyway
                return true;
            }
        }
        return true;
    }

    private boolean combat(Predator target) {
        // homozygous dominant
        if (this.genotype.contains("SS")) {
            if (target.genotype.contains("SS")) {
                if (rand.nextInt(2) == 0) {
                    // the predators fight with even odds
                    if (rand.nextInt(2) == 0) {
                        // this predator won the combat
                        energy += (int)(0.9 * target.energy);
                        map.entities.remove(target);
                        return true;
                    }
                    else {
                        // the other guy won
                        target.energy += (int)(0.9 * energy);
                        map.entities.remove(this);
                        return false;
                    }
                }
                else {
                    // the predators disengage
                }
            }
            else if (target.genotype.contains("Ss")) {
                // eat the other guy 75% of the time
                if (rand.nextInt(4) == 3) {
                    // the other guy won
                    target.energy += (int)(0.9 * energy);
                    map.entities.remove(this);
                    return false;
                }
                else {
                    // this predator won
                    energy += (int)(0.9 * target.energy);
                    map.entities.remove(target);
                    return true;
                }
            }
            else if (target.genotype.contains("ss")) {
                // eat the other guy 95% of the time
                if (rand.nextInt(20) == 19) {
                    // the other guy won
                    target.energy += (int)(0.9 * energy);
                    map.entities.remove(this);
                    return false;
                }
                else {
                    // this predator won
                    energy += (int)(0.9 * target.energy);
                    map.entities.remove(target);
                    return true;
                }
            }
        }
        // heterozygous dominant
        else if (this.genotype.contains("Ss")) {
            if (target.genotype.contains("Ss")) {
                if (rand.nextInt(2) == 0) {
                    // the predators fight with even odds
                    if (rand.nextInt(2) == 0) {
                        // this predator won the combat
                        energy += (int)(0.9 * target.energy);
                        map.entities.remove(target);
                        return true;
                    }
                    else {
                        // the other guy won
                        target.energy += (int)(0.9 * energy);
                        map.entities.remove(this);
                        return false;
                    }
                }
                else {
                    // the predators disengage
                }
            }
            else if (target.genotype.contains("SS")) {
                // lose 75% of the time
                if (rand.nextInt(4) == 3) {
                    // this predator won
                    energy += (int)(0.9 * target.energy);
                    map.entities.remove(target);
                    return false;
                }
                else {
                    // the other guy won
                    target.energy += (int)(0.9 * energy);
                    map.entities.remove(this);
                    return true;
                }
            }
            else if (target.genotype.contains("ss")) {
                // eat the other guy 75% of the time
                if (rand.nextInt(4) == 3) {
                    // the other guy won
                    target.energy += (int)(0.9 * energy);
                    map.entities.remove(this);
                    return false;
                }
                else {
                    // this predator won
                    energy += (int)(0.9 * target.energy);
                    map.entities.remove(target);
                    return true;
                }
            }            
        }
        // homozygous recessive
        else if (this.genotype.contains("ss")) {
            if (target.genotype.contains("ss")) {
                if (rand.nextInt(2) == 0) {
                    // the predators fight with even odds
                    if (rand.nextInt(2) == 0) {
                        // this predator won the combat
                        energy += (int)(0.9 * target.energy);
                        map.entities.remove(target);
                        return true;
                    }
                    else {
                        // the other guy won
                        target.energy += (int)(0.9 * energy);
                        map.entities.remove(this);
                        return false;
                    }
                }
                else {
                    // the predators disengage
                }
            }
            else if (target.genotype.contains("SS")) {
                // lose 95% of the time
                if (rand.nextInt(20) != 19) {
                    // the other guy won
                    target.energy += (int)(0.9 * energy);
                    map.entities.remove(this);
                    return false;
                }
                else {
                    // this predator won
                    energy += (int)(0.9 * target.energy);
                    map.entities.remove(target);
                    return true;
                }
            }
            else if (target.genotype.contains("Ss")) {
                // lose 75% of the time
                if (rand.nextInt(4) != 3) {
                    // the other guy won
                    target.energy += (int)(0.9 * energy);
                    map.entities.remove(this);
                    return false;
                }
                else {
                    // this predator won
                    energy += (int)(0.9 * target.energy);
                    map.entities.remove(target);
                    return true;
                }
            }            
        }
        // this line of code should never be reached
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

    private void notifyAttacking(Predator target) {
        target.attacker = this;
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
