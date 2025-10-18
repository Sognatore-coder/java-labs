package Lab5.Task2;

import java.util.Random;

// Класс для производителя заказов
public class Producer implements Runnable {

    private final ShoeWarehouse warehouse;
    private final int orderCount;
    private final Random random = new Random();

    public Producer(ShoeWarehouse warehouse, int orderCount) {
        this.warehouse = warehouse;
        this.orderCount = orderCount;
    }

    @Override
    public void run() {
        System.out.println("\nPRODUCER начал создание " + orderCount + " заказов");

        for (int i = 1; i <= orderCount; i++) {
            String shoeType = ShoeWarehouse.SHOE_TYPES[random.nextInt(ShoeWarehouse.SHOE_TYPES.length)];
            int quantity = random.nextInt(5) + 1; // от 1 до 5

            Order order = new Order(i, shoeType, quantity);
            warehouse.receiveOrder(order);

            try {
                // Быстрое создание заказов
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Producer прерван");
                break;
            }
        }

        // Сообщаем что производство завершено
        warehouse.finishProduction();
        System.out.println("\nPRODUCER завершил создание всех заказов");
    }
}