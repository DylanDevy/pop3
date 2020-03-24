package maildrop.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Maildrop {
    private List<Message> messages;
    private int notMarkedMessageOctetCount;
    private int notMarkedMessageCount;
    private HashSet<Message> messagesMarkedForDeletion;

    public Maildrop() {
        this.messages = new ArrayList<>();
        this.messagesMarkedForDeletion = new HashSet<>();
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

    public HashSet<Message> getMessagesMarkedForDeletion() {
        return messagesMarkedForDeletion;
    }

    public void setMessagesMarkedForDeletion(HashSet<Message> messagesMarkedForDeletion) {
        this.messagesMarkedForDeletion = messagesMarkedForDeletion;
    }
}
