package isp;

public class MultiFunctionMachine implements Printer, Scanner, FaxMachine{

    @Override
    public void print(String doc){
        System.out.println("Печатаю: " + doc);
    }

    @Override
    public void scan(String doc){
        System.out.println("Сканирую: " + doc);
    }

    @Override
    public void fax(String doc){
        System.out.println("Факс: " + doc);
    }
}
