package maildrop.entity;

import java.time.Instant;

public class Message {
    private int id;
    private Integer userIdReceived;
    private Instant dateReceived;
    private String fromEmail;
    private String toEmail;
    private String subject;
    private Instant dateSent;
    private String mimeVersion;
    private String contentType;
    private String charset;
    private String content;
    private boolean deleted;
    private Instant dateDeleted;
    private int octetCount;
    private int messageNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserIdReceived() {
        return userIdReceived;
    }

    public void setUserIdReceived(Integer userIdReceived) {
        this.userIdReceived = userIdReceived;
    }

    public Instant getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Instant dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Instant getDateSent() {
        return dateSent;
    }

    public void setDateSent(Instant dateSent) {
        this.dateSent = dateSent;
    }

    public String getMimeVersion() {
        return mimeVersion;
    }

    public void setMimeVersion(String mimeVersion) {
        this.mimeVersion = mimeVersion;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Instant getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(Instant dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public int getOctetCount() {
        return octetCount;
    }

    public void setOctetCount(int octetCount) {
        this.octetCount = octetCount;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    @Override
    public String toString() {
        return "Received by: " + toEmail +
                "\nReceived date: " + Instant.now() +
                "\nFrom: " + fromEmail +
                "\nTo: " + toEmail +
                "\nSubject: " + subject +
                "\nDate sent: " + dateSent +
                "\nMime version: " + mimeVersion +
                "\nContent type: " + contentType +
                "\nCharset: " + charset +
                "\nContent: " + content +
                "\n.";
    }
}
