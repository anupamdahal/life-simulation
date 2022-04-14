package simulator;

import java.util.List;
import java.util.Random;

import com.google.protobuf.Struct;
import com.google.protobuf.Value;

import simulator.fields.InitialConditionsFields;
import simulator.entity.*;

public class Simulator{
  private simulator.Map map;
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

  public void init(Struct ic){
        // initialize the map
    // map = map.getInstance();

    // // initialize simulation time to 0
    // this.simulation_time = 0;

    // // import the given data file to test the entity behaviors
    java.util.Map<String, Value> world = ic.getFieldsMap().get(InitialConditionsFields.WORLD).getStructValue().getFieldsMap();
    java.util.Map<String, Value> plant = ic.getFieldsMap().get(InitialConditionsFields.PLANT).getStructValue().getFieldsMap();
    java.util.Map<String, Value> grazer = ic.getFieldsMap().get(InitialConditionsFields.GRAZER).getStructValue().getFieldsMap();
    java.util.Map<String, Value> predator = ic.getFieldsMap().get(InitialConditionsFields.PREDATOR).getStructValue().getFieldsMap();
    java.util.Map<String, Value> obstacle = ic.getFieldsMap().get(InitialConditionsFields.OBSTACLE).getStructValue().getFieldsMap();
    List<Value> rows = ic.getFieldsMap().get(InitialConditionsFields.ENTITIES).getListValue().getValuesList();
    int i=0;
    int j=0;
    Random random = new Random();
    // // initialize the map
    // map.setWidth((int)world.get(InitialConditionsFields.WORLD_WIDTH).getNumberValue());
    // map.setHeight((int)world.get(InitialConditionsFields.WORLD_HEIGHT).getNumberValue());


    // // initialize config files
    // grazerConfig = grazerConfig.getInstance();
    // predatorConfig = predatorConfig.getInstance();
    // plantConfig = plantConfig.getInstance();


    // // initialize plants
    // plantConfig.setMaxSize((int) plant.get(InitialConditionsFields.MAX_PLANT_SIZE).getNumberValue());
    // plantConfig.setMaxSeedCastDistance((int) plant.get(InitialConditionsFields.MAX_PLANT_SIZE).getNumberValue());
    // plantConfig.setMaxSeedNumber((int) plant.get(InitialConditionsFields.MAX_PLANT_SIZE).getNumberValue());
    // plantConfig.setGrowthRate((int) plant.get(InitialConditionsFields.MAX_PLANT_SIZE).getNumberValue());
    // plantConfig.setSeedViability((int) plant.get(InitialConditionsFields.MAX_PLANT_SIZE).getNumberValue());

    
    // // // initialize grazers
    // grazerConfig.setMaintainSpeedTime((int) grazer.get(InitialConditionsFields.GRAZER_MAINTAIN_SPEED_TIME).getNumberValue());
    // grazerConfig.setMaxSpeed((int) grazer.get(InitialConditionsFields.GRAZER_MAX_SPEED).getNumberValue());
    // grazerConfig.setEnergyInputRate((int) grazer.get(InitialConditionsFields.GRAZER_ENERGY_INPUT_RATE).getNumberValue());
    // grazerConfig.setEnergyOutputRate((int) grazer.get(InitialConditionsFields.GRAZER_ENERGY_OUTPUT_RATE).getNumberValue());
    // grazerConfig.setEnergyToReproduce((int) grazer.get(InitialConditionsFields.GRAZER_ENERGY_TO_REPRODUCE).getNumberValue());


    // // // initialize predators
    // predatorConfig.setMaxSpeedHOD((int) predator.get(InitialConditionsFields.PREDATOR_MAX_SPEED_HOD).getNumberValue());
    // predatorConfig.setMaxSpeedHED((int) predator.get(InitialConditionsFields.PREDATOR_MAX_SPEED_HED).getNumberValue());
    // predatorConfig.setMaxSpeedHOR((int) predator.get(InitialConditionsFields.PREDATOR_MAX_SPEED_HOR).getNumberValue());
    // predatorConfig.setMaintainSpeedTime((int) predator.get(InitialConditionsFields.PREDATOR_MAINTAIN_SPEED_TIME).getNumberValue());
    // predatorConfig.setEnergyOutputRate((int) predator.get(InitialConditionsFields.PREDATOR_ENERGY_OUTPUT_RATE).getNumberValue());
    // predatorConfig.setEnergyToReproduce((int) predator.get(InitialConditionsFields.PREDATOR_ENERGY_TO_REPRODUCE).getNumberValue());
    // predatorConfig.setMaxOffspring((int) predator.get(InitialConditionsFields.PREDATOR_MAX_OFFSPRING).getNumberValue());
    // predatorConfig.setOffspringEnergyLevel((int) predator.get(InitialConditionsFields.PREDATOR_OFFSPRING_ENERGY_LEVEL).getNumberValue());
    // predatorConfig.setGestationPeriod((int) predator.get(InitialConditionsFields.PREDATOR_GESTATION_PERIOD).getNumberValue());

  

    for(Value row: rows){
      List<Value> cols = row.getListValue().getValuesList();
      for(Value col: cols){
        int entityType = (int) col.getNumberValue();
        // switch (entityType) {
        //   case InitialConditionsFields.PREDATOR_TYPE:
        //       map.entities.add(new Predator(i, j,
        //             (int) predator.get(InitialConditionsFields.PREDATOR_INITIAL_ENERGY).getNumberValue(),
        //             InitialConditionsFields.PREDATOR_GENOTYPES[random.nextInt(InitialConditionsFields.PREDATOR_GENOTYPES.length)]));
        //       break;
        //   case InitialConditionsFields.GRAZER_TYPE:
        //     map.entities.add(new Grazer(i, j,
        //             (int) grazer.get(InitialConditionsFields.GRAZER_INITIAL_ENERGY).getNumberValue()));  
        //       break;
        //   case InitialConditionsFields.PLANT_TYPE:
        //       map.entities.add(new Plant(i, j,
        //               (int) plant.get(InitialConditionsFields.PLANT_DIAMETER).getNumberValue()));
        //       break;
        //   case InitialConditionsFields.OBSTACLE_TYPE:
        //       map.entities.add(new Obstacle(i, j,
        //         (int) obstacle.get(InitialConditionsFields.OBSTACLE_DIAMETER).getNumberValue(),
        //         (int) obstacle.get(InitialConditionsFields.OBSTACLE_HEIGHT).getNumberValue()));
        //       break;
        
        //   default:
        //     break;
        // }
        j++;
      }
      i++;
    }
  }

  public void run(){
    System.out.println("Running the Server");
    while(map.shouldSimulationContinue()) {
      update();
    }
  }

  public void update(){
    for (int i=0; i < map.entities.size(); i++) {
      map.entities.get(i).update();
    }
    this.simulation_time += 1;
    // TODO: send a snapshot of the simulation back to the server
  }

  public static void main() {
    Simulator lifesim = new Simulator();
    lifesim.init();
    lifesim.run();
  }
}