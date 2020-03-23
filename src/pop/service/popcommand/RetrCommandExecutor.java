package pop.service.popcommand;

import maildrop.entity.Maildrop;
import maildrop.entity.Message;
import pop.entity.Session;
import pop.enumeration.State;

public class RetrCommandExecutor implements PopCommandExecutorInterface {
    public String execute(String command, Session session) {
        if (session.getState() != State.TRANSACTION) {
            return PopCommandExecutor.COMMAND_UNAVAILABLE;
        }

        String[] commandParts = command.split(" ");
        if (commandParts.length != 2){
            return PopCommandExecutor.WRONG_COMMAND_INPUT + " Use: RETR message_number";
        }

        Maildrop maildrop = session.getMaildrop();
        try {
            int messageNumber = Integer.parseInt(commandParts[1]);
            if (maildrop.getMessages().size() < messageNumber || messageNumber < 1) {
                return "-ERR no such message";
            } else if (maildrop.getMessagesMarkedAsDeleted().contains(maildrop.getMessages().get(messageNumber - 1))) {
                return "-ERR message is marked for deletion";
            } else {
                Message message = maildrop.getMessages().get(messageNumber - 1);

                return "+OK " + message.getOctetSize() + " octets\n" + message.toString();
            }
        } catch (NumberFormatException e) {
            return "-ERR wrong input, please provide an integer";
        }
    }

    public static class Builder {
        public RetrCommandExecutor build() {
            return new RetrCommandExecutor();
        }
    }
}
