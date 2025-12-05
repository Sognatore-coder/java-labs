package common;

import java.io.*;

public class Protocol {
    public static void sendMessage(Message message, ObjectOutputStream out) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    public static Message receiveMessage(ObjectInputStream in) throws IOException, ClassNotFoundException {
        return (Message) in.readObject();
    }
}
