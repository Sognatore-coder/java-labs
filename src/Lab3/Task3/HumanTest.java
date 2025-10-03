package Lab3.Task3;
import java.util.*;

public class HumanTest {
    public static void main(String[] args) {
        // 1. Создайте список объектов Human
        List<Human> humans = Arrays.asList(
                new Human("Иван", "Иванов", 25),
                new Human("Петр", "Петров", 30),
                new Human("Мария", "Сидорова", 22),
                new Human("Анна", "Иванова", 25),
                new Human("Иван", "Петров", 35)
        );

        System.out.println("1. Исходный список:");
        humans.forEach(System.out::println);

        // 2. Положите список в HashSet и выведите содержимое
        Set<Human> hashSet = new HashSet<>(humans);
        System.out.println("\n2. HashSet:");
        hashSet.forEach(System.out::println);

        // 3. Положите список в LinkedHashSet и выведите содержимое
        Set<Human> linkedHashSet = new LinkedHashSet<>(humans);
        System.out.println("\n3. LinkedHashSet:");
        linkedHashSet.forEach(System.out::println);

        // 4. Положите список в TreeSet и выведите содержимое
        Set<Human> treeSet = new TreeSet<>(humans);
        System.out.println("\n4. TreeSet (сортировка по Comparable):");
        treeSet.forEach(System.out::println);

        // 5. Создайте TreeSet с компаратором HumanComparatorByLastName
        Set<Human> treeSetByLastName = new TreeSet<>(new HumanComparatorByLastName());
        treeSetByLastName.addAll(humans);
        System.out.println("\n5. TreeSet с компаратором по фамилии:");
        treeSetByLastName.forEach(System.out::println);

        // 6. Создайте TreeSet с анонимным компаратором по возрасту
        Set<Human> treeSetByAge = new TreeSet<>(new Comparator<Human>() {
            @Override
            public int compare(Human h1, Human h2) {
                return Integer.compare(h1.getAge(), h2.getAge());
            }
        });
        treeSetByAge.addAll(humans);
        System.out.println("\n6. TreeSet с анонимным компаратором по возрасту:");
        treeSetByAge.forEach(System.out::println);

        // 7. Объяснение различий в выводах коллекций
        System.out.println("\n7. Объяснение различий:");
        System.out.println("""
            - HashSet: не гарантирует порядок элементов, основан на хэш-кодах
            - LinkedHashSet: сохраняет порядок вставки элементов
            - TreeSet (Comparable): сортирует элементы согласно natural ordering (по фамилии, затем по имени)
            - TreeSet (по фамилии): сортирует только по фамилии, игнорируя имя и возраст
            - TreeSet (по возрасту): сортирует только по возрасту
            """);
    }
}