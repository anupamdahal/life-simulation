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
    grazerConfig.setGrazerMaintainSpeedTime(lsdp.getGrazerMaintainSpeedTime());
    grazerConfig.setGrazerMaxSpeed(lsdp.getGrazerMaxSpeed());
    grazerConfig.setGrazerEnergyInputRate(lsdp.getGrazerEnergyInputRate());
    grazerConfig.setGrazerEnergyOutputRate(lsdp.getGrazerEnergyOutputRate());
    grazerConfig.setGrazerEnergyToReproduce(lsdp.getGrazerEnergyToReproduce());
    int grazerCount = lsdp.getInitialGrazerCount();
    for (int i=0; i < grazerCount; i++) {
      if (lsdp.getGrazerData())
      {
        map.entities.add(new Grazer(lsdp.GrazerX, lsdp.GrazerY, lsdp.GrazerEnergy));
      }
    }

    // initialize predators
    predatorConfig.setPredatorMaxSpeedHOD(lsdp.getPredatorMaxSpeedHOD());
    predatorConfig.setPredatorMaxSpeedHED(lsdp.getPredatorMaxSpeedHED());
    predatorConfig.setPredatorMaxSpeedHOR(lsdp.getPredatorMaxSpeedHOR());
    predatorConfig.setPredatorMaintainSpeedTime(lsdp.getPredatorMaintainSpeedTime());
    predatorConfig.setPredatorEnergyOutputRate(lsdp.getPredatorEnergyOutputRate());
    predatorConfig.setPredatorEnergyToReproduce(lsdp.getPredatorEnergyToReproduce());
    predatorConfig.setPredatorMaxOffspring(lsdp.getPredatorMaxOffspring());
    predatorConfig.setPredatorOffspringEnergyLevel(lsdp.getPredatorOffspringEnergyLevel());
    predatorConfig.setPredatorGestationPeriod(lsdp.getPredatorGestationPeriod());
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