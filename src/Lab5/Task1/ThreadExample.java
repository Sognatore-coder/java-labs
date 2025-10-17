package Lab5.Task1;

// Класс для демонстрации потоков

public class ThreadExample {

    public static void main(String[] args) {
        System.out.println("Основной поток: " + Thread.currentThread().getName());

        // Создание потока через наследование от Thread
        Thread evenThread = new EvenThread();
        evenThread.setName("EvenThread");

        // Создание потока через реализацию Runnable
        Thread oddThread = new Thread(new OddRunnable(), "OddThread");

        // Запуск потоков (асинхронное выполнение)
        evenThread.start();
        oddThread.start();

        try {
            // Ожидаем завершения обоих потоков
            evenThread.join();
            oddThread.join();
        } catch (InterruptedException e) {
            System.out.println("Основной поток прерван: " + e.getMessage());
        }

        System.out.println("Все потоки завершили работу");
    }
}