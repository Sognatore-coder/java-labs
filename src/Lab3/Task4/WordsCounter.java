package Lab3.Task4;
import java.util.*;

public class WordsCounter {
    public static void main(String[] args) {
        String text = "There's grass in the yard, firewood on the grass. Don't chop wood on the grass of the yard!";
        System.out.println(text + "\n");

        Map<String,Integer> wordFreq = countWordFrequency(text);

        System.out.println("Частота встречаемости слов:");
        for(Map.Entry<String, Integer> entry: wordFreq.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static Map<String, Integer> countWordFrequency(String text){
        Map<String, Integer> freqMap = new HashMap<>();

        if(text == null || text.isEmpty()){
            return freqMap;
        }

        String[] words = text.split("\\W+"); // Разбивает строку на отдельные слова. + означает 1 и более символов
        for(String word: words){
            if(!word.isEmpty()){
                String normalWord = word.toLowerCase();

                freqMap.put(normalWord,freqMap.getOrDefault(normalWord, 0) + 1);
            }
        }
        return freqMap;
    }
}