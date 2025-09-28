package Lab2.Figure.geometry2d;
import Lab2.Figure.exceptions.NegativeValueException;

public class Rectangle implements Figure {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        if (width < 0 || height < 0) {
            throw new NegativeValueException("Стороны прямоугольника не могут быть отрицательными: " + width + "x" + height);
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);
    }

    @Override
    public String toString() {
        return String.format("Прямоугольник [ширина=%.2f, высота=%.2f, площадь=%.2f, периметр=%.2f]", width, height, area(), perimeter());
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}