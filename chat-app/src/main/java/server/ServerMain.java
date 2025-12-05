package server;

public class ServerMain {
    public static void main(String[] args) {
        int port = 12345; // Порт по умолчанию

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Неверный порт. Использую порт по умолчанию: 12345");
            }
        }

        ChatServer server = new ChatServer(port);
        server.start();
    }
}
