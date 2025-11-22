package Lab9.util;

import Lab9.model.Visitor;
import Lab9.model.Book;
import org.json.JSONArray;
import lombok.experimental.UtilityClass;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


// Аннотация делает класс утилитарным (все методы static)
@UtilityClass

public class JsonParser {

    public static Visitor parseVisitorFromJson(JSONObject visitorJson) {
        // Создаем список книг
        List<Book> favoriteBooks = new ArrayList<>();
        if (visitorJson.has("favoriteBooks")) {
            JSONArray booksArray = visitorJson.getJSONArray("favoriteBooks");
            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject bookJson = booksArray.getJSONObject(i);
                Book book = parseBookFromJson(bookJson);
                favoriteBooks.add(book);
            }
        }

        return Visitor.builder()
                .name(visitorJson.getString("name"))
                .surname(visitorJson.getString("surname"))
                .phone(visitorJson.getString("phone"))
                .subscribed(visitorJson.getBoolean("subscribed"))
                .favoriteBooks(favoriteBooks)
                .build();
        }

    public static Book parseBookFromJson(JSONObject bookJson) {
        return Book.builder()
                .isbn(bookJson.getString("isbn"))
                .name(bookJson.getString("name"))
                .author(bookJson.getString("author"))
                .publishingYear(bookJson.getInt("publishingYear"))
                .publisher(bookJson.getString("publisher"))
                .build();
    }
}