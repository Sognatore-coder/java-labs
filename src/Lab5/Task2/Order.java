package Lab5.Task2;

//Класс для хранения данных с помощью record

public record Order(int orderId, String shoeType, int quantity) {

    @Override
    public String toString() {
        return String.format("Заказ #%d: %s x%d", orderId, shoeType, quantity);
    }
}