package Lab5.Task1;

// Поток для нечетных чисел

public class OddRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " начал работу");

        for (int i = 1; i <= 9; i += 2) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Поток прерван: " + e.getMessage());
            }
        }

        System.out.println(Thread.currentThread().getName() + " завершил работу");
    }
}