package Lab5.Task2;

import java.util.LinkedList;
import java.util.Queue;

// Класс для склада
public class ShoeWarehouse {

    public static final String[] SHOE_TYPES = {
            "Nike Air Max", "Adidas Ultraboost", "Puma RS-X",
            "Reebok Classic", "Converse Chuck Taylor"
    };

    private static final int MAX_CAPACITY = 20; //
    private final Queue<Order> orders = new LinkedList<>();
    private boolean productionFinished = false; // Флаг для координации между потребителем и производителем

    public synchronized void receiveOrder(Order order) {
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
        System.out.println("ДОБАВЛЕН: " + order + " | Всего заказов: " + orders.size());
        notifyAll();
    }

    public synchronized Order fulfillOrder() {
        while (orders.isEmpty()) {
            if (productionFinished) {
                return null; // Производство завершено и заказов нет
            }
            try {
                System.out.println("Consumer ждет заказы...");
                wait(300); // Выстраиваем очередь для потребителей.
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }

        Order order = orders.poll();
        System.out.println("ОБРАБОТАН: " + order + " | Осталось заказов: " + orders.size());
        notifyAll();
        return order;
    }

    public synchronized void finishProduction() {
        productionFinished = true;
        notifyAll(); // Будим всех потребителей
    }

    public synchronized int getOrderCount() {
        return orders.size();
    }
}
