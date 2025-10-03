package Lab3.Task3;
import java.util.*;

public class Human implements Comparable<Human>{
    private String firstName;
    private String lastName;
    private int age;

    public Human(String firstName, String lastName, int age){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    // Геттеры
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public int getAge() {return age;}

    // Сравнение  объектов Human
    public int compareTo(Human other){
        int lastNameCompare = this.lastName.compareTo(other.lastName); // 0 - фамилии одинаковы, отриц - текущая фамилия раньше, положит - текущая фамилия позже
        if(lastNameCompare !=0) {
            return lastNameCompare;
        }
        return this.firstName.compareTo(other.firstName);
    }

    // Переопределяем equals и hashCode для корректной работы HashSet
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Human human = (Human) obj;
        return age == human.age &&
                Objects.equals(firstName, human.firstName) &&
                Objects.equals(lastName, human.lastName);
    }
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age);
    }

    @Override
    public String toString(){
        return String.format("%s %s (%d лет)", lastName, firstName, age);
    }
}

