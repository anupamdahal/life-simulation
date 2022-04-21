package simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.protobuf.ListValue;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import com.google.protobuf.Value.Builder;

import grpc.InitialConditions;
import grpc.Report;
import grpc.Results;
import simulator.fields.InitialConditionsFields;
import simulator.entity.*;
import simulator.entity.Entity.EntityType;

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
    this.report = new ArrayList<>();
    Map map = Map.getInstance();
    this.map = map;

    // initialize simulation time to 0
    this.simulation_time = 0;

    // import the given data file to test the entity behaviors
    String DATAFILE = new String("/home/anupam/files/school/programs/CS499/life-simulation/simulator/src/main/java/simulator/Simulator.java");
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

  public void generateDummyReport(){
    map.setWidth(5);
    map.setHeight(5);
    Random rand = new Random();
    for(int i = 0; i < 2; i++){
      int[][] temp = new int[map.getWidth()][map.getHeight()];
      for(int j = 0; j < map.getWidth(); j++){
        for (int k = 0; k < map.getHeight(); k++){
          temp[j][k]= rand.nextInt(5);
        }
      }
      this.report.add(temp);
    }
  }

  public Results getSimulationResults(){

    java.util.List<Value> entities;

    Builder builder = Value.newBuilder();
    ListValue.Builder rows = ListValue.newBuilder();
    Struct.Builder structBuilder = Struct.newBuilder();
    
    int k = 0;

    for(int a = 0; a < this.report.size(); a++){
      ListValue.Builder grid = ListValue.newBuilder();
      for(int i = 0; i < map.getWidth(); i++){
        rows = ListValue.newBuilder();
        entities = new java.util.ArrayList();
        for(int j = 0; j < map.getHeight(); j++){
          entities.add(builder.setNumberValue(this.report.get(a)[i][j]).build());
          builder.clear();
        }
        rows.addAllValues(entities);
        grid.addValues(builder.setListValue(rows.build()));
      }
      structBuilder.putFields(String.valueOf(k), builder.setListValue(grid.build()).build());
      k++;
    }

    Results results = Results.newBuilder().setResult(structBuilder.build()).setError(String.valueOf(this.report.size())).build();
    structBuilder.clear();
    return results;
  }

  public Report getSimulationReport(){
    ArrayList<Integer> report = new ArrayList<>();
    for(int a = 0; a < this.report.size(); a++){
    for(int j = 0; j < map.getHeight(); j++){
      for(int i = 0; i < map.getWidth(); i++){
          report.add(this.report.get(a)[j][i]);
        }    
      }
    }

    Report.Builder builder = Report.newBuilder();
    builder.addAllEntities(report);
    builder.setLength(this.report.size());
    
    //this looks incorrect but isnt
    builder.setWidth(map.getHeight());
    builder.setHeight(map.getWidth());
    builder.setError("None");

    assert(report.size() == this.report.size()*map.getWidth()*map.getHeight());
    return builder.build();
  }

  public void init(Struct ic){
    this.report = new ArrayList<>();

    Map map = Map.getInstance();
    this.map = map;

    // initialize simulation time to 0
    this.simulation_time = 0;

    // // import the given data file to test the entity behaviors
    java.util.Map<String, Value> world = ic.getFieldsMap().get(InitialConditionsFields.WORLD).getStructValue().getFieldsMap();
    java.util.Map<String, Value> plant = ic.getFieldsMap().get(InitialConditionsFields.PLANT).getStructValue().getFieldsMap();
    java.util.Map<String, Value> grazer = ic.getFieldsMap().get(InitialConditionsFields.GRAZER).getStructValue().getFieldsMap();
    java.util.Map<String, Value> predator = ic.getFieldsMap().get(InitialConditionsFields.PREDATOR).getStructValue().getFieldsMap();
    java.util.Map<String, Value> obstacle = ic.getFieldsMap().get(InitialConditionsFields.OBSTACLE).getStructValue().getFieldsMap();
    Random random = new Random();
    // initialize the map
    
    map.setWidth((int)world.get(InitialConditionsFields.WORLD_WIDTH).getNumberValue());
    map.setHeight((int)world.get(InitialConditionsFields.WORLD_HEIGHT).getNumberValue());


    // initialize config files
    GrazerConfig grazerConfig = GrazerConfig.getInstance();
    PredatorConfig predatorConfig = PredatorConfig.getInstance();
    PlantConfig plantConfig = PlantConfig.getInstance();
    this.grazerConfig = grazerConfig;
    this.predatorConfig = predatorConfig;
    this.plantConfig = plantConfig;


    // initialize plants
    plantConfig.setMaxSize((int) plant.get(InitialConditionsFields.MAX_PLANT_SIZE).getNumberValue());
    plantConfig.setMaxSeedCastDistance((int) plant.get(InitialConditionsFields.MAX_PLANT_SIZE).getNumberValue());
    plantConfig.setMaxSeedNumber((int) plant.get(InitialConditionsFields.MAX_PLANT_SIZE).getNumberValue());
    plantConfig.setGrowthRate((int) plant.get(InitialConditionsFields.MAX_PLANT_SIZE).getNumberValue());
    plantConfig.setSeedViability((int) plant.get(InitialConditionsFields.MAX_PLANT_SIZE).getNumberValue());

    
    // // initialize grazers
    grazerConfig.setMaintainSpeedTime((int) grazer.get(InitialConditionsFields.GRAZER_MAINTAIN_SPEED_TIME).getNumberValue());
    grazerConfig.setMaxSpeed((int) grazer.get(InitialConditionsFields.GRAZER_MAX_SPEED).getNumberValue());
    grazerConfig.setEnergyInputRate((int) grazer.get(InitialConditionsFields.GRAZER_ENERGY_INPUT_RATE).getNumberValue());
    grazerConfig.setEnergyOutputRate((int) grazer.get(InitialConditionsFields.GRAZER_ENERGY_OUTPUT_RATE).getNumberValue());
    grazerConfig.setEnergyToReproduce((int) grazer.get(InitialConditionsFields.GRAZER_ENERGY_TO_REPRODUCE).getNumberValue());


    // // initialize predators
    predatorConfig.setMaxSpeedHOD((int) predator.get(InitialConditionsFields.PREDATOR_MAX_SPEED_HOD).getNumberValue());
    predatorConfig.setMaxSpeedHED((int) predator.get(InitialConditionsFields.PREDATOR_MAX_SPEED_HED).getNumberValue());
    predatorConfig.setMaxSpeedHOR((int) predator.get(InitialConditionsFields.PREDATOR_MAX_SPEED_HOR).getNumberValue());
    predatorConfig.setMaintainSpeedTime((int) predator.get(InitialConditionsFields.PREDATOR_MAINTAIN_SPEED_TIME).getNumberValue());
    predatorConfig.setEnergyOutputRate((int) predator.get(InitialConditionsFields.PREDATOR_ENERGY_OUTPUT_RATE).getNumberValue());
    predatorConfig.setEnergyToReproduce((int) predator.get(InitialConditionsFields.PREDATOR_ENERGY_TO_REPRODUCE).getNumberValue());
    predatorConfig.setMaxOffspring((int) predator.get(InitialConditionsFields.PREDATOR_MAX_OFFSPRING).getNumberValue());
    predatorConfig.setOffspringEnergyLevel((int) predator.get(InitialConditionsFields.PREDATOR_OFFSPRING_ENERGY_LEVEL).getNumberValue());
    predatorConfig.setGestationPeriod((int) predator.get(InitialConditionsFields.PREDATOR_GESTATION_PERIOD).getNumberValue());
    List<Value> rows = ic.getFieldsMap().get(InitialConditionsFields.ENTITIES).getListValue().getValuesList();
    for(int i = 0; i < map.getWidth(); i++){
      for(int j = 0; j < map.getHeight(); j++){
        List<Value> cols = rows.get(j).getListValue().getValuesList();
        int entityType = (int) cols.get(i).getNumberValue();

        
        if( entityType == InitialConditionsFields.PREDATOR_TYPE){
          System.out.print(i+":"+j+ ": " + entityType + "\t");
          map.entities.add(new Predator(i, j,
                (int) predator.get(InitialConditionsFields.PREDATOR_INITIAL_ENERGY).getNumberValue(),
                InitialConditionsFields.PREDATOR_GENOTYPES[random.nextInt(InitialConditionsFields.PREDATOR_GENOTYPES.length)]));
        }
        else if( entityType == InitialConditionsFields.GRAZER_TYPE){
          System.out.print(i+":"+j+ ": " + entityType + "\t");
          map.entities.add(new Grazer(i, j,
                  (int) grazer.get(InitialConditionsFields.GRAZER_INITIAL_ENERGY).getNumberValue()));  
        }
        else if( entityType == InitialConditionsFields.PLANT_TYPE){
          System.out.print(i+":"+j+ ": " + entityType + "\t");
          map.entities.add(new Plant(i, j,
                  (int) plant.get(InitialConditionsFields.PLANT_DIAMETER).getNumberValue()));

        }
        else if( entityType == InitialConditionsFields.OBSTACLE_TYPE){
          System.out.print(i+":"+j+ ": " + entityType + "\t");
          map.entities.add(new Obstacle(i, j,
            (int) obstacle.get(InitialConditionsFields.OBSTACLE_DIAMETER).getNumberValue(),
            (int) obstacle.get(InitialConditionsFields.OBSTACLE_HEIGHT).getNumberValue()));

        }

      }
    }
    System.out.println(map.entities.size());
    System.out.println("End init");
  }

  public void run(){
    System.out.println("Begin run");
    report = new ArrayList<int[][]>();

    do {
      update();
    } while(map.shouldSimulationContinue() && this.simulation_time < 100);
    map.entities.clear();
  }

  public void update(){

    // for (int i=0; i < map.entities.size(); i++) {
    //   map.entities.get(i).update();

    // }
    this.simulation_time += 1;
    // TODO: send a snapshot of the simulation back to the server
    int[][] frame = new int[map.getHeight()][map.getWidth()];
    // fill the frame with positions of entities
    // initialize all values to 0
    for (int x=0; x < map.getWidth(); x++) {
      for (int y=0; y < map.getHeight(); y++) {
        frame[y][x] = 0;
      }
    }
    for (int i=0; i < map.entities.size(); i++) {
      map.entities.get(i).update();
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
      try {
        System.out.println(entity.y + " " + entity.x);
        frame[entity.y][entity.x] = type;
      } catch (Exception e) {
        //TODO: handle exception
        System.out.println("Entity out of bounds: Simulation time - " + this.simulation_time + ", " + (type) + ", " + entity.x + ", " + entity.y);
      }
      
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