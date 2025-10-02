package Lab2.FileAnalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileAnalyzer {

    public static class AnalysisResult {
        private int lines;
        private int words;
        private int charac;

        public AnalysisResult(int lines, int words, int charac){
            this.lines = lines;
            this.words = words;
            this.charac = charac;
        }

        @Override
        public String toString() {
            return String.format("Строк: %d\nСлов: %d\nСимволов: %d", lines, words, charac);
        }
    }

    public AnalysisResult analyze(String filename) {
        int lineCount = 0;
        int wordCount = 0;
        int charCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lineCount++;

                String[] words = line.trim().split("\\s+");
                if (!line.trim().isEmpty()) {
                    wordCount += words.length;
                }

                for (char c : line.toCharArray()) {
                    if (c != ' ' && c != '\t' && c != '\n' && c != '\r') {
                        charCount++;
                    }
                }
            }

            return new AnalysisResult(lineCount, wordCount, charCount);

        } catch (IOException e) {
            System.out.println("Файл не найден!");
            return new AnalysisResult(0, 0, 0);
        }
    }

    public static void main(String[] args) {
        FileAnalyzer analyzer = new FileAnalyzer();
        AnalysisResult result = analyzer.analyze("D:\\JavaWork\\JavaLabs\\src\\Lab2\\FileAnalyzer\\test.txt");
        System.out.println(result);
    }
}