package JavaFX.shapes.models;

import javafx.scene.canvas.GraphicsContext;
import JavaFX.shapes.geometry2d.*;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShapeManager {
    private List<DraggableShape> shapes;
    private Random random;

    public ShapeManager() {
        shapes = new ArrayList<>();
        random = new Random();
    }

    public void addRandomRectangle(double maxWidth, double maxHeight) {
        double x = random.nextDouble() * (maxWidth - 100);
        double y = random.nextDouble() * (maxHeight - 100);
        double width = 50 + random.nextDouble() * 100;
        double height = 50 + random.nextDouble() * 100;
        Color color = generateRandomColor();

        Rectangle rect = new Rectangle(x, y, width, height);
        DraggableShape shape = new DraggableShape(rect, color);
        shapes.add(shape);
    }

    public void addRandomCircle(double maxWidth, double maxHeight) {
        double x = random.nextDouble() * (maxWidth - 100);
        double y = random.nextDouble() * (maxHeight - 100);
        double radius = 25 + random.nextDouble() * 50;
        Color color = generateRandomColor();

        Circle circle = new Circle(x, y, radius);
        DraggableShape shape = new DraggableShape(circle, color);
        shapes.add(shape);
    }

    public DraggableShape selectShapeAt(double x, double y) {
        // Проверяем с конца (верхние фигуры)
        for (int i = shapes.size() - 1; i >= 0; i--) {
            DraggableShape shape = shapes.get(i);
            if (shape.contains(x, y)) {
                return shape;
            }
        }
        return null;
    }

    public void bringToFront(DraggableShape shape) {
        shapes.remove(shape);
        shapes.add(shape);
    }

    public void drawAll(GraphicsContext gc) {
        for (DraggableShape shape : shapes) {
            shape.draw(gc);
        }
    }

    private Color generateRandomColor() {
        return Color.color(
                random.nextDouble(),
                random.nextDouble(),
                random.nextDouble(),
                0.7  // Полупрозрачность
        );
    }
}