package Lab9.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.util.List;
import java.util.ArrayList;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Visitor {
    private int id;
    private String name;
    private String surname;
    private String phone;
    private boolean subscribed;

    @Builder.Default
    private  List<Book> favoriteBooks = new ArrayList<>();

    public Visitor(String name, String surname, String phone, boolean subscribed) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.subscribed = subscribed;
        this.favoriteBooks = new ArrayList<>();
    }

    // Метод для добавления книги
    public void addFavoriteBook(Book book) {
        if(this.favoriteBooks == null) {
            this.favoriteBooks = new ArrayList<>();
        }
        this.favoriteBooks.add(book);
    }

    @Override
    public String toString() {
        return String.format("Visitor{id=%d, name='%s', surname='%s', phone='%s', subscribed=%s, favoriteBooks=%d}",
                id, name, surname, phone, subscribed,
                favoriteBooks != null ? favoriteBooks.size() : 0);
    }
}
