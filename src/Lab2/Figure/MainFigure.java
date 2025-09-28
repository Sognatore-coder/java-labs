package Lab2.Figure;
import Lab2.Figure.geometry2d.Circle;
import Lab2.Figure.geometry2d.Rectangle;
import Lab2.Figure.geometry3d.Cylinder;
import Lab2.Figure.exceptions.NegativeValueException;

public class MainFigure {
    public static void main(String[] args) {
        try {
            // Тестируем 2D фигуры
            Circle circle = new Circle(5.0);
            Rectangle rectangle = new Rectangle(4.0, 6.0);

            System.out.println("=== 2D Фигуры ===");
            System.out.println(circle);
            System.out.println(rectangle);

            // Тестируем 3D фигуры
            Cylinder cylinder1 = new Cylinder(circle, 10.0);
            Cylinder cylinder2 = new Cylinder(rectangle, 8.0);

            System.out.println("\n=== 3D Фигуры ===");
            System.out.println(cylinder1);
            System.out.println(cylinder2);

            // Тестируем исключения
            System.out.println("\n=== Тест исключений ===");
            try {
                Circle invalidCircle = new Circle(-5.0);
            } catch (NegativeValueException e) {
                System.out.println("Поймано исключение: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}