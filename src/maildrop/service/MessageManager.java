package maildrop.service;

import database.DatabaseConnection;
import maildrop.entity.Message;
import maildrop.repository.MessageRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class MessageManager {
    private final MessageRepository messageRepository;

    private MessageManager(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> findAllMessagesByToEmail(String toEmail) throws SQLException {
        return messageRepository.findAllByToEmail(toEmail);
    }

    public void updateMessagesDeleted(List<Message> messages) throws SQLException {
        if (messages.isEmpty()) {
            return;
        }

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            throw new SQLException();
        }

        String query = "UPDATE messages " +
                        "SET deleted = ?, " +
                        "date_deleted = ? " +
                        "WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);

        for(Message message : messages) {
            ps.setBoolean(1, true);
            ps.setObject(2, Timestamp.from(Instant.now()));
            ps.setInt(3, message.getId());
            ps.addBatch();
        }
        ps.executeBatch();

        ps.close();
        connection.close();
    }

    public static class Builder {
        private MessageRepository messageRepository;

        public Builder setMessageRepository(MessageRepository messageRepository) {
            this.messageRepository = messageRepository;

            return this;
        }

        public MessageManager build() {
            return new MessageManager(messageRepository);
        }
    }
}
