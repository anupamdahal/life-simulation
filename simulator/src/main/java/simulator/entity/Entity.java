package simulator.entity;

import simulator.Map;

public abstract class Entity {
    public static Map map = Map.getInstance();
    public EntityType type;

    public int x, y;

    public static enum EntityType {
        GRAZER, PREDATOR, OBSTACLE, PLANT
    }

    // update the entity
    // this method is overridden by all entities which can
    // be updated
    public boolean update() {
        return true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
