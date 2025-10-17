package Lab5.Task2;

import java.util.Random;

// Производитель заказов
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
        System.out.println(Thread.currentThread().getName() + " начал работу");

        for (int i = 1; i <= orderCount; i++) {
            // Случайный выбор типа обуви и количества
            String shoeType = ShoeWarehouse.SHOE_TYPES[random.nextInt(ShoeWarehouse.SHOE_TYPES.length)];
            int quantity = random.nextInt(10) + 1; // от 1 до 10

            Order order = new Order(i, shoeType, quantity);
            warehouse.receiveOrder(order);

            try {
                // Имитация времени на создание заказа
                Thread.sleep(random.nextInt(500) + 100);
            } catch (InterruptedException e) {
                System.out.println("Producer прерван: " + e.getMessage());
                break;
            }
        }

        System.out.println(Thread.currentThread().getName() + " завершил работу");
    }
}