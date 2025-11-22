package Lab9;

import java.sql.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
import Lab9.model.Visitor;
import Lab9.model.Book;
import Lab9.util.JsonParser;

public class TaskExecutor {
    private DatabaseManager dbManager;

    public TaskExecutor(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void task1() {
        System.out.println("\nЗадание 1: Все музыкальные композиции");
        System.out.println("=" .repeat(25));

        try(Statement stmt = dbManager.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM music")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                System.out.printf("%2d: %s%n", id, title);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка выполнения задания 1: " + e.getMessage());
        }
    }

    public void task2() {
        System.out.println("\nЗадание 2: Композиции без букв 'm' и 't'");
        System.out.println("=" .repeat(25));

        try(Statement stmt = dbManager.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM music " +
                    "WHERE LOWER(title) NOT LIKE '%m%' AND LOWER(title) NOT LIKE '%t%'"
            )) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                System.out.printf("%2d: %s%n", id, title);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка выполнения задания 2: " + e.getMessage());
        }
    }

    public void task3() {
        System.out.println("\nЗадание 3: Добавление любимой композиции");
        System.out.println("=" .repeat(25));

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement("INSERT INTO music (title) VALUES (?)")) {
            ps.setString(1,"My Favorite Song - forever");
            int rows = ps.executeUpdate();
            System.out.println("Добавлено композиций: " + rows);

        } catch (SQLException e) {
            System.err.println("Ошибка выполнения задания 3: " + e.getMessage());
        }
    }


    public void task4() {
        System.out.println("\nЗадание 4: Работа с JSON books.json");
        System.out.println("=" .repeat(25));

        try {
            // Создаем таблицы
            dbManager.createBookTables();

            // Читаем и парсим JSON
            String jsonContent = new String(Files.readAllBytes(Paths.get("book.json")));
            JSONArray visitorsArray = new JSONArray(jsonContent);

            // Обрабатываем данные
            processJSONData(visitorsArray);

        } catch (Exception e) {
            System.err.println("Ошибка выполнения задания 4: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void processJSONData(JSONArray visitorsArray) {
        try {
            int totalVisitors = 0;
            int totalBooks = 0;

            for (int i = 0; i < visitorsArray.length(); i++) {
                JSONObject visitorJson = visitorsArray.getJSONObject(i);

                // Используем JsonParser для создания Visitor объекта
                Visitor visitor = JsonParser.parseVisitorFromJson(visitorJson);

                // Добавляем посетителя
                int visitorId = insertOrGetVisitor(visitor);
                if (visitorId != -1) {
                    totalVisitors++;
                }

                // Обрабатываем книги посетителя
                for (Book book : visitor.getFavoriteBooks()) {
                    // Добавляем книгу
                    int bookId = insertOrGetBook(book);
                    if (bookId != -1) {
                        totalBooks++;
                    }

                    // Создаем связь
                    if (visitorId != -1 && bookId != -1) {
                        insertVisitorBookRelation(visitorId, bookId);
                    }
                }
            }

            System.out.println("Обработано посетителей: " + totalVisitors);
            System.out.println("Обработано книг: " + totalBooks);

        } catch (Exception e) {
            System.err.println("Ошибка обработки JSON данных: " + e.getMessage());
        }
    }

    private int insertOrGetVisitor(Visitor visitor) throws SQLException {
        String sql = "INSERT INTO visitors (name, surname, phone, subscribed) VALUES (?, ?, ?, ?)";
        String checkSql = "SELECT id FROM visitors WHERE name = ? AND surname = ?";

        // Проверяем существование
        PreparedStatement checkStmt = dbManager.getConnection().prepareStatement(checkSql);
        checkStmt.setString(1, visitor.getName());
        checkStmt.setString(2, visitor.getSurname());
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        }

        // Добавляем нового
        PreparedStatement ps = dbManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, visitor.getName());
        ps.setString(2, visitor.getSurname());
        ps.setString(3, visitor.getPhone());
        ps.setBoolean(4, visitor.isSubscribed());
        ps.executeUpdate();

        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        }

        return -1;
    }

    private int insertOrGetBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (isbn, name, author, publishing_year, publisher) VALUES (?, ?, ?, ?, ?)";
        String checkSql = "SELECT id FROM books WHERE isbn = ?";

        // Проверяем существование
        PreparedStatement checkStmt = dbManager.getConnection().prepareStatement(checkSql);
        checkStmt.setString(1, book.getIsbn());
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        }

        // Добавляем новую книгу
        PreparedStatement ps = dbManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, book.getIsbn());
        ps.setString(2, book.getName());
        ps.setString(3, book.getAuthor());
        ps.setInt(4, book.getPublishingYear());
        ps.setString(5, book.getPublisher());
        ps.executeUpdate();

        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        }

        return -1;
    }

    private void insertVisitorBookRelation(int visitorId, int bookId) {
        String sql = "INSERT INTO visitor_books (visitor_id, book_id) VALUES (?, ?)";

        try {
            PreparedStatement ps = dbManager.getConnection().prepareStatement(sql);
            ps.setInt(1, visitorId);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        } catch (SQLException e) {
            // Игнорируем ошибку дублирования связи
            if (!e.getMessage().contains("PRIMARY KEY")) {
                System.err.println("Ошибка добавления связи: " + e.getMessage());
            }
        }
    }


    public void task5() {
        System.out.println("\nЗадание 5: Книги отсортированные по году издания");
        System.out.println("=" .repeat(25));

        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT name, author, publishing_year FROM books ORDER BY publishing_year"
             )) {

            while (rs.next()) {
                String name = rs.getString("name");
                String author = rs.getString("author");
                int year = rs.getInt("publishing_year");
                System.out.printf("%4d: %-40s - %s%n", year, name, author);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка выполнения задания 5: " + e.getMessage());
        }
    }

    public void task6() {
        System.out.println("\nЗадание 6: Книги изданные до 2000 года");
        System.out.println("=" .repeat(25));

        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT name, author, publishing_year FROM books WHERE publishing_year < 2000 ORDER BY publishing_year"
             )) {

            while (rs.next()) {
                String name = rs.getString("name");
                String author = rs.getString("author");
                int year = rs.getInt("publishing_year");
                System.out.printf("%4d: %-40s - %s%n", year, name, author);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка выполнения задания 6: " + e.getMessage());
        }
    }

    public void task7() {
        System.out.println("\nЗадание 7: Добавление информации о себе");
        System.out.println("=" .repeat(25));

        try {
            // Создаем себя как посетителя
            Visitor me = Visitor.builder()
                    .name("Кирилл")
                    .surname("Беседин")
                    .phone("+7-951-593-82-72")
                    .subscribed(true)
                    .build();

            // Добавляем посетителя в базу
            int visitorId = insertOrGetVisitor(me);
            if (visitorId != -1) {
                System.out.println("Добавлен посетитель: " + me.getName() + " " + me.getSurname());
            }

            // Создаем любимые книги с помощью Builder
            Book[] myFavoriteBooks = {
                    Book.builder()
                            .name("The girl from the song")
                            .author("Emma Scott")
                            .isbn("9780132350884")
                            .publishingYear(2020)
                            .publisher("Freedom")
                            .build(),

                    Book.builder()
                            .name("Among a thousand words")
                            .author("Emma Scott")
                            .isbn("9780134685991")
                            .publishingYear(2024)
                            .publisher("Freedom")
                            .build(),

                    Book.builder()
                            .name("grok the algorithms")
                            .author("Aditya Bhargava")
                            .isbn("9785446141722")
                            .publishingYear(2025)
                            .publisher("Progress book")
                            .build()
            };

            // Добавляем книги и связи
            for (Book book : myFavoriteBooks) {
                int bookId = insertOrGetBook(book);
                if (bookId != -1 && visitorId != -1) {
                    insertVisitorBookRelation(visitorId, bookId);
                    System.out.println("Добавлена книга: " + book.getName());
                }
            }

            // Выводим результат
            showMyData(visitorId);

        } catch (SQLException e) {
            System.err.println("Ошибка выполнения задания 7: " + e.getMessage());
        }
    }

    private void showMyData(int visitorId) {
        System.out.println("\nМои данные и любимые книги:");
        System.out.println("-".repeat(25));

        String sql = "SELECT v.name, v.surname, b.name as book_name, b.author, b.publishing_year " +
                "FROM visitors v " +
                "JOIN visitor_books vb ON v.id = vb.visitor_id " +
                "JOIN books b ON vb.book_id = b.id " +
                "WHERE v.id = ?";


        try (PreparedStatement ps = dbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, visitorId);
            ResultSet rs = ps.executeQuery();

            boolean first = true;
            while (rs.next()) {
                if (first) {
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    System.out.println("Посетитель: " + name + " " + surname);
                    System.out.println("Любимые книги:");
                    first = false;
                }

                String bookName = rs.getString("book_name");
                String author = rs.getString("author");
                int year = rs.getInt("publishing_year");
                System.out.printf("  • %s (%s, %d)%n", bookName, author, year);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка получения данных: " + e.getMessage());
        }
    }

    public void task8() {
        System.out.println("\nЗадание 8: Удаление таблиц книг и посетителей");
        System.out.println("=" .repeat(25));

        dbManager.dropBookTables();
    }
}
