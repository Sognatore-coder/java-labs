package Lab9.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    private int id;
    private String isbn;
    private String name;
    private String author;
    private int publishingYear;
    private String publisher;

    public Book(String isbn, String name, String author, int publishingYear, String publisher) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.publishingYear = publishingYear;
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return String.format("Book{id=%d, name='%s', author='%s', year=%d, isbn='%s'}",
                id, name, author, publishingYear, isbn);
    }
}
