package Lab3.Task2;
import java.util.*;

public class PrimesGenerator implements Iterator<Integer> {
    private int count; // кол-во чисел, которые нужно сгенерировать
    private int generated; // кол-во сгенерированных
    private int current; // текущее число

    public PrimesGenerator(int count){
        this.count = count;
        this.generated = 0;
        this.current = 2;
    }

    private boolean isPrime(int n){
        if(n < 2) return false;
        if(n == 2) return true;
        if(n % 2 == 0) return false;

        for(int i = 3; i*i <= n; i+=2){
            if(n % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasNext() {
        return generated < count; // есть ли ещё числа для генерации
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        while (!isPrime(current)) {
            current++;
        }

        int prime = current;
        current++;
        generated++;
        return prime;
    }
}