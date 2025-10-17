package Lab5.Task2;

import java.util.LinkedList;
import java.util.Queue;

// Склад обуви с синхронизированными методами

public class ShoeWarehouse {

    // Поле со списком товаров
    public static final String[] SHOE_TYPES = {
            "Nike Air Max", "Adidas Ultraboost", "Puma RS-X",
            "Reebok Classic", "Converse Chuck Taylor", "Vans Old Skool"
    };

    // Максимальная вместимость склада
    private static final int MAX_CAPACITY = 10;

    // Очередь заказов (FIFO)
    private final Queue<Order> orders = new LinkedList<>();

    // Метод для добавления заказа на склад
    public synchronized void receiveOrder(Order order) {
        // Ждем, если склад полон
        while (orders.size() >= MAX_CAPACITY) {
            try {
                System.out.println("Склад полон! Producer ждет...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Producer прерван: " + e.getMessage());
                return;
            }
        }

        // Добавляем заказ
        orders.offer(order);
        System.out.println("Добавлен: " + order + " | Заказов на складе: " + orders.size());

        // Уведомляем всех ожидающих потребителей
        notifyAll();
    }

    // Метод для обработки заказа
    public synchronized Order fulfillOrder() {
        // Ждем, если склад пуст
        while (orders.isEmpty()) {
            try {
                System.out.println("Склад пуст! Consumer ждет...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Consumer прерван: " + e.getMessage());
                return null;
            }
        }

        // Извлекаем заказ (FIFO)
        Order order = orders.poll();
        System.out.println("Обработан: " + order + " | Заказов на складе: " + orders.size());

        // Уведомляем всех ожидающих производителей
        notifyAll();

        return order;
    }

    // Текущее кол-во заказов на складе
    public synchronized int getOrderCount() {
        return orders.size();
    }
}
}