package simulator;

import java.util.*;
import simulator.entity.*;

public class Simulator{
  public ArrayList<Entity> entities;
  private int simulation_time;

  public Simulator(){}

  public void init(){
    // initialize simulation time to 0
    this.simulation_time = 0;

    // import the given data file to test the entity behaviors
    String DATAFILE = new String(System.getProperty("user.dir") + "/LifeSimulation01.xml");
    LifeSimDataParser lsdp = LifeSimDataParser.getInstance();	// Get the singleton
		lsdp.initDataParser(DATAFILE);

    // initialize the map
    System.out.println("World width = " + lsdp.getWorldWidth());
    System.out.println("World height= " + lsdp.getWorldHeight());

    // initialize obstacles
    int obstacleCount = lsdp.getObstacleCount();
    for (int i=0; i < obstacleCount; i++) {
      if(lsdp.getObstacleData())
      {
        entities.add(new Obstacle(lsdp.ObstacleX, lsdp.ObstacleY,
           lsdp.ObstacleDiameter, lsdp.ObstacleHeight));
      }
    }

    // initialize plants
    int plantCount = lsdp.getInitialPlantCount();
    for (int i=0; i < plantCount; i++) {
      if(lsdp.getPlantData())
      {
        entities.add(new Plant(lsdp.PlantX, lsdp.PlantY, lsdp.PlantDiameter, lsdp.getMaxPlantSize(),
          lsdp.getMaxSeedCastDistance(), lsdp.getMaxSeedNumber(), lsdp.getPlantGrowthRate(),
          lsdp.getSeedViability()));
      }
    }

    // initialize grazers
    int grazerCount = lsdp.getInitialGrazerCount();
    for (int i=0; i < grazerCount; i++) {
      if (lsdp.getGrazerData())
      {
        entities.add(new Grazer(lsdp.GrazerX, lsdp.GrazerY, lsdp.GrazerEnergy,
          lsdp.getGrazerMaintainSpeedTime(), lsdp.getGrazerMaxSpeed(), lsdp.getGrazerEnergyInputRate(),
          lsdp.getGrazerEnergyOutputRate(), lsdp.getGrazerEnergyToReproduce()));
      }
    }

    // initialize predators
    int predatorCount = lsdp.getInitialPredatorCount();
    for (int i=0; i < predatorCount; i++) {
      if (lsdp.getPredatorData())
      {
        entities.add(new Predator(lsdp.PredatorX, lsdp.PredatorY, lsdp.PredatorEnergy,
          lsdp.PredatorGenotype, lsdp.getPredatorMaxSpeedHOD(), lsdp.getPredatorMaxSpeedHED(),
          lsdp.getPredatorMaxSpeedHOR(), lsdp.getPredatorMaintainSpeedTime(), lsdp.getPredatorEnergyOutputRate(),
          lsdp.getPredatorEnergyToReproduce(), lsdp.getPredatorMaxOffspring(), lsdp.getPredatorOffspringEnergyLevel(),
          lsdp.getPredatorGestationPeriod()));
      }
    }
  }

  public void run(){
    System.out.println("Running the Server");
  }

  public static void main() {
    Simulator lifesim = new Simulator();
    lifesim.init();
    lifesim.run();
  }
}