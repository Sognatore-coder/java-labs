package Lab6.Task2;

import java.nio.file.*;
import java.io.IOException;
import java.util.stream.Stream;

public class TaskFileSystem {

    public static void main(String[] args) {
        String surname = "Besedin";
        String name = "Kirill";

        try {
            Path mainDir = Paths.get(surname);
            Files.createDirectories(mainDir);
            System.out.println("Создана директория: " + mainDir.toAbsolutePath());

            // 2. Создаем файл с именем
            Path nameFile = mainDir.resolve(name);
            Files.createFile(nameFile);
            System.out.println("Создан файл: " + nameFile);

            // 3. Создаем вложенные директории и копируем файл
            Path nestedDir = mainDir.resolve("dir1/dir2/dir3");
            Files.createDirectories(nestedDir);
            System.out.println("Созданы вложенные директории: " + nestedDir);

            Path copiedFile = nestedDir.resolve(name);
            Files.copy(nameFile, copiedFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Файл скопирован в: " + copiedFile);

            // 4. Создаем file1 в dir1
            Path file1 = mainDir.resolve("dir1/file1");
            Files.createFile(file1);
            System.out.println("Создан файл: " + file1);

            // 5. Создаем file2 в dir2
            Path file2 = mainDir.resolve("dir1/dir2/file2");
            Files.createFile(file2);
            System.out.println("Создан файл: " + file2);

            // 6. Рекурсивный обход
            System.out.println("\n=== Рекурсивный обход директории " + surname + " ===");
            try (Stream<Path> paths = Files.walk(mainDir)) {
                paths.forEach(path -> {
                    if (Files.isDirectory(path)) {
                        System.out.println("D: " + mainDir.relativize(path));
                    } else {
                        System.out.println("F: " + mainDir.relativize(path));
                    }
                });
            }

            // 7. Удаляем dir1 со всем содержимым
            System.out.println("\n=== Удаление директории dir1 ===");
            Path dir1 = mainDir.resolve("dir1");
            try (Stream<Path> paths = Files.walk(dir1)) {
                paths.sorted((p1, p2) -> -p1.compareTo(p2)) // обратный порядок
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                                System.out.println("Удалено: " + path);
                            } catch (IOException e) {
                                System.err.println("Ошибка удаления: " + path + " - " + e.getMessage());
                            }
                        });
            }
        } catch (IOException e){
            System.err.println("Ошибка в работе с файловой системой: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
