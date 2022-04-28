package simulator.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.EnumSet;
import java.util.Random;

import simulator.Pair;

public class Predator extends Animal {
    private PredatorConfig predatorConfig;
    public Predator attacker;
    private int gestationTimeLeft;

    private static Random rand = new Random();

    public Predator(int x, int y, int energy, String genotype)
    {
        predatorConfig = predatorConfig.getInstance();
        this.x = x;
        this.y = y;
        this.energy = energy;
        this.genotype = genotype;
        gestationTimeLeft = 0;
        type = EntityType.PREDATOR;

    }

    @Override
    public boolean update() {
        if (energy <= 0) {
            // starved to death
            map.entities.remove(this);
            return false;
        }

        // check for grazers and predators
        ArrayList<Pair<Double, Entity>> nearbyTargets = map.search(this, EnumSet.of(EntityType.PREDATOR, EntityType.GRAZER), 15); // lowered search range to 15
        if (!nearbyTargets.isEmpty()) {
            // filter into two lists of predators and grazers
            ArrayList<Pair<Double, Predator>> nearbyPredators = new ArrayList<Pair<Double, Predator>>();
            ArrayList<Pair<Double, Grazer>> nearbyGrazers = new ArrayList<Pair<Double, Grazer>>();
            Predator nearestPredator = new Predator(0, 0, 0, ""); // dummy predator
            double smallestPredatorDistance = Double.MAX_VALUE;
            Grazer nearestGrazer = new Grazer(0, 0, 0); // dummy grazer
            double smallestGrazerDistance = Double.MAX_VALUE;
            Entity nearestEntity = nearbyTargets.get(0).getValue();
            double smallestEntityDistance = Double.MAX_VALUE;
            Iterator<Pair<Double, Entity>> filterIter = nearbyTargets.iterator();
            while (filterIter.hasNext()) {
                Pair<Double, Entity> entityPair = filterIter.next();
                double distance = entityPair.getKey();
                if (distance < smallestEntityDistance) {
                    smallestEntityDistance = distance;
                    nearestEntity = entityPair.getValue();
                }
                if (entityPair.getValue().type == EntityType.PREDATOR) {
                    nearbyPredators.add(new Pair<Double, Predator>(distance, (Predator)entityPair.getValue()));
                    if (distance == smallestEntityDistance) {
                        nearestPredator = (Predator)entityPair.getValue();
                        smallestPredatorDistance = distance;
                    }
                }
                if (entityPair.getValue().type == EntityType.GRAZER) {
                    nearbyGrazers.add(new Pair<Double, Grazer>(distance, (Grazer)entityPair.getValue()));
                    if (distance == smallestEntityDistance) {
                        nearestGrazer = (Grazer)entityPair.getValue();
                        smallestGrazerDistance = distance;
                    }
                }
            }
            // attempt to reproduce
            if (!nearbyPredators.isEmpty() && energy >= predatorConfig.getEnergyToReproduce() && smallestPredatorDistance < Double.MAX_VALUE) {
                if (!this.moveTowards(nearestPredator)) {
                    this.moveRand();
                }
                if (smallestPredatorDistance <= 1) {
                    this.mateWith(nearestPredator);
                }
                return true;
            }
            // else attempt to find food
            if (!nearbyGrazers.isEmpty() && smallestGrazerDistance < Double.MAX_VALUE) {
                if (!this.chase(nearestGrazer)) {
                    this.moveRand();
                }
                if (smallestGrazerDistance < 2.0) {
                    return combat(nearestGrazer);
                }
                return true;
            }
            // else react to the nearest predator
            if (!nearbyPredators.isEmpty() && (this.genotype.contains("AA") || (this.genotype.contains("Aa") && energy < 30 && smallestPredatorDistance < Double.MAX_VALUE))) {
                notifyAttacking(nearestPredator);
                if (!this.chase(nearestPredator)) {
                    this.moveRand();
                }
                // if we caught them, attack
                if (smallestPredatorDistance < 2.0) {
                    return combat(nearestPredator);
                }
                // if we didn't, we moved for this turn anyway
                return true;
            }
            if (!nearbyPredators.isEmpty() && this.genotype.contains("aa") && smallestPredatorDistance < Double.MAX_VALUE) {
                if (!this.fleeFrom(nearestPredator)) {
                    this.moveRand();
                }
                return true;
            }
        }
        this.moveRand();
        return true;
    }

    private boolean combat(Grazer target) {
        // homozygous dominant
        if (this.genotype.contains("SS")) {
            // succeeds in killing and eating grazers 95% of the time
            if (rand.nextInt(20) == 19) {
                return false;
            }
            else {
                energy += (int)(0.9 * target.energy);
                map.entities.remove((Entity) target);
                return true;
            }
        }
        // heterozygous dominant
        else if (this.genotype.contains("Ss")) {
            // succeeds in killing and eating grazers 75% of the time
            if (rand.nextInt(4) == 3) {
                return false;
            }
            else {
                energy += (int)(0.9 * target.energy);
                map.entities.remove((Entity) target);
                return true;
            }
        }
        // homozygous recessive
        else if (this.genotype.contains("ss")) {
            // succeeds in killing and eating grazers 50% of the time
            if (rand.nextInt(2) == 1) {
                return false;
            }
            else {
                energy += (int)(0.9 * target.energy);
                map.entities.remove((Entity) target);
                return true;
            } 
        }
        // this line of code should never be reached
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
                        map.entities.remove((Entity) target);
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
                    map.entities.remove((Entity) target);
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
                    map.entities.remove((Entity) target);
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
                        map.entities.remove((Entity) target);
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
                    map.entities.remove((Entity) target);
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
                    map.entities.remove((Entity) target);
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
                        map.entities.remove((Entity) target);
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
                    map.entities.remove((Entity) target);
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
                    map.entities.remove((Entity) target);
                    return true;
                }
            }            
        }
        // this line of code should never be reached
        return true;
    }

    public void mateWith(Predator mate) {
        //mate.mateWith(this);
        int numOffspring = rand.nextInt(predatorConfig.getMaxOffspring() + 1);
        for (int i = 0; i < numOffspring; i++) {
            // TODO: gestation period
            // TODO: birth offspring small distance away
            // TODO: offspring and parent move away and do not interact for an hour
            map.entities.add(new Predator(x, y, predatorConfig.getOffspringEnergyLevel(), mixGenes(mate)));
        }
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
