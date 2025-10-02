package Lab2.Task2;

public class Balance {
    private int leftWeight;
    private int rightWeight;

    public Balance(){
        this.leftWeight = 0;
        this.rightWeight = 0;
    }

    public void addRight(int weight){
        if(weight > 0) {
            rightWeight += weight;
        }
    }

    public void addLeft(int weight){
        if(weight > 0){
            leftWeight += weight;
        }
    }

    public String result(){
        if(leftWeight==rightWeight){
            return "Равновесие";
        }
        else if(rightWeight > leftWeight){
            return "Перевесила правая";
        }
        else {
            return "Перевесила левая";
        }
    }
    public static void main(String[] args){
        Balance balance = new Balance();

        balance.addLeft(10);
        balance.addRight(10);
        System.out.println("Результат 1: " + balance.result());

        balance.addRight(5);
        System.out.println("Результат 2: " + balance.result());

        balance.addLeft(15);
        System.out.println("Результат 3: " + balance.result());
    }
}