package Lab4.ObjClass;


import lombok.AllArgsConstrustor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstrustor
public class Book {
    private String name;
    private String author;
    private int publishingYear;
    private String isbn;
    private String publisher;

    @Override
    public boolean equals(Object o){
        if(this == o ) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return java.util.Object.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return java.util.Object.hash(isbn);
    }
}