package simulator.entity;
import java.util.ArrayList;
import java.util.Random;

public class Entity_Movement extends Entity {

    static Random rand = new Random();    
    
    // Not sure if I need these since it might end up in entity
    int x_Bound = 1000;
    int y_Bound = 750;

    private int obstacle_Count;
    private int plant_Count;
    private int grazer_Count;
    private int predator_Count;

    private int north_Wall = y_Bound;
    private int south_Wall = -y_Bound;
    private int east_Wall = x_Bound;
    private int west_Wall = -x_Bound;
    // ***********************************************************************
    
    private String entity_Type;
    private int x;
    private int y;
    private int current_Entity;
    private int direction;
    private int g_Energy;
    private int p_Energy = predatorsList.get(current_Entity).getEnergy();
    private int g_Energy_To_Reproduce = grazersList.get(current_Entity).getEnergyToReproduce();
    private double g_Speed; // speed of grazer
    private double p_Speed; // speed of predator
    private double g_Max_Speed = grazersList.get(current_Entity).getMaxSpeed();
    private int chance; // used for determining if an entity is killed in combat
    
    private int space_Moved; // once units moved is >= 5, energy is taken away (fatigue)
    private int target; // whatever the entity is searching for (obstacle to hide behind, food, etc.)
    private int target_Tracker;
    private boolean visible; // this one might be a little scuffed but it's for noting if an entity is hidden behind an obstacle
    private boolean target_Nearby; // Once a target has been spotted, this is used to trigger various events
    private boolean chase; 
    private boolean flee;

    // Need to be able to determine which entity is using the code **************************************
    // REMOVE EXCESS FROM ENTITY

    void entityCount(){
        obstacle_Count = obstaclesList.size();
        for(int c = 0; c < obstacle_Count ; c++){
        }

        plant_Count = plantsList.size();
        for(int c = 0; c < plant_Count ; c++){
        }

        grazer_Count = grazersList.size();
        for(int c = 0; c < grazer_Count ; c++){
        }

        predator_Count = predatorsList.size();
        for(int c = 0; c < predator_Count ; c++){
        }
    }

    void move(){ // basic movement 
        if(entity_Type == "grazer"){
            if(direction == 0){
                // Don't know if the getter is necessary, was just trying various methods
                // because my coding knowledge isn't exactly the greatest
                x = grazersList.get(current_Entity).getX() + (int) g_Speed; // use "this" here?
            }
            else if(direction == 1){
                x = grazersList.get(current_Entity).getX() - (int) g_Speed;
            }
            else if(direction == 2){
                y = grazersList.get(current_Entity).getY() + (int) g_Speed;
            }
            else {
                y = grazersList.get(current_Entity).getY() - (int) g_Speed;
            }

            space_Moved = space_Moved + (int) g_Speed;
            if(space_Moved >= 5){
                g_Energy = grazersList.get(current_Entity).getEnergy() - grazersList.get(current_Entity).getEnergyOutputRate();
                space_Moved = 0;
            }
        }
        // Ideally I would like to have just one "speed" instead of having to separate 
        // between grazer and predator but I just have this for the time being
        else if(entity_Type == "predator"){
            if(direction == 0){
                x = predatorsList.get(current_Entity).getX() + (int) p_Speed;
            }
            else if(direction == 1){
                x = predatorsList.get(current_Entity).getX() - (int) p_Speed;
            }
            else if(direction == 2){
                y = predatorsList.get(current_Entity).getY() + (int) p_Speed;
            }
            else {
                y = predatorsList.get(current_Entity).getY() - (int) p_Speed;
            }
            space_Moved = space_Moved + (int) g_Speed;
            if(space_Moved >= 5){
                p_Energy = predatorsList.get(current_Entity).getEnergy() - predatorsList.get(current_Entity).getEnergyOutputRate();
                space_Moved = 0;
            }
        }
    }

    void highEnergy(){ /* Allows the grazer to run fast but I'm not entirely sure on if it is supposed to be
     the default or only triggered when fleeing from a predator. My interpretation was that it was supposed
     to be just the default but the latter would make more sense in a standard scenario obviously */

        if(entity_Type == "grazer") { // Only grazers can do this
            if(g_Energy > g_Max_Speed){
                g_Speed = g_Max_Speed;
            }
            else{
                g_Speed = (int)(g_Max_Speed * .75); // once the grazer isn't feeling really energetic, it returns to "normal"
            }
        }
    }

    void lowEnergy(){ // for when the grazer gets fatigued
            if(entity_Type == "grazer" && g_Energy <= 25){
                g_Speed = g_Speed / 2; 
            }
            else if(entity_Type == "predator" && p_Energy <= 25){
                p_Speed = p_Speed / 2; 
            }

            if(entity_Type == "grazer" && g_Energy <= 5){ // grazer got big tired and can't move
                g_Speed = 0;
            }
            else if(entity_Type == "predator" && g_Energy <= 15){ // predator got big tired and can't move
                p_Speed = 0;
            }
    
            if(energy <= 0) {
                // this part should remove the entity from the simulation ***********
            }
        }
    }

    void wall(){ // avoids running into walls
        if(y <= (south_Wall + 5)){
            direction = 0; //makes the entity rotate north
        }
        if(y >= (north_Wall - 5)){
            direction = 1; //makes the entity rotate south
        }
        if(x <= (west_Wall + 5)){ 
            direction = 2; //makes the entity rotate east
        }
        if(x >= (east_Wall - 5)){
            direction = 3; //makes the entity rotate west
        }
    } 

    void visibilityCheck(){ // NOT DONE YET **********************************

    }

    // Also needs loop to traverse whatever Ethan used so that it searches 
    // all the entities of that target type (Array, Linked List, etc.?)
    void search(int x1, int x2, int y1, int y2, int target, boolean visible){

        // checks if the target is within very close range
        if((((x1 <= (x2 + 5)) || (x1 >= (x2 - 5))) && ((y1 <= (y2 + 5)) || (y1 >= (y2 - 5))))){ 
            chase = false;
            flee = false;
            if((entity_Type == "grazer") && (target == 1)){ // 1 is for plant
                eat();
            }
            else if((entity_Type == "predator") && (target >= 2)){ // 2 is for grazer, 3 is for predator
                combat();
            }
        }

        // Checks for any units regardless of visibility within 25 units
        else if((((x1 <= (x2 + 25)) || (x1 >= (x2 - 25))) && ((y1 <= (y2 + 25)) || (y1 >= (y2 - 25))))){ 
            target_Nearby = true;
        }

        // Checks for any VISIBLE units within 150 units
        else if(((x1 <= (x2 + 150)) || (x1 >= (x2 - 150))) && ((y1 <= (y2 + 150)) || (y1 >= (y2 - 150)))){ 
            if(visible == true){
                target_Nearby = true;
            }
        }

        else {
            target_Nearby = false;
            chase = false;
            flee = false;
        }
    }

    void obstacleCheck(){ // INCOMPLETE **********************************
        target = 0; // Sets the target to obstacle

        // was pretty confused on how to implement this properly, same with the other 3 class-level checks below 
        entityCheck(x, obstacle.x, y, obstacle.y, target, visible);

        if((target_Nearby == true) && (flee == true)){ // can be used by grazers and predators
            hide();
        }
        if((target_Nearby == true) && (chase == true)){ // this would be used by predators
            walkAround();
        }
    }

    void grazerFoodCheck(){ // GRAZER ONLY, checks for nearby plants
        target = 1; // Sets the target to plant
        entityCheck(x, plant.x, y, plant.y, target, visible);
        if(target_Nearby == true){
            fightOrFlight();
        }
    }

    void predatorGrazerCheck(){ // PREDATOR ONLY, checks for nearby grazers
        target = 2; // Sets the target to grazer
        entityCheck(x, grazer.x, y, grazer.y, target, visible);
        if(target_Nearby == true){
            fightOrFlight();
        }
    }

    void predatorCheck(){ // checks for nearby predators
        target = 3; // Sets the target to predator
        entityCheck(x, predator.x, y, predator.y, target, visible);
        if(target_Nearby == true){
            fightOrFlight();
        }
    }
    
    void fightOrFlight(){
        if(flee = true){
            escape();
        }

        if(chase = true){
            if(target == 1) // checks for plant target
            {
                pursue(x, plant.x, y, plant.y);
            }
            else if(target == 2) // checks for grazer target
            {
                pursue(x, grazer.x, y, grazer.y);
            }
            else if(target == 3)// checks for predator target
            {
                pursue(x, predator.x, y, predator.y);
            }
        }  

        // haven't focused much on mating yet **
        if(mate == true){ 
            pursue(x, predator.x, y, predator.y);
        }
    }
    
    void pursue(int x1, int x2, int y1, int y2){
        if(chase == true){
            if((x1 <= x2) && (y1 <= y2)){ 
                if(rand.nextInt(1) == 0){ // 50% chance for the entity to chase north
                    direction = 1;
                }
                else{ // 50% chance for the entity to chase west
                    direction = 2;
                }
            }
            if((x1 >= x2) && (y1 >= y2)){
                if(rand.nextInt(1) == 0){ // 50% chance for the entity to chase south
                    direction = 0;
                }
                else{ // 50% chance for the entity to chase east
                    direction = 3;
                }
            }
            if((x1 < x2) && (y1 > y2)){
                if(rand.nextInt(1) == 0){ // 50% chance for the entity to chase south
                    direction = 0;
                }
                else{ // 50% chance for the entity to chase west
                    direction = 2;
                }
            }
            if((x1 > x2) && (y1 < y2)){
                if(rand.nextInt(1) == 0){ // 50% chance for the entity to chase north
                    direction = 1;
                }
                else{ // 50% chance for the entity to chase east
                    direction = 3;
                }
            }
        }
    }

    void escape(){ // INCOMPLETE **********************************
        if(flee == true)
        {
            if((x <= predator.x) && (y <= predator.y)){ 
                if(rand.nextInt(1) == 0){ // 50% chance for the entity to flee north
                    direction = 0;
                }
                else{ // 50% chance for the entity to flee west
                    direction = 3;
                }
            }
            if((x >= predator.x) && (y >= predator.y)){
                if(rand.nextInt(1) == 0){ // 50% chance for the entity to flee south
                    direction = 1;
                }
                else{ // 50% chance for the entity to flee east
                    direction = 2;
                }
            }
            if((x < predator.x) && (y > predator.y)){
                if(rand.nextInt(1) == 0){ // 50% chance for the entity to flee south
                    direction = 1;
                }
                else{ // 50% chance for the entity to flee west
                    direction = 3;
                }
            }
            if((x > predator.x) && (y < predator.y)){
                if(rand.nextInt(1) == 0){ // 50% chance for the entity to flee north
                    direction = 0;
                }
                else{ // 50% chance for the entity to flee east
                    direction = 2;
                }
            }
            wall();
            // Incorporate hiding option
        }
    }

    void eat(){
        if(entity_Type == "grazer"){ // Grazers eating plants only
            g_Energy = g_Energy + target_Energy;
        }
        if(entity_Type == "predator"){ // Predators eating only (target food can be either grazer or predator) 
            g_Energy = (int)(target_Energy * .9);
        }

        if(target == 1){
            plantsList.remove(target_Tracker);
        }
        else if(target == 2){
            grazersList.remove(target_Tracker);
        }
        else{
            predatorsList.remove(target_Tracker);
        }
        target_Nearby = false;

        if(entity_Type == "grazer") && (g_Energy >= g_Energy_To_Reproduce)) { // grazer reproduction is asexual
                reproduce();
            }
            if(entity_Type == "predator"){ 
                mateSearch();
            }
        }
    }

    void mateSearch(){ // NOT DONE YET
        if(energy >= energy_To_Reproduce){
            // sends predator on the journey to find another interested predator
        }

        reproduce();
    }

    void reproduce(){ 
        if(entity_Type == "grazer"){
            // two grazers are produced with half the parent's energy
        }
        if(entity_Type == "predator"){
            // predators are produced 
        }
    }

    void hide(){ // Used for hiding behind obstacles

    }

    void walkAround(){ // Used for getting around obstacles when not trying to hide (should apply to both grazer and predator)

    }

    void combat(){ // Needs to take into account predator genes
        if(genotype == "SS"){
            if(target == 2){
                
            }
            else if(target_Genotype == "SS"){
                chance = rand.nextInt
            }
            else if(target_Genotype == "Ss"){

            }   
            else{

            }
        }
        else if(genotype == "Ss"){
            if(target == 2){
                
            }
        }
        else{
            if(target == 2){

            }
        }
    }

    void movement(){ // Once finished will call all the functions required to control entity movement

        // these are just here as placeholders to remind me later
        if(entity_Type == "grazer"){ // Checks if the entity is a grazer
            grazerFoodCheck();
        }
        if(entity_Type == "predator"){ // Checks if the entity is a predator
            predatorGrazerCheck();
        }
    }
}