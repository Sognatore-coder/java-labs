package JavaFX.shapes.geometry2d;

public class Rectangle extends Figure{
    private double width, height;

    public Rectangle(double x, double y, double width, double height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean contains(double pointX, double pointY) {
        return pointX >= x && pointX <= x + width &&
                pointY >= y && pointY <= y + height;
    }

    @Override
    public void moveTo(double newX, double newY) {
        this.x = newX;
        this.y = newY;
    }

    public double getWidth() { return width; }
    public double getHeight() { return height; }
}
