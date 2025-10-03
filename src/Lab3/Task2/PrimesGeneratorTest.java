package Lab3.Task2;
import Lab3.Task2.PrimesGenerator;
import java.util.*;

public class PrimesGeneratorTest {
    public static void main(String[] args){
        int N = 10;

        PrimesGenerator generator = new PrimesGenerator(N);
        List<Integer> primes = new ArrayList<>();

        while(generator.hasNext()){
            primes.add(generator.next());
        }

        System.out.println("Первые " + N + " простых чисел в прямом порядке:");
        System.out.println(primes);

        // Вывод в обратном порядке
        System.out.println("Первые " + N + " простых чисел в обратном порядке:");
        List<Integer> reversed = new ArrayList<>(primes);
        Collections.reverse(reversed);
        System.out.println(reversed);
    }
}