package Lab2.StudGrades;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class StudentGrades {

    public static class Student {
        private String name;
        private List<Integer> grades;
        private double average;

        public Student(String name, List<Integer> grades) {
            this.name = name;
            this.grades = grades;
            this.average = calculateAverage();
        }

        private double calculateAverage() {
            if (grades.isEmpty()) return 0.0;
            int sum = 0;
            for (int grade : grades) {
                sum += grade;
            }
            return (double) sum / grades.size();
        }

        public String getName() { return name; }
        public double getAverage() { return average; }
    }

    private Map<String, Student> students;

    public StudentGrades() {
        this.students = new HashMap<>();
    }

    public Map<String, Student> analyze(String filename) {
        students.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                processStudentLine(line);
            }

        } catch (IOException e) {
            System.out.println("Файл не найден!");
        }

        return students;
    }

    private void processStudentLine(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length < 2) return;

        String name = parts[0];
        List<Integer> grades = new ArrayList<>();

        for (int i = 1; i < parts.length; i++) {
            try {
                int grade = Integer.parseInt(parts[i]);
                grades.add(grade);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка формата оценки для студента: " + name);
            }
        }

        students.put(name, new Student(name, grades));
    }

    public String bestStudent() {
        if (students.isEmpty()) return null;

        return students.values().stream()
                .max(Comparator.comparingDouble(Student::getAverage))
                .get()
                .getName();
    }

    public String worstStudent() {
        if (students.isEmpty()) return null;

        return students.values().stream()
                .min(Comparator.comparingDouble(Student::getAverage))
                .get()
                .getName();
    }

    public void printAnalysis() {
        for (Student student : students.values()) {
            System.out.printf("%s: %.2f\n", student.getName(), student.getAverage());
        }

        System.out.println("\nЛучший студент: " + bestStudent());
        System.out.println("Худший студент: " + worstStudent());
    }

    public static void main(String[] args) {
        StudentGrades analyzer = new StudentGrades();
        analyzer.analyze("D:\\JavaWork\\JavaLabs\\src\\Lab2\\StudGrades\\grades.txt");
        analyzer.printAnalysis();
    }
}