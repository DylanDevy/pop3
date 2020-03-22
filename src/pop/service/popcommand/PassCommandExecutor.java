package pop.service.popcommand;

import maildrop.service.MaildropManager;
import pop.entity.Session;
import pop.enumeration.State;
import security.service.EncryptorInterface;

import java.sql.SQLException;

public class PassCommandExecutor implements PopCommandExecutorInterface {
    private final EncryptorInterface encryptor;
    private final MaildropManager maildropManager;

    private PassCommandExecutor(EncryptorInterface encryptor, MaildropManager maildropManager) {
        this.encryptor = encryptor;
        this.maildropManager = maildropManager;
    }

    public String execute(String command, Session session) {
        if (session.getState() != State.AUTHORIZATION) {
            return PopCommandExecutor.COMMAND_UNAVAILABLE;
        }

        if (session.getUser() == null) {
            return "-ERR who are you? May want to introduce yourself with USER";
        }

        String[] commandParts = command.split(" ");
        if (commandParts.length != 2) {
            return PopCommandExecutor.WRONG_COMMAND_INPUT + " Use: PASS password";
        }

        if (!encryptor.matches(commandParts[1], session.getUser().getHash())) {
            return "-ERR password is a little off.. or a lot";
        }
        try {
            session.setMaildrop(maildropManager.getUserMaildrop(session.getUser()));
            session.setState(State.TRANSACTION);
        } catch (SQLException e) {
            return PopCommandExecutor.SERVER_ERROR_MESSAGE;
        }

        return "+OK success, your messages are ready";
    }

    public static class Builder {
        private EncryptorInterface encryptor;
        private MaildropManager maildropManager;

        public Builder setEncryptor(EncryptorInterface encryptor) {
            this.encryptor = encryptor;

            return this;
        }

        public Builder setMaildropManager(MaildropManager maildropManager) {
            this.maildropManager = maildropManager;

            return this;
        }

        public PassCommandExecutor build() {
            return new PassCommandExecutor(encryptor, maildropManager);
        }
    }
}
