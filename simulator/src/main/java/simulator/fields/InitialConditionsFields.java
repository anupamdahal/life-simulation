package simulator.fields;

import grpc.InitialConditions;

public class InitialConditionsFields {

  public static final String ENTITIES = "entities";

  public static final String WORLD = "landConfig";
  public static final String WORLD_WIDTH = "landX";
  public static final String WORLD_HEIGHT = "landY";

  public static final String PLANT = "plantConfig";
  public static final String PLANT_GROWTH_RATE = "growthRate";
  public static final String MAX_SEED_CAST_DISTANCE = "maxSeedCastDist";
  public static final String MAX_PLANT_SIZE = "maxSize";
  public static final String MAX_SEED_NUMBER = "maxSeedCount";
  public static final String MAX_SEED_VIABILITY = "seedVariability";
  public static final String PLANT_DIAMETER = "diameter";


  public static final String GRAZER = "grazerConfig";
  public static final String GRAZER_INITIAL_ENERGY = "initialEnergy";
  public static final String GRAZER_ENERGY_INPUT_RATE = "energyInput";
  public static final String GRAZER_ENERGY_OUTPUT_RATE = "energyOutput";
  public static final String GRAZER_ENERGY_TO_REPRODUCE = "energyToReproduce";
  public static final String GRAZER_MAINTAIN_SPEED_TIME = "maintainSpeedTime";
  public static final String GRAZER_MAX_SPEED = "maxSpeed";
  
  public static final String PREDATOR = "predatorConfig";
  public static final String PREDATOR_INITIAL_ENERGY = "initialEnergy";
  public static final String PREDATOR_MAX_SPEED_HOD = "maxSpeedHOD";
  public static final String PREDATOR_MAX_SPEED_HED = "maxSpeedHED";
  public static final String PREDATOR_MAX_SPEED_HOR = "maxSpeedHOR";
  public static final String PREDATOR_ENERGY_OUTPUT_RATE = "energyOutput";
  public static final String PREDATOR_ENERGY_TO_REPRODUCE = "energyToReproduce";
  public static final String PREDATOR_MAINTAIN_SPEED_TIME = "maintainSpeedTime";
  public static final String PREDATOR_MAX_OFFSPRING = "maxOffspring";
  public static final String PREDATOR_GESTATION_PERIOD = "gestationPeriod";
  public static final String PREDATOR_OFFSPRING_ENERGY_LEVEL = "offspringEnergy";
  public static final String[] PREDATOR_GENOTYPES = new String[] {
    "AA SS FF",
    "Aa Ss Ff",
    "aa ss ff",
  };

  public static final String OBSTACLE = "obstacleConfig";
  public static final String OBSTACLE_DIAMETER = "diameter";
  public static final String OBSTACLE_HEIGHT = "height";
  
  public static final int    PREDATOR_TYPE = 1;
  public static final int    GRAZER_TYPE = 2;
  public static final int    PLANT_TYPE = 3;
  public static final int    OBSTACLE_TYPE = 4;

  private InitialConditionsFields() {
  }
}
