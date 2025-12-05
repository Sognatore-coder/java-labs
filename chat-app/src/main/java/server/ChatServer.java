package server;

import common.Message;
import common.MessageType;
import common.Protocol;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);
    private static final Logger errorLogger = LoggerFactory.getLogger("ERROR_LOGGER");

    private final int port;
    private final ConcurrentHashMap<String, ClientHandler> clients;
    private final CopyOnWriteArrayList<String> userList;
    private ServerSocket serverSocket;
    private boolean running;

    public ChatServer(int port) {
        this.port = port;
        this.clients = new ConcurrentHashMap<>();
        this.userList = new CopyOnWriteArrayList<>();
        this.running = true;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Сервер запущен на порту {}", port);

            while (running) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Новое подключение: {}", clientSocket.getInetAddress());

                // Создаем поток для обработки клиента
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            errorLogger.error("Ошибка сервера: {}", e.getMessage(), e);
        } finally {
            stop();
        }
    }

    public void stop() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            logger.info("Сервер остановлен");
        } catch (IOException e) {
            errorLogger.error("Ошибка при остановке сервера: {}", e.getMessage(), e);
        }
    }

    // Регистрация нового клиента
    public synchronized boolean registerClient(String nickname, ClientHandler handler) {
        if (clients.containsKey(nickname)) {
            return false;
        }
        clients.put(nickname, handler);
        userList.add(nickname);
        logger.info("Пользователь {} зарегистрирован", nickname);
        broadcastUserList();
        return true;
    }

    // Удаление клиента
    public synchronized void removeClient(String nickname) {
        clients.remove(nickname);
        userList.remove(nickname);
        logger.info("Пользователь {} отключился", nickname);
        broadcastUserList();
    }

    // Отправка личного сообщения
    public void sendPrivateMessage(String sender, String recipient, String content) {
        ClientHandler recipientHandler = clients.get(recipient);
        if (recipientHandler != null) {
            Message message = new Message(MessageType.PRIVATE, sender, recipient, content);
            recipientHandler.sendMessage(message);
            logger.info("Личное сообщение от {} к {}: {}", sender, recipient, content);
        } else {
            errorLogger.warn("Получатель {} не найден", recipient);
        }
    }

    // Широковещательное сообщение
    public void broadcastMessage(String sender, String content) {
        Message message = new Message(MessageType.BROADCAST, sender, "ALL", content);
        for (ClientHandler client : clients.values()) {
            client.sendMessage(message);
        }
        logger.info("Широковещательное сообщение от {}: {}", sender, content);
    }

    // Рассылка обновленного списка пользователей
    private void broadcastUserList() {
        String[] users = userList.toArray(new String[0]);
        Message userListMessage = new Message(MessageType.USER_LIST_RESPONSE, "SERVER", "");
        userListMessage.setUserList(users);

        for (ClientHandler client : clients.values()) {
            client.sendMessage(userListMessage);
        }
        logger.debug("Список пользователей обновлен: {}", (Object) users);
    }

    public String[] getUserList() {
        return userList.toArray(new String[0]);
    }
}
