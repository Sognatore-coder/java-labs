package Lab3.Task1;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionsTask {
    public static void main(String[] args){
        // Создайте массив из N случайных чисел от 0 до 100.
        int N = 12;
        Integer[] array = new Integer[N];
        Random random = new Random();

        for(int i=0;i < N;i++) {
            array[i] = random.nextInt(101);
        }

        System.out.println("1. Исходный массив: " + Arrays.toString(array));

        // На основе массива создайте список List.
        List<Integer> list = new ArrayList<>(Arrays.asList(array));
        System.out.println("2. Список из массива: " + list);

        // Отсортируйте список по возрастанию.
        Collections.sort(list);
        System.out.println("3. Отсортированный список по возрастанию: " + list);

        // Отсортируйте список в обратном порядке.
        Collections.sort(list, Collections.reverseOrder());
        System.out.println("4. Отсортированный список в обратном порядке: " + list);

        // Перемешайте список.
        Collections.shuffle(list);
        System.out.println("5. Перемешанный список: " + list);

        // Выполните циклический сдвиг на 1 элемент.
        Collections.rotate(list,1);
        System.out.println("6. После циклического сдвига вправо: " + list);

        // Оставьте в списке только уникальные элементы.
        Set<Integer> uniSet = new LinkedHashSet<>(list);
        List<Integer> uniList = new ArrayList<>(uniSet);
        System.out.println("7. Только уникальные элементы: " + uniList);

        // Оставьте в списке только дублирующиеся элементы.
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Integer num : list) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        List<Integer> duplicates = list.stream()
                .filter(num -> frequencyMap.get(num) > 1)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("8. Только дублирующиеся элементы: " + duplicates);

        // Из списка получить массив
        Integer[] newArray = uniList.toArray(new Integer[0]);
        System.out.println("9. Массив из списка: " + Arrays.toString(newArray));

        // Кол-во вхождений каждого числа
        System.out.println("10. Кол-во вхождений каждого числа:");
        Map<Integer,Integer> countMap = new HashMap<>();
        for(Integer num: list){
            countMap.put(num, countMap.getOrDefault(num,0) + 1);
        }
        for(Map.Entry<Integer,Integer> entry : countMap.entrySet()){
            System.out.println("Число " + entry.getKey() + ": " + entry.getValue() + " раз(а)");
        }
    }
}
