package Lab4.Task1_6;

import Lab4.ObjClass.Visitor;
import java.util.List;

// Список посетителей
public class Task1 {
    public static void execute(List<Visitor> visitors) {
        System.out.println("Список посетителей:");
        visitors.forEach(v -> System.out.println(v.getName() + " " + v.getSurname()));
        System.out.println("Общее количество посетителей: " + visitors.size());
        System.out.println();
    }
}