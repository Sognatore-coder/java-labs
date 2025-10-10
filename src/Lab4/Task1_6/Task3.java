package Lab4.Task1_6;

import Lab4.ObjClass.Visitor;
import Lab4.ObjClass.Book;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// Отсортированные книги по году издания
public class Task3 {
    public static void execute(List<Visitor> visitors) {
        List<Book> sortedBooks = visitors.stream()
                .flatMap(v -> v.getFavoriteBooks().stream())
                .distinct()
                .sorted(Comparator.comparingInt(Book::getPublishingYear))
                .collect(Collectors.toList());

        System.out.println("Книги, отсортированные по году издания:");
        sortedBooks.forEach(b -> System.out.println(b.getPublishingYear() + ": " + b.getName() + " - " + b.getAuthor()));
        System.out.println();
    }
}