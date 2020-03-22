package maildrop.entity;

import java.sql.Timestamp;

public class Message {
    private int id;
    private Integer userIdReceived;
    private Timestamp dateReceived;
    private String fromEmail;
    private String toEmail;
    private String subject;
    private Timestamp dateSent;
    private String mimeVersion;
    private String contentType;
    private String charset;
    private String content;
    private boolean deleted;
    private Timestamp dateDeleted;
    private int octetSize;

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

    public Timestamp getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Timestamp dateReceived) {
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

    public Timestamp getDateSent() {
        return dateSent;
    }

    public void setDateSent(Timestamp dateSent) {
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

    public Timestamp getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(Timestamp dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public int getOctetSize() {
        return octetSize;
    }

    public void setOctetSize(int octetSize) {
        this.octetSize = octetSize;
    }
}
