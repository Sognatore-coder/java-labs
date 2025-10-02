package Lab2.Task1;

public class Button {

    private int CountClick;

    public Button(){
        this.CountClick = 0;
    }

    public void click(){
        CountClick++;
        System.out.println("Текущее кол-во кликов: " + CountClick);
    }

    public static void main(String[] args){
        Button myBtn = new Button();

        myBtn.click();
        myBtn.click();
        myBtn.click();
    }
}