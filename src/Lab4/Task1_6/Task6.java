package Lab4.Task1_6;

import Lab4.ObjClass.Visitor;
import Lab4.ObjClass.SmsMessage;
import java.util.List;
import java.util.stream.Collectors;

public class Task6 {
    public static void execute(List<Visitor> visitors) {

        double averageBooks = visitors.stream()
                .mapToInt(v -> v.getFavoriteBooks().size())
                .average()
                .orElse(0.0);

        System.out.println("Среднее количество книг на посетителя: " + averageBooks);

        List<SmsMessage> smsMessages = visitors.stream()
                .filter(Visitor::isSubscribed)
                .map(v -> {
                    int bookCount = v.getFavoriteBooks().size();
                    String message;

                    if (bookCount > averageBooks) {
                        message = "you are a bookworm";
                    } else if (bookCount < averageBooks) {
                        message = "read more";
                    } else {
                        message = "fine";
                    }

                    return new SmsMessage(v.getPhone(), message);
                })
                .collect(Collectors.toList());

        System.out.println("SMS сообщения для подписанных пользователей:");
        smsMessages.forEach(sms ->
                System.out.println("Телефон: " + sms.getPhoneNumber() + " | Сообщение: " + sms.getMessage()));
    }
}