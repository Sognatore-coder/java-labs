package JavaFX.shapes.geometry2d;

public class Circle extends Figure{
    private double radius;

    public Circle(double x, double y, double radius) {
        super(x, y);
        this.radius = radius;
    }

    @Override
    public boolean contains(double pointX, double pointY) {
        double dx = pointX - x;
        double dy = pointY - y;
        return dx * dx + dy * dy <= radius * radius;
    }

    @Override
    public void moveTo(double newX, double newY) {
        this.x = newX;
        this.y = newY;
    }

    public double getRadius() { return radius; }
}
