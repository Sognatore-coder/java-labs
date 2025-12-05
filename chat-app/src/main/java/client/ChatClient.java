package client;


import common.Message;
import common.MessageType;
import common.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private static final Logger logger = LoggerFactory.getLogger(ChatClient.class);

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String nickname;
    private boolean connected;
    private Scanner scanner;

    public ChatClient(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        this.scanner = new Scanner(System.in);
        this.connected = true;
    }

    public void start() {
        try {
            // Ввод и отправка никнейма
            System.out.print("Введите ваш никнейм: ");
            nickname = scanner.nextLine();

            Message connectMessage = new Message(MessageType.CONNECT, nickname, "");
            Protocol.sendMessage(connectMessage, out);

            // Получение ответа от сервера
            Message response = Protocol.receiveMessage(in);
            if (response.getType() == MessageType.DISCONNECT) {
                System.out.println(response.getContent());
                closeConnection();
                return;
            }

            System.out.println(response.getContent());

            // Запуск потока для получения сообщений
            Thread receiverThread = new Thread(this::receiveMessages);
            receiverThread.start();

            // Основной цикл отправки сообщений
            while (connected) {
                showMenu();
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        sendBroadcastMessage();
                        break;
                    case "2":
                        sendPrivateMessage();
                        break;
                    case "3":
                        requestUserList();
                        break;
                    case "4":
                        disconnect();
                        return;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Ошибка клиента: {}", e.getMessage(), e);
        } finally {
            closeConnection();
        }
    }

    private void showMenu() {
        System.out.println("\n=== Меню ===");
        System.out.println("1. Отправить сообщение всем");
        System.out.println("2. Отправить личное сообщение");
        System.out.println("3. Показать список пользователей");
        System.out.println("4. Выйти");
        System.out.print("Выберите действие: ");
    }

    private void sendBroadcastMessage() throws IOException {
        System.out.print("Введите сообщение: ");
        String content = scanner.nextLine();

        Message message = new Message(MessageType.BROADCAST, nickname, "ALL", content);
        Protocol.sendMessage(message, out);
        System.out.println("Сообщение отправлено всем пользователям.");
    }

    private void sendPrivateMessage() throws IOException {
        // Сначала запрашиваем список пользователей
        requestUserList();

        System.out.print("Введите имя получателя: ");
        String recipient = scanner.nextLine();

        System.out.print("Введите сообщение: ");
        String content = scanner.nextLine();

        Message message = new Message(MessageType.PRIVATE, nickname, recipient, content);
        Protocol.sendMessage(message, out);
        System.out.println("Личное сообщение отправлено.");
    }

    private void requestUserList() throws IOException {
        Message request = new Message(MessageType.USER_LIST, nickname, "");
        Protocol.sendMessage(request, out);
    }

    private void disconnect() throws IOException {
        Message disconnectMessage = new Message(MessageType.DISCONNECT, nickname, "");
        Protocol.sendMessage(disconnectMessage, out);
        closeConnection();
        System.out.println("Отключение от сервера...");
    }

    private void receiveMessages() {
        try {
            while (connected) {
                Message message = Protocol.receiveMessage(in);

                switch (message.getType()) {
                    case BROADCAST:
                    case PRIVATE:
                        System.out.println("\n" + message.toString());
                        break;

                    case USER_LIST_RESPONSE:
                        System.out.println("\n=== Список пользователей онлайн ===");
                        String[] users = message.getUserList();
                        for (String user : users) {
                            System.out.println("- " + user);
                        }
                        break;

                    case DISCONNECT:
                        System.out.println("\nСервер: " + message.getContent());
                        closeConnection();
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            if (connected) {
                logger.error("Ошибка при получении сообщения: {}", e.getMessage(), e);
            }
        }
    }

    private void closeConnection() {
        if (connected) {
            connected = false;
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null && !socket.isClosed()) socket.close();
                scanner.close();
                logger.info("Соединение закрыто");
            } catch (IOException e) {
                logger.error("Ошибка при закрытии соединения: {}", e.getMessage(), e);
            }
        }
    }
}
