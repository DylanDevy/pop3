package pop.service.popcommand;

import pop.entity.Session;
import pop.enumeration.State;

public class StatCommandExecutor implements PopCommandExecutorInterface {
    private StatCommandExecutor() {}

    public String execute(String command, Session session) {
        if (session.getState() != State.TRANSACTION) {
            return PopCommandExecutor.COMMAND_UNAVAILABLE;
        }

        String[] commandParts = command.split(" ");
        if (commandParts.length != 1) {
            return PopCommandExecutor.WRONG_COMMAND_INPUT + " Use: STAT";
        }

        return "+OK " + session.getMaildrop().getMessages().size() + " " + session.getMaildrop().getOctetSize();
    }

    public static class Builder {
        public StatCommandExecutor build() {
            return new StatCommandExecutor();
        }
    }
}
