package Lab4;

import Lab4.ObjClass.Visitor;
import Lab4.Task1_6.Task1;
import Lab4.Task1_6.Task2;
import Lab4.Task1_6.Task3;
import Lab4.Task1_6.Task4;
import Lab4.Task1_6.Task5;
import Lab4.Task1_6.Task6;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            String jsonContent;

            // Пробуем найти файл как ресурс (для GitHub Actions)
            InputStream inputStream = Main.class.getResourceAsStream("/Lab4/book.json");
            if (inputStream != null) {
                Reader reader = new InputStreamReader(inputStream);
                StringBuilder content = new StringBuilder();
                char[] buffer = new char[1024];
                int charsRead;
                while ((charsRead = reader.read(buffer)) != -1) {
                    content.append(buffer, 0, charsRead);
                }
                reader.close();
                jsonContent = content.toString();
            } else {
                // Если не найден как ресурс, ищем в файловой системе (для локальной разработки)
                jsonContent = new String(Files.readAllBytes(Paths.get("src/Lab4/book.json")));
            }

            // Парсинг JSON
            Gson gson = new Gson();
            Type visitorListType = new TypeToken<List<Visitor>>(){}.getType();
            List<Visitor> visitors = gson.fromJson(jsonContent, visitorListType);

            // Выполнение всех заданий
            Task1.execute(visitors);
            Task2.execute(visitors);
            Task3.execute(visitors);
            Task4.execute(visitors);
            Task5.execute(visitors);
            Task6.execute(visitors);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}