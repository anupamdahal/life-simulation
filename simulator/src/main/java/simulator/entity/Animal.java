package simulator.entity;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.util.Pair;

public abstract class Animal extends Entity {
    public int energy, energyOutputRate;

    public boolean moveTowards(int desiredX, int desiredY) {
        // get the speed of the move and energy drain
        if (this.type == EntityType.GRAZER) {

        }
        // get the speed of the move and energy drain
        else if (this.type == EntityType.PREDATOR) {
            int a = this.maxSpeedHED;
        }
        else {
            return false; // you're trying to move something that can't move somehow
        }
        // is the point in bounds?
        if (map.isPointInBounds(x, y)) {
            // is the point inside a rock?
            // TODO: this should be "is the path to the point blocked by a rock"
            ArrayList<Pair<Double, Entity>> rocks = map.search(x, y, EntityType.OBSTACLE, 10);
            if (!rocks.isEmpty()) {
                Iterator<Pair<Double, Entity>> iter = rocks.iterator();
                while (iter.hasNext()) {
                    Obstacle rock = (Obstacle)iter.next().getValue();
                    if (rock.isBlocking(x, y)) {
                        return false;
                    }
                }
            }

            this.x = x;
            this.y = y;
            return true;
        }
        return false;
    }

    // move in a random direction
    public boolean moveRand() {

    }

    public boolean moveTowards(Entity target) {

    }

    public boolean fleeFrom(Entity target) {

    }
}
