package JavaFX.shapes.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import JavaFX.shapes.geometry2d.*;
import java.util.Random;

public class DraggableShape {
    private Figure figure;
    private Color color;
    private Random random;

    public DraggableShape(Figure figure, Color color) {
        this.figure = figure;
        this.color = color;
        this.random = new Random();
    }

    public boolean contains(double x, double y) {
        return figure.contains(x, y);
    }

    public void moveTo(double x, double y) {
        figure.moveTo(x, y);
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color);

        if (figure instanceof Rectangle) {
            Rectangle rect = (Rectangle) figure;
            gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        } else if (figure instanceof Circle) {
            Circle circle = (Circle) figure;
            gc.fillOval(
                    circle.getX() - circle.getRadius(),
                    circle.getY() - circle.getRadius(),
                    circle.getRadius() * 2,
                    circle.getRadius() * 2
            );
        }

        // Обводка
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        if (figure instanceof Rectangle) {
            Rectangle rect = (Rectangle) figure;
            gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        } else if (figure instanceof Circle) {
            Circle circle = (Circle) figure;
            gc.strokeOval(
                    circle.getX() - circle.getRadius(),
                    circle.getY() - circle.getRadius(),
                    circle.getRadius() * 2,
                    circle.getRadius() * 2
            );
        }
    }

    public void setRandomColor() {
        this.color = Color.color(
                random.nextDouble(),
                random.nextDouble(),
                random.nextDouble(),
                0.7
        );
    }

    public Figure getFigure() { return figure; }
    public Color getColor() { return color; }
}