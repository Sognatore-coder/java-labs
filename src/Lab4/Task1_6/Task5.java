package Lab4.Task1_6;

import Lab4.ObjClass.Visitor;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;


// Max кол-во книг добавленных посетителем в избранное
public class Task5 {
    public static void execute(List<Visitor> visitors) {
        OptionalInt maxBooks = visitors.stream()
                .mapToInt(v -> v.getFavoriteBooks().size())
                .max();

        if (maxBooks.isPresent()) {
            System.out.println("Максимальное количество книг у одного посетителя: " + maxBooks.getAsInt());

            List<Visitor> visitorsWithMaxBooks = visitors.stream()
                    .filter(v -> v.getFavoriteBooks().size() == maxBooks.getAsInt())
                    .collect(Collectors.toList());

            System.out.println("Посетители с максимальным количеством книг:");
            visitorsWithMaxBooks.forEach(v ->
                    System.out.println(v.getName() + " " + v.getSurname() + " - " + v.getFavoriteBooks().size() + " книг"));
        }
        System.out.println();
    }
}