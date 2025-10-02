package Lab2.Task3;

import java.util.ArrayList;
import java.util.List;

public class OddEvenSeparator {
    private List<Integer> numbers; // Общий список чисел
    private List<Integer> evenNumbers; // Список четных чисел
    private List<Integer> oddNumbers; // Список нечетных чисел

    // Конструктор
    public OddEvenSeparator() {
        this.numbers = new ArrayList<>();
        this.evenNumbers = new ArrayList<>();
        this.oddNumbers = new ArrayList<>();
    }


    public void addNumber(int number) {
        numbers.add(number);


        if (number % 2 == 0) {
            evenNumbers.add(number);
        } else {
            oddNumbers.add(number);
        }
    }


    public void even() {
        System.out.println("Четные числа: " + evenNumbers);
    }

    public void odd() {
        System.out.println("Нечетные числа: " + oddNumbers);
    }



    public static void main(String[] args) {
        OddEvenSeparator separator = new OddEvenSeparator();

        separator.addNumber(5);
        separator.addNumber(8);
        separator.addNumber(2);
        separator.addNumber(4);
        separator.addNumber(9);
        separator.addNumber(1);


        separator.even();
        separator.odd();


        separator.addNumber(11);
        separator.addNumber(14);

        separator.even();
        separator.odd();
    }
}