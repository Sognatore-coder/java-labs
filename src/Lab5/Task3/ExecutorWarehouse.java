package Lab5.Task3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


// Модифицированный склад с использованием ExecutorService

public class ExecutorWarehouse {

    public static final String[] SHOE_TYPES = {
            "Nike Air Max", "Adidas Ultraboost", "Puma RS-X",
            "Reebok Classic", "Converse Chuck Taylor", "Vans Old Skool"
    };

    private static final int MAX_CAPACITY = 10;
    private final Queue<Order> orders = new LinkedList<>();
    private final ExecutorService executor;

    public ExecutorWarehouse(int threadPoolSize) {
        // Создаем пул потоков фиксированного размера
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
    }


    // Асинхронное добавление заказа
    public void receiveOrderAsync(Order order) {
        executor.submit(() -> {
            synchronized (this) {
                while (orders.size() >= MAX_CAPACITY) {
                    try {
                        System.out.println("Склад полон! Producer ждет...");
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                orders.offer(order);
                System.out.println("Добавлен: " + order + " | Заказов на складе: " + orders.size());
                notifyAll();
            }
        });
    }


    //Асинхронная обработка заказа
    public void fulfillOrderAsync() {
        executor.submit(() -> {
            synchronized (this) {
                while (orders.isEmpty()) {
                    try {
                        System.out.println("Склад пуст! Consumer ждет...");
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                Order order = orders.poll();
                System.out.println("Обработан: " + order + " | Заказов на складе: " + orders.size());
                notifyAll();
            }
        });
    }

    //Завершение работы ExecutorService
    public void shutdown() {
        executor.shutdown();
    }


    //Получить текущее количество заказов
    public synchronized int getOrderCount() {
        return orders.size();
    }
}

// Record для заказа
record Order(int orderId, String shoeType, int quantity) {
    @Override
    public String toString() {
        return String.format("Заказ #%d: %s x%d", orderId, shoeType, quantity);
    }
}