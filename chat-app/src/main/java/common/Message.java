package common;

import java.io.Serializable;

public class Message implements Serializable{
    private MessageType type;
    private String sender;
    private String recipient;
    private String content;
    private String[] userList;

    public Message(MessageType type, String sender, String content) {
        this.type = type;
        this.sender = sender;
        this.content = content;
    }

    public Message(MessageType type, String sender, String recipient, String content) {
        this.type = type;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    // Геттеры и сеттеры
    public MessageType getType() { return type; }
    public void setType(MessageType type) { this.type = type; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String[] getUserList() { return userList; }
    public void setUserList(String[] userList) { this.userList = userList; }

    @Override
    public String toString() {
        if (recipient == null || recipient.equals("ALL")) {
            return "[" + sender + " → ALL] : " + content;
        }
        return "[" + sender + " → " + recipient + "] : " + content;
    }
}
