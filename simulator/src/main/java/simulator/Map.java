package simulator;

import java.util.*;
import simulator.entity.*;

public class Map {
    public ArrayList<Entity> entities;

    // there is only one map, so this is a singleton
    private static Map theInstance = new Map();

    private Map() {
        entities = new ArrayList<Entity>(entities);
    }

    public Map getInstance() {
        return theInstance;
    }
}
