package maildrop.service;

import maildrop.entity.Message;
import maildrop.repository.MessageRepository;

import java.sql.SQLException;
import java.util.List;

public class MessageManager {
    private final MessageRepository messageRepository;

    private MessageManager(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> findAllMessagesByToEmail(String toEmail) throws SQLException {
        return messageRepository.findAllByToEmail(toEmail);
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
