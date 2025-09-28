package Lab2.Figure.geometry3d;
import Lab2.Figure.geometry2d.Figure;
import Lab2.Figure.exceptions.NegativeValueException;
import Lab2.Figure.exceptions.InvalidGeometryException;

public class Cylinder {
    private Figure base;  // Основание
    private double height; // Высота цилиндра

    public Cylinder(Figure base, double height) {
        if (base == null) {
            throw new InvalidGeometryException("Основание не может быть null");
        }
        if (height < 0) {
            throw new NegativeValueException("Высота не может быть отрицательной: " + height);
        }

        this.base = base;
        this.height = height;
    }

    public double volume() {
        return base.area() * height;
    }

    @Override
    public String toString() {
        return String.format("Цилиндр [основание=%s, высота=%.2f, объем=%.2f]",
                base.toString(), height, volume());
    }

    public Figure getBase() {
        return base;
    }

    public double getHeight() {
        return height;
    }
}