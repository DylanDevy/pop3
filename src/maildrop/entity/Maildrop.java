package maildrop.entity;

import java.util.ArrayList;
import java.util.List;

public class Maildrop {
    private List<Message> messages;
    private int notMarkedMessageOctetCount;
    private int notMarkedMessageCount;
    private List<Message> messagesMarkedForDeletion;

    public Maildrop() {
        this.messages = new ArrayList<>();
        this.messagesMarkedForDeletion = new ArrayList<>();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getNotMarkedMessageOctetCount() {
        return notMarkedMessageOctetCount;
    }

    public void setNotMarkedMessageOctetCount(int notMarkedMessageOctetCount) {
        this.notMarkedMessageOctetCount = notMarkedMessageOctetCount;
    }

    public int getNotMarkedMessageCount() {
        return notMarkedMessageCount;
    }

    public void setNotMarkedMessageCount(int notMarkedMessageCount) {
        this.notMarkedMessageCount = notMarkedMessageCount;
    }

    public List<Message> getMessagesMarkedForDeletion() {
        return messagesMarkedForDeletion;
    }

    public void setMessagesMarkedForDeletion(List<Message> messagesMarkedForDeletion) {
        this.messagesMarkedForDeletion = messagesMarkedForDeletion;
    }
}
