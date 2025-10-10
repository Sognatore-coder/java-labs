package Lab4.Task1_6;

import Lab4.ObjClass.Visitor;
import java.util.List;
import java.util.stream.Collectors;

public class Task4 {
    public static void execute(List<Visitor> visitors) {
        boolean hasJaneAusten = visitors.stream()
                .flatMap(v -> v.getFavoriteBooks().stream())
                .anyMatch(b -> "Jane Austen".equals(b.getAuthor()));

        System.out.println("Есть ли книги Jane Austen в избранном: " + (hasJaneAusten ? "ДА" : "НЕТ"));

        List<Visitor> janeAustenFans = visitors.stream()
                .filter(v -> v.getFavoriteBooks().stream()
                        .anyMatch(b -> "Jane Austen".equals(b.getAuthor())))
                .collect(Collectors.toList());

        System.out.println("Посетители с книгами Jane Austen:");
        janeAustenFans.forEach(v -> System.out.println(v.getName() + " " + v.getSurname()));
        System.out.println();
    }
}