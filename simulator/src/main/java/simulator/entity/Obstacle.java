package simulator.entity;

public class Obstacle extends Entity {
    private double x, y, diameter, height;

    public Obstacle(double x, double y, double diameter, double height) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.height = height;
    }

    // TODO: is this obstacle in the path of a planned movement?
    // for now, is the point something is trying to move to inside an obstacle
    public boolean isBlocking(int x1, int y1) {
        double dx = x - x1;
        double dy = y - y1;
        double dist = Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));

        if (dist < diameter/2.0) {
            return true;
        }
        else {
            return false;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
