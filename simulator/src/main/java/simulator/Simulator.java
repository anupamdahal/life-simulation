package simulator;

import simulator.entity.*;
import simulator.entity.Entity.EntityType;

import java.util.ArrayList;

public class Simulator{
  private Map map;
  private int simulation_time;
  private GrazerConfig grazerConfig;
  private PredatorConfig predatorConfig;
  private PlantConfig plantConfig;
  private ArrayList<int[][]> report;

  public Simulator(){}

  public void init(){
    // initialize the map
    Map map = Map.getInstance();
    this.map = map;

    // initialize simulation time to 0
    this.simulation_time = 0;

    // import the given data file to test the entity behaviors
    String DATAFILE = new String(System.getProperty("user.dir") + "/simulator/src/LifeSimDataParserJava/LifeSimulation01.xml");
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
    GrazerConfig grazerConfig = GrazerConfig.getInstance();
    PredatorConfig predatorConfig = PredatorConfig.getInstance();
    PlantConfig plantConfig = PlantConfig.getInstance();
    this.grazerConfig = grazerConfig;
    this.predatorConfig = predatorConfig;
    this.plantConfig = plantConfig;

    // initialize plants
    plantConfig.setMaxSize(lsdp.getMaxPlantSize());
    plantConfig.setMaxSeedCastDistance(lsdp.getMaxSeedCastDistance());
    plantConfig.setMaxSeedNumber(lsdp.getMaxSeedNumber());
    plantConfig.setGrowthRate(lsdp.getPlantGrowthRate());
    plantConfig.setSeedViability(lsdp.getSeedViability());
    int plantCount = lsdp.getInitialPlantCount();
    for (int i=0; i < plantCount; i++) {
      if(lsdp.getPlantData())
      {
        map.entities.add(new Plant(lsdp.PlantX, lsdp.PlantY, lsdp.PlantDiameter));
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
    report = new ArrayList<int[][]>();
    while(map.shouldSimulationContinue()) {
      update();
    }
    // Anupam: this.report should now have the grid
  }

  public void update(){
    for (int i=0; i < map.entities.size(); i++) {
      map.entities.get(i).update();
    }
    this.simulation_time += 1;
    System.out.println(simulation_time);
    // TODO: send a snapshot of the simulation back to the server
    int[][] frame = new int[map.getWidth()][map.getHeight()];
    // fill the frame with positions of entities
    // initialize all values to 0
    for (int x=0; x < map.getWidth(); x++) {
      for (int y=0; y < map.getHeight(); y++) {
        frame[x][y] = 0;
      }
    }
    // TODO: fill width of plants and obstacles
    // TODO: ensure that grazers/predators take precedence over plants
    for (int i=0; i < map.entities.size(); i++) {
      Entity entity = map.entities.get(i);
      int type = 0;
      if (entity.type == EntityType.PREDATOR) { type = 1; }
      else if (entity.type == EntityType.GRAZER)   { type = 2; }
      else if (entity.type == EntityType.PLANT)    { type = 3; }
      else if (entity.type == EntityType.OBSTACLE) { type = 4; }
      frame[entity.x][entity.y] = type;
    }
    // add the frame to the report
    report.add(frame);
  }

  public static void main(String[] args) {
    Simulator lifesim = new Simulator();
    lifesim.init();
    lifesim.run();
  }
}