package client;

public class ClientMain {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;

        if (args.length > 0) {
            host = args[0];
        }
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Неверный порт. Использую порт по умолчанию: 12345");
            }
        }

        try {
            ChatClient client = new ChatClient(host, port);
            client.start();
        } catch (Exception e) {
            System.err.println("Не удалось подключиться к серверу: " + e.getMessage());
        }
    }
}
