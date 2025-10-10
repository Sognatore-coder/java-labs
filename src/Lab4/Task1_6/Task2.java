package Lab4.Task1_6;

import Lab4.ObjClass.Visitor;
import Lab4.ObjClass.Book;
import java.util.List;
import java.util.stream.Collectors;


public class Task2 {
    public static void execute(List<Visitor> visitors) {
        List<Book> allUniqueBooks = visitors.stream()
                .flatMap(v -> v.getFavoriteBooks().stream())
                .distinct()
                .collect(Collectors.toList());

        System.out.println("Уникальные книги в избранном:");
        allUniqueBooks.forEach(b -> System.out.println(b.getName() + " - " + b.getAuthor()));
        System.out.println("Количество уникальных книг: " + allUniqueBooks.size());
        System.out.println();
    }
}