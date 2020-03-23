package maildrop.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Maildrop {
    private List<Message> messages;
    private int notMarkedOctetSize;
    private int notMarkedMessageCount;
    private HashSet<Message> messagesMarkedAsDeleted;

    public Maildrop() {
        this.messages = new ArrayList<>();
        this.messagesMarkedAsDeleted = new HashSet<>();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getNotMarkedOctetSize() {
        return notMarkedOctetSize;
    }

    public void setNotMarkedOctetSize(int notMarkedOctetSize) {
        this.notMarkedOctetSize = notMarkedOctetSize;
    }

    public int getNotMarkedMessageCount() {
        return notMarkedMessageCount;
    }

    public void setNotMarkedMessageCount(int notMarkedMessageCount) {
        this.notMarkedMessageCount = notMarkedMessageCount;
    }

    public HashSet<Message> getMessagesMarkedAsDeleted() {
        return messagesMarkedAsDeleted;
    }

    public void setMessagesMarkedAsDeleted(HashSet<Message> messagesMarkedAsDeleted) {
        this.messagesMarkedAsDeleted = messagesMarkedAsDeleted;
    }
}
