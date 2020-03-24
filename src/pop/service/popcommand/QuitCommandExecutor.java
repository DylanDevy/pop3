package pop.service.popcommand;

import maildrop.service.MessageManager;
import pop.entity.Session;
import pop.enumeration.State;

import java.sql.SQLException;

public class QuitCommandExecutor implements PopCommandExecutorInterface {
    private final MessageManager messageManager;

    private QuitCommandExecutor(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    public String execute(String command, Session session) {
        String[] commandParts = command.split(" ");
        if (commandParts.length != 1) {
            return PopCommandExecutor.WRONG_COMMAND_INPUT + " Use: QUIT";
        }

        if (session.getState() == State.AUTHORIZATION) {
            return "+OK";
        }
        session.setState(State.UPDATE);

        try {
            messageManager.updateMessagesDeleted(session.getMaildrop().getMessagesMarkedForDeletion());
        } catch (SQLException e) {
            return "-ERR some deleted messages not removed";
        }
        int messagesLeft = session.getMaildrop().getNotMarkedMessageCount();

        return "+OK POP3 server signing off (maildrop " + (messagesLeft == 0 ? "empty)" : messagesLeft + " messages)");
    }

    public static class Builder {
        private MessageManager messageManager;

        public Builder setMessageManager(MessageManager messageManager) {
            this.messageManager = messageManager;

            return this;
        }

        public QuitCommandExecutor build() {
            return new QuitCommandExecutor(messageManager);
        }
    }
}
