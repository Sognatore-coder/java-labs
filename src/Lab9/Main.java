package Lab9;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        System.out.println("Лабораторная работа 9:");
        System.out.println("=" .repeat(25));

        DatabaseManager dbManager = new DatabaseManager();
        TaskExecutor executor = new TaskExecutor(dbManager);

        try {
            // Выполняем задания 1-3 (музыка)
            executor.task1();
            executor.task2();
            executor.task3();

            // Проверяем наличие файла book.json в корне проекта
            String bookJsonPath = "book.json";

            if (Files.exists(Paths.get(bookJsonPath))) {
                System.out.println("Файл book.json найден");
                executor.task4();
                executor.task5();
                executor.task6();
                executor.task7();
                executor.task8();
            } else {
                System.out.println("\nФайл book.json не найден");
                System.out.println("Пропускаем задания 4-8.");
            }

            System.out.println("\nПрограмма завершена!");

        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbManager.close();
        }
    }
}
