package Lab5.Task2;

import java.util.Random;

// Потребитель заказов

public class Consumer implements Runnable {

    private final ShoeWarehouse warehouse;
    private final int ordersToProcess;
    private final Random random = new Random();

    public Consumer(ShoeWarehouse warehouse, int ordersToProcess) {
        this.warehouse = warehouse;
        this.ordersToProcess = ordersToProcess;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " начал работу");

        for (int i = 0; i < ordersToProcess; i++) {
            Order order = warehouse.fulfillOrder();
            if (order == null) {
                break; // Прерываем если получили null
            }

            try {
                // Имитация времени на обработку заказа
                Thread.sleep(random.nextInt(800) + 200);
            } catch (InterruptedException e) {
                System.out.println("Consumer прерван: " + e.getMessage());
                break;
            }
        }

        System.out.println(Thread.currentThread().getName() + " завершил работу");
    }
}