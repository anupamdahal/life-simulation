package simulator.entity;

import simulator.Map;

public class Entity {
    public Map map;

    // update the entity
    // this method is overridden by all entities which can
    // be updated
    public boolean update() {
        return true;
    }
}
