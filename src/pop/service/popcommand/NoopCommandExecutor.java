package pop.service.popcommand;

import pop.entity.Session;
import pop.enumeration.State;

public class NoopCommandExecutor implements PopCommandExecutorInterface {
    public String execute(String command, Session session) {
        if (session.getState() != State.TRANSACTION) {
            return PopCommandExecutor.COMMAND_UNAVAILABLE;
        }
        String[] commandParts = command.split(" ");

        return commandParts.length == 1 ? "+OK" : PopCommandExecutor.WRONG_COMMAND_INPUT + " Use: NOOP";
    }

    public static class Builder {
        public NoopCommandExecutor build() {
            return new NoopCommandExecutor();
        }
    }
}
