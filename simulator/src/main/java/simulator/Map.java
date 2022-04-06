package simulator;

import java.util.*;
import javafx.util.Pair;
import simulator.entity.*;

public class Map {
    public ArrayList<Entity> entities;
    private int width, height;

    // there is only one map, so this is a singleton
    private static Map theInstance = new Map();

    private Map() {
        entities = new ArrayList<Entity>(entities);
    }

    public Map getInstance() {
        return theInstance;
    }

    // return true if a given point is within the map confines
    public boolean isPointInBounds(int x, int y) {
        if (0 < x && x < this.width && 0 < y && y < this.height) {
            return true;
        }
        else {
            return false;
        }
    }

    // seach the map for entities of a given type within a range
    // of a source entity
    // return a list of range, object pairs
    // TODO: make this more efficient
    public ArrayList<Pair<Double, Entity>> search(Entity source, Entity.EntityType type, int range) {
        ArrayList<Pair<Double, Entity>> result = new ArrayList<Pair<Double, Entity>>();
        for (int i=0; i < entities.size(); i++) {
            if (entities.get(i).type != type) {
                continue;
            }
            Entity target = entities.get(i);
            double distance = Math.sqrt(Math.pow(source.getX() - target.getX(), 2)
                + Math.pow(source.getY() - target.getY(), 2));
            if (distance <= range) {
                result.add(new Pair<>(distance, target));
            }
        }
        return result;
    }

    // override the search method to accept an x, y coordinate source as well
    public ArrayList<Pair<Double, Entity>> search(int x, int y, Entity.EntityType type, int range) {
        ArrayList<Pair<Double, Entity>> result = new ArrayList<Pair<Double, Entity>>();
        for (int i=0; i < entities.size(); i++) {
            if (entities.get(i).type != type) {
                continue;
            }
            Entity target = entities.get(i);
            double distance = Math.sqrt(Math.pow(x - target.getX(), 2)
                + Math.pow(y - target.getY(), 2));
            if (distance <= range) {
                result.add(new Pair<>(distance, target));
            }
        }
        return result;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
