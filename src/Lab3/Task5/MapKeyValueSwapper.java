package Lab3.Task5;
import java.util.*;

public class MapKeyValueSwapper {
    public static void main(String[] args) {
        Map<String, Integer> origMap1 = new HashMap<>();
        origMap1.put("cherry", 1);
        origMap1.put("banana", 2);
        origMap1.put("orange", 3);
        origMap1.put("apple", 4);
        origMap1.put("melon", 2); // выдаст исключение, т.к 2 уже существует

        System.out.println("Оригинальный Map: " + origMap1);
        Map<Integer,String> swappedMap1 = swapKeyAndVal(origMap1);
        System.out.println("После замены: " + swappedMap1);
    }

    public static <K,V> Map<V,K> swapKeyAndVal(Map<K,V> orig){
        Map<V,K> swappedMap = new HashMap<>();

        for(Map.Entry<K,V> entry: orig.entrySet()){
            if(swappedMap.containsKey(entry.getValue())){
                throw new IllegalArgumentException("Нельзя поменять местами ключи и значения: значение '" + entry.getValue() + "' встречается несколько раз");
            }
            swappedMap.put(entry.getValue(), entry.getKey());
        }
        return swappedMap;
    }
}