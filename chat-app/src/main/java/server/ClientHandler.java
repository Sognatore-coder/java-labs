package server;

import common.Message;
import common.MessageType;
import common.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private static final Logger errorLogger = LoggerFactory.getLogger("ERROR_LOGGER");

    private final Socket socket;
    private final ChatServer server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String nickname;
    private boolean connected;

    public ClientHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        this.connected = true;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // Первое сообщение должно содержать никнейм
            Message connectMessage = Protocol.receiveMessage(in);
            if (connectMessage.getType() == MessageType.CONNECT) {
                nickname = connectMessage.getSender();

                if (!server.registerClient(nickname, this)) {
                    sendMessage(new Message(MessageType.DISCONNECT, "SERVER",
                            "Никнейм уже занят. Отключение."));
                    closeConnection();
                    return;
                }

                sendMessage(new Message(MessageType.CONNECT, "SERVER",
                        "Добро пожаловать, " + nickname + "!"));

                // Обработка сообщений от клиента
                while (connected) {
                    Message message = Protocol.receiveMessage(in);
                    processMessage(message);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            if (connected) {
                errorLogger.error("Ошибка обработки клиента {}: {}", nickname, e.getMessage(), e);
            }
        } finally {
            closeConnection();
        }
    }

    private void processMessage(Message message) {
        switch (message.getType()) {
            case BROADCAST:
                server.broadcastMessage(message.getSender(), message.getContent());
                break;

            case PRIVATE:
                server.sendPrivateMessage(message.getSender(),
                        message.getRecipient(), message.getContent());
                break;

            case USER_LIST:
                // Отправляем список пользователей
                Message response = new Message(MessageType.USER_LIST_RESPONSE, "SERVER", "");
                response.setUserList(server.getUserList());
                sendMessage(response);
                break;

            case DISCONNECT:
                closeConnection();
                break;

            default:
                errorLogger.warn("Неизвестный тип сообщения от {}: {}",
                        nickname, message.getType());
        }
    }

    public void sendMessage(Message message) {
        try {
            Protocol.sendMessage(message, out);
        } catch (IOException e) {
            errorLogger.error("Ошибка отправки сообщения клиенту {}: {}",
                    nickname, e.getMessage(), e);
            closeConnection();
        }
    }

    private void closeConnection() {
        if (connected) {
            connected = false;
            if (nickname != null) {
                server.removeClient(nickname);
            }
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null && !socket.isClosed()) socket.close();
                logger.info("Соединение с {} закрыто", nickname != null ? nickname : "клиентом");
            } catch (IOException e) {
                errorLogger.error("Ошибка при закрытии соединения: {}", e.getMessage(), e);
            }
        }
    }
}
