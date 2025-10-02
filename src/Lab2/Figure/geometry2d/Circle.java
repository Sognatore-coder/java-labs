package Lab2.Figure.geometry2d;
import Lab2.Figure.exceptions.NegativeValueException;

public class Circle implements Figure {
    private double radius;

    public Circle(double radius) {
        if (radius < 0) {
            throw new NegativeValueException("Радиус не может быть отрицательным: " + radius);
        }
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String toString() {
        return String.format("Круг [радиус=%.2f, площадь=%.2f, периметр=%.2f]",
                radius, area(), perimeter());
    }

    public double getRadius() {
        return radius;
    }
}