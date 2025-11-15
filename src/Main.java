import lsp.*;
import ocp.*;
import isp.*;
import dsp.*;
import srp.ReportManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // ------ S -------
        ReportManager manager = new ReportManager(List.of(5, 10, 15, 20));
        manager.generateReport();

        // ------ O -------
        DiscountCalculator calculator = new DiscountCalculator();
        System.out.println("Regular: " + calculator.calculateDiscount(new RegularDiscount(), 1000));
        System.out.println("VIP: " + calculator.calculateDiscount(new VipDiscount(), 1000));
        System.out.println("Super VIP: " + calculator.calculateDiscount(new SuperVipDiscount(), 1000));

        // ------ L -------
        displayFlyingBird(new Sparrow());
        displayNonFlyingBird(new Penguin()); // Теперь нет исключения


        // ------ I -------
        Printer oldPrinter = new OldPrinter();
        oldPrinter.print("Отчёт за неделю");

        MultiFunctionMachine mfm = new MultiFunctionMachine();
        mfm.print("Документ");
        mfm.scan("Фотография");
        mfm.fax("Договор");


        // ------ D -------
        MessageSender emailSender = new EmailSender();
        NotificationService emailService = new NotificationService(emailSender);
        emailService.send("Ваш заказ готов к выдаче!");


        MessageSender smsSender = new SmsSender();
        NotificationService smsService = new NotificationService(smsSender);
        smsService.send("Ваш код подтверждения: 123456");
    }

    public static void displayFlyingBird(FlyingBird bird) {
        bird.eat();
        bird.fly();
    }

    public static void displayNonFlyingBird(NonFlyingBird bird) {
        bird.eat();
        bird.swim();
    }
}
