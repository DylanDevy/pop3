package maildrop.entity;

import java.util.List;

public class Maildrop {
    private List<Message> messages;
    private int octetSize;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getOctetSize() {
        return octetSize;
    }

    public void setOctetSize(int octetSize) {
        this.octetSize = octetSize;
    }
}
