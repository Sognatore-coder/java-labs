package Lab5.Task3;


import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


// Главный класс для демонстрации работы с ExecutorService

public class ExecutorMain {

    public static void main(String[] args) {

        // Создаем склад с пулом из 4 потоков
        ExecutorWarehouse warehouse = new ExecutorWarehouse(4);
        Random random = new Random();

        // ScheduledExecutorService для периодических задач
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // Планируем производителя: добавляет заказы каждые 200-700 мс
        scheduler.scheduleAtFixedRate(() -> {
            String shoeType = ExecutorWarehouse.SHOE_TYPES[
                    random.nextInt(ExecutorWarehouse.SHOE_TYPES.length)
                    ];
            int quantity = random.nextInt(10) + 1;
            int orderId = random.nextInt(1000);

            Order order = new Order(orderId, shoeType, quantity);
            warehouse.receiveOrderAsync(order);
        }, 0, random.nextInt(500) + 200, TimeUnit.MILLISECONDS);

        // Планируем потребителя: обрабатывает заказы каждые 300-800 мс
        scheduler.scheduleAtFixedRate(() -> {
            warehouse.fulfillOrderAsync();
        }, 100, random.nextInt(500) + 300, TimeUnit.MILLISECONDS);

        // Запускаем работу на 30 секунд
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Основной поток прерван: " + e.getMessage());
        }

        // Останавливаем планировщик и склад
        System.out.println("Завершение работы...");
        scheduler.shutdown();
        warehouse.shutdown();

        try {
            // Ждем завершения всех задач
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }

        System.out.println("Работа завершена!");
        System.out.println("Осталось заказов на складе: " + warehouse.getOrderCount());
    }
}