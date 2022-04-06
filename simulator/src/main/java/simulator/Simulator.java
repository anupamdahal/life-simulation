package simulator;

import simulator.entity.*;

public class Simulator{
  private Map map;
  private int simulation_time;

  public Simulator(){}

  public void init(){
    // initialize the map
    map = map.getInstance();

    // initialize simulation time to 0
    this.simulation_time = 0;

    // import the given data file to test the entity behaviors
    String DATAFILE = new String(System.getProperty("user.dir") + "/LifeSimulation01.xml");
    LifeSimDataParser lsdp = LifeSimDataParser.getInstance();	// Get the singleton
		lsdp.initDataParser(DATAFILE);

    // initialize the map
    map.setWidth((int)lsdp.getWorldWidth());
    map.setHeight((int)lsdp.getWorldHeight());

    // initialize obstacles
    int obstacleCount = lsdp.getObstacleCount();
    for (int i=0; i < obstacleCount; i++) {
      if(lsdp.getObstacleData())
      {
        map.entities.add(new Obstacle(lsdp.ObstacleX, lsdp.ObstacleY,
           lsdp.ObstacleDiameter, lsdp.ObstacleHeight));
      }
    }

    // initialize plants
    int plantCount = lsdp.getInitialPlantCount();
    for (int i=0; i < plantCount; i++) {
      if(lsdp.getPlantData())
      {
        map.entities.add(new Plant(lsdp.PlantX, lsdp.PlantY, lsdp.PlantDiameter, lsdp.getMaxPlantSize(),
          lsdp.getMaxSeedCastDistance(), lsdp.getMaxSeedNumber(), lsdp.getPlantGrowthRate(),
          lsdp.getSeedViability()));
      }
    }

    // initialize grazers
    int grazerCount = lsdp.getInitialGrazerCount();
    for (int i=0; i < grazerCount; i++) {
      if (lsdp.getGrazerData())
      {
        map.entities.add(new Grazer(lsdp.GrazerX, lsdp.GrazerY, lsdp.GrazerEnergy,
          lsdp.getGrazerMaintainSpeedTime(), lsdp.getGrazerMaxSpeed(), lsdp.getGrazerEnergyInputRate(),
          lsdp.getGrazerEnergyOutputRate(), lsdp.getGrazerEnergyToReproduce()));
      }
    }

    // initialize predators
    int predatorCount = lsdp.getInitialPredatorCount();
    for (int i=0; i < predatorCount; i++) {
      if (lsdp.getPredatorData())
      {
        map.entities.add(new Predator(lsdp.PredatorX, lsdp.PredatorY, lsdp.PredatorEnergy,
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