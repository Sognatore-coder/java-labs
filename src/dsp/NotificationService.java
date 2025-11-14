package dsp;

public class NotificationService {
    private final MessageSender messageSender;

    public NotificationService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void send(String message) {
        messageSender.send(message);
    }
}
