package simulator.entity;

public class Obstacle extends Entity {
    private double x, y, width, height;

    public Obstacle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
