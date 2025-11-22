package Lab9;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:h2:~/lab9db";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private Connection connection;

    public DatabaseManager() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Подключение к БД установлено");
            initializeDatabase();
        } catch (Exception e) {
            System.err.println("Ошибка подключения к БД: " + e.getMessage());
        }
    }

    private void initializeDatabase() {
        try {
            Statement stmt = connection.createStatement();

            // Таблица музыки
            String createMusicTable = "CREATE TABLE IF NOT EXISTS music (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255) NOT NULL)";
            stmt.execute(createMusicTable);

            // Заполняем начальными данными, если таблица пуста
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM music");
            if (rs.next() && rs.getInt(1) == 0) {
                String[] initialSongs = {
                        "Bohemian Rhapsody", "Stairway to Heaven", "Imagine",
                        "Sweet Child O Mine", "Hey Jude", "Hotel California",
                        "Billie Jean", "Wonderwall", "Smells Like Teen Spirit",
                        "Let It Be", "I Want It All", "November Rain"
                };

                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO music (title) VALUES (?)"
                );

                for (String song : initialSongs) {
                    ps.setString(1, song);
                    ps.executeUpdate();
                }
                System.out.println("Таблица music заполнена начальными данными!");
            }

        } catch (SQLException e) {
            System.err.println("Ошибка инициализации базы данных: " + e.getMessage());
        }
    }

    public void createBookTables() {
        try {
            Statement stmt = connection.createStatement();

            // Таблица посетителей
            String createVisitorsTable = "CREATE TABLE IF NOT EXISTS visitors (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "surname VARCHAR(100) NOT NULL, " +
                    "phone VARCHAR(20), " +
                    "subscribed BOOLEAN, " +
                    "UNIQUE(name, surname)" +
                    ")";
            stmt.execute(createVisitorsTable);

            // Таблица книг
            String createBooksTable = "CREATE TABLE IF NOT EXISTS books (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "isbn VARCHAR(20) UNIQUE NOT NULL, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "author VARCHAR(255) NOT NULL, " +
                    "publishing_year INT, " +
                    "publisher VARCHAR(255)" +
                    ")";
            stmt.execute(createBooksTable);

            // Таблица связей
            String createFavoritesTable = "CREATE TABLE IF NOT EXISTS visitor_books (" +
                    "visitor_id INT, " +
                    "book_id INT, " +
                    "FOREIGN KEY (visitor_id) REFERENCES visitors(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE, " +
                    "PRIMARY KEY (visitor_id, book_id)" +
                    ")";
            stmt.execute(createFavoritesTable);

            System.out.println("Таблицы для книг и посетителей созданы!");

        } catch (SQLException e) {
            System.err.println("Ошибка создания таблиц: " + e.getMessage());
        }
    }

    public void dropBookTables() {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("DROP TABLE IF EXISTS visitor_books");
            stmt.execute("DROP TABLE IF EXISTS books");
            stmt.execute("DROP TABLE IF EXISTS visitors");
            System.out.println("Таблицы книг и посетителей удалены!");
        } catch (SQLException e) {
            System.err.println("Ошибка удаления таблиц: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Соединение с базой данных закрыто!");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка закрытия соединения: " + e.getMessage());
        }
    }
}
