package JavaFX.shapes.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import JavaFX.shapes.models.ShapeManager;
import JavaFX.shapes.models.DraggableShape;

public class ShapesController {

    @FXML private Canvas canvas;
    @FXML private Pane canvasContainer;
    @FXML private Button rectangleBtn;
    @FXML private Button circleBtn;

    private ShapeManager shapeManager;
    private GraphicsContext gc;
    private DraggableShape selectedShape;

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        shapeManager = new ShapeManager();

        setupCanvasSize();
        drawShapes();
    }

    private void setupCanvasSize() {
        // Canvas заполняет весь Pane
        canvas.widthProperty().bind(canvasContainer.widthProperty());
        canvas.heightProperty().bind(canvasContainer.heightProperty());

        // Перерисовываем при изменении размера
        canvas.widthProperty().addListener((obs, oldVal, newVal) -> drawShapes());
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> drawShapes());
    }

    @FXML
    private void handleAddRectangle() {
        shapeManager.addRandomRectangle(canvas.getWidth(), canvas.getHeight());
        drawShapes();
    }

    @FXML
    private void handleAddCircle() {
        shapeManager.addRandomCircle(canvas.getWidth(), canvas.getHeight());
        drawShapes();
    }

    @FXML
    private void handleCanvasMousePressed(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        if (event.isPrimaryButtonDown()) {
            // ЛКМ - выбор фигуры для перетаскивания
            selectedShape = shapeManager.selectShapeAt(x, y);
            if (selectedShape != null) {
                shapeManager.bringToFront(selectedShape);
            }
        } else if (event.isSecondaryButtonDown()) {
            // ПКМ - смена цвета
            DraggableShape shape = shapeManager.selectShapeAt(x, y);
            if (shape != null) {
                shape.setRandomColor();
                drawShapes();
            }
        }
    }

    @FXML
    private void handleCanvasMouseDragged(MouseEvent event) {
        if (selectedShape != null && event.isPrimaryButtonDown()) {
            selectedShape.moveTo(event.getX(), event.getY());
            drawShapes();
        }
    }

    @FXML
    private void handleCanvasMouseReleased(MouseEvent event) {
        selectedShape = null;
    }

    private void drawShapes() {
        clearCanvas();
        shapeManager.drawAll(gc);
    }

    private void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}