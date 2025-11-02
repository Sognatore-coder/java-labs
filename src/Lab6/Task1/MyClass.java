package Lab6.Task1;

public class MyClass {

    //Публичные методы
    public String publicMet1(){
        return "Public Method 1";
    }
    public int publicMet2(int x,int y){
        return x+y;
    }

    //Защищенные методы
    @Repeat(times = 3)
    protected String protectedMet1(String name){
        return "Protected, " + name;
    }

    @Repeat(times = 2)
    protected int protectedMet2(int a,int b){
        return a*b;
    }

    //Приватные методы
    @Repeat(times = 4)
    private String privateMet1(String message){
        return "Private: " + message.toUpperCase();
    }

    @Repeat(times = 1)
    private int privateMet2(int num){
        return num*num;
    }
}
