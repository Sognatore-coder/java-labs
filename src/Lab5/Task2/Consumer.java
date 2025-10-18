package Lab5.Task2;

import java.util.Random;

// Класс для потребителя заказов
public class Consumer implements Runnable {

    private final ShoeWarehouse warehouse;
    private final String name;
    private final Random random = new Random();

    public Consumer(ShoeWarehouse warehouse, String name) {
        this.warehouse = warehouse;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + " начал работу");

        while (true) {
            Order order = warehouse.fulfillOrder();
            if (order == null) {
                // Заказов больше нет
                System.out.println(name + " завершил работу - заказов больше нет");
                break;
            }

            try {
                // Имитация времени на обработку заказа
                Thread.sleep(600);
            } catch (InterruptedException e) {
                System.out.println(name + " прерван");
                break;
            }
        }
    }
}