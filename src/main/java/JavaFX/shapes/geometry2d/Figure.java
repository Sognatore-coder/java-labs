package JavaFX.shapes.geometry2d;

public abstract class Figure {
    protected double x, y;

    public Figure(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Проверяет, содержит ли фигура точку (x, y)
    public abstract boolean contains(double x, double y);

    // Перемещает фигуру в новую позицию
    public abstract void moveTo(double x, double y);

    // Геттеры
    public double getX() { return x; }
    public double getY() { return y; }

    // Сеттеры
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + x + ", " + y + ")";
    }
}
