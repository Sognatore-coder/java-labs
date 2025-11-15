package isp;

public class OldPrinter implements Printer {

    @Override
    public void print(String doc){
        System.out.println("Печатаю: " + doc);
    }
}
