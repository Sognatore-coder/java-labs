package Lab5.Task2;

// Главный класс для демонстрации

public class WareMain {
    public static void main(String[] args) {

        // Создаем склад
        ShoeWarehouse warehouse = new ShoeWarehouse();

        // Создаем и запускаем производителя (20 заказов)
        Thread producerThread = new Thread(new Producer(warehouse, 20), "Producer");

        // Создаем и запускаем потребителей (по 5 заказов на каждого)
        Thread consumer1 = new Thread(new Consumer(warehouse, 5), "Consumer-1");
        Thread consumer2 = new Thread(new Consumer(warehouse, 5), "Consumer-2");
        Thread consumer3 = new Thread(new Consumer(warehouse, 5), "Consumer-3");
        Thread consumer4 = new Thread(new Consumer(warehouse, 5), "Consumer-4");

        // Запускаем все потоки
        producerThread.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();

        // Ожидаем завершения всех потоков
        try {
            producerThread.join();
            consumer1.join();
            consumer2.join();
            consumer3.join();
            consumer4.join();
        } catch (InterruptedException e) {
            System.out.println("Основной поток прерван: " + e.getMessage());
        }

        System.out.println("Все заказы обработаны!");
        System.out.println("Осталось заказов на складе: " + warehouse.getOrderCount());
    }
}