package simulator;

import simulator.entity.*;

public class Simulator{
  private Map map;
  private int simulation_time;
  private GrazerConfig grazerConfig;
  private PredatorConfig predatorConfig;
  private PlantConfig plantConfig;

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

    // initialize config files
    grazerConfig = grazerConfig.getInstance();
    predatorConfig = predatorConfig.getInstance();
    plantConfig = plantConfig.getInstance();

    // initialize plants
    plantConfig.setMaxPlantSize(lsdp.getMaxPlantSize());
    plantConfig.setMaxSeedCastDistance(lsdp.getMaxSeedCastDistance());
    plantConfig.setMaxSeedNumber(lsdp.getMaxSeedNumber());
    plantConfig.setPlantGrowthRate(lsdp.getPlantGrowthRate());
    plantConfig.setSeedViability(lsdp.getSeedViability());
    int plantCount = lsdp.getInitialPlantCount();
    for (int i=0; i < plantCount; i++) {
      if(lsdp.getPlantData())
      {
        map.entities.add(new Plant(lsdp.PlantX, lsdp.PlantY, lsdp.PlantDiameter);
      }
    }

    // initialize grazers
    grazerConfig.setMaintainSpeedTime(lsdp.getGrazerMaintainSpeedTime());
    grazerConfig.setMaxSpeed(lsdp.getGrazerMaxSpeed());
    grazerConfig.setEnergyInputRate(lsdp.getGrazerEnergyInputRate());
    grazerConfig.setEnergyOutputRate(lsdp.getGrazerEnergyOutputRate());
    grazerConfig.setEnergyToReproduce(lsdp.getGrazerEnergyToReproduce());
    int grazerCount = lsdp.getInitialGrazerCount();
    for (int i=0; i < grazerCount; i++) {
      if (lsdp.getGrazerData())
      {
        map.entities.add(new Grazer(lsdp.GrazerX, lsdp.GrazerY, lsdp.GrazerEnergy));
      }
    }

    // initialize predators
    predatorConfig.setMaxSpeedHOD(lsdp.getPredatorMaxSpeedHOD());
    predatorConfig.setMaxSpeedHED(lsdp.getPredatorMaxSpeedHED());
    predatorConfig.setMaxSpeedHOR(lsdp.getPredatorMaxSpeedHOR());
    predatorConfig.setMaintainSpeedTime(lsdp.getPredatorMaintainSpeedTime());
    predatorConfig.setEnergyOutputRate(lsdp.getPredatorEnergyOutputRate());
    predatorConfig.setEnergyToReproduce(lsdp.getPredatorEnergyToReproduce());
    predatorConfig.setMaxOffspring(lsdp.getPredatorMaxOffspring());
    predatorConfig.setOffspringEnergyLevel(lsdp.getPredatorOffspringEnergyLevel());
    predatorConfig.setGestationPeriod((int)lsdp.getPredatorGestationPeriod()); // should this be a double?
    int predatorCount = lsdp.getInitialPredatorCount();
    for (int i=0; i < predatorCount; i++) {
      if (lsdp.getPredatorData())
      {
        map.entities.add(new Predator(lsdp.PredatorX, lsdp.PredatorY, lsdp.PredatorEnergy, lsdp.PredatorGenotype));
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