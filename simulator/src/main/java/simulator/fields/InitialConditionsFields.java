package simulator.fields;

import grpc.InitialConditions;

public class InitialConditionsFields {
  public static final String WORLD_WIDTH                        = "";
  public static final String WORLD_HEIGHT                       = "";

  public static final String INITIAL_PLANT_COUNT                = "";
  public static final String PLANT_GROWTH_RATE                  = "";
  public static final String MAX_SEED_CAST_DISTANCE             = "";
  public static final String MAX_PLANT_SIZE                     = "";
  public static final String MAX_SEED_NUMBER                    = "";
  public static final String MAX_SEED_VIABILITY                 = "";
  public static final String PLANT_DATA                         = "";

  public static final String INITIAL_GRAZER_COUNT               = "";
  public static final String GRAZER_ENERGY_INPUT_RATE           = "";
  public static final String GRAZER_ENERGY_OUTPUT_RATE          = "";
  public static final String GRAZER_ENERGY_TO_REPRODUCE         = "";
  public static final String GRAZER_MAINTAIN_SPEED_TIME         = "";
  public static final String GRAZER_MAX_SPEED                   = "";
  public static final String GRAZER_DATA                        = "";

  public static final String INITIAL_PREDATOR_COUNT             = "";
  public static final String PREDATOR_MAX_SPEED_HOD             = "";
  public static final String PREDATOR_MAX_SPEED_HED             = "";
  public static final String PREDATOR_MAX_SPEED_HOR             = "";
  public static final String PREDATOR_ENERGY_OUTPUT_RATE        = "";
  public static final String PREDATOR_ENERGY_TO_REPRODUCE       = "";
  public static final String PREDATOR_MAINTAIN_SPEED_TIME       = "";
  public static final String PREDATOR_MAX_OFFSPRING             = "";
  public static final String PREDATOR_GESTATION_PERIOD          = "";
  public static final String PREDATOR_OFFSPRING_ENERGY_LEVEL    = "";
  public static final String PREDATOR_DATA                      = "";

  public static final String OBSTACLE_COUNT                     = "";
  public static final String OBSTACLE_DATA                      = "";
  
  private static InitialConditionsFields instance = new InitialConditionsFields();
  private InitialConditionsFields(){}
  public static InitialConditionsFields getInstance(){
    return instance;
  }
}
