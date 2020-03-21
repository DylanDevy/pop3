package pop.service.popcommand;

import pop.entity.Session;
import pop.enumeration.State;
import security.service.EncryptorInterface;

public class PassCommandExecutor implements PopCommandExecutorInterface {
    private final EncryptorInterface encryptor;

    private PassCommandExecutor(EncryptorInterface encryptor) {
        this.encryptor = encryptor;
    }

    public String execute(String command, Session session) {
        if (session.getState() != State.AUTHORIZATION) {
            return PopCommandExecutor.COMMAND_UNAVAILABLE;
        }

        if (session.getUserDetails() == null) {
            return "-ERR who are you? May want to introduce yourself with USER";
        }

        String[] commandParts = command.split(" ");
        if (commandParts.length != 2) {
            return PopCommandExecutor.WRONG_COMMAND_INPUT + " Use: PASS password";
        }

        if (!encryptor.matches(commandParts[1], session.getUserDetails().getHash())) {
            return "-ERR password is a little off.. or a lot";
        }
        session.setState(State.TRANSACTION);

        return "+OK success";
    }

    public static class Builder {
        private EncryptorInterface encryptor;

        public Builder setEncryptor(EncryptorInterface encryptor) {
            this.encryptor = encryptor;

            return this;
        }

        public PassCommandExecutor build() {
            return new PassCommandExecutor(encryptor);
        }
    }
}
