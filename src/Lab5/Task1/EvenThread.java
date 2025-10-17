package Lab5.Task1;

// Поток для четных чисел

public class EvenThread extends Thread {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " начал работу");

        for (int i = 2; i <= 10; i += 2) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            try {
                // Небольшая задержка для демонстрации асинхронности
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("Поток прерван: " + e.getMessage());
            }
        }

        System.out.println(Thread.currentThread().getName() + " завершил работу");
    }
}
}