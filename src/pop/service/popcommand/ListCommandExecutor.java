package pop.service.popcommand;

import maildrop.entity.Maildrop;
import maildrop.entity.Message;
import pop.entity.Session;
import pop.enumeration.State;

public class ListCommandExecutor implements PopCommandExecutorInterface {
    private ListCommandExecutor() {}

    public String execute(String command, Session session) {
        if (session.getState() != State.TRANSACTION) {
            return PopCommandExecutor.COMMAND_UNAVAILABLE;
        }

        String[] commandParts = command.split(" ");
        if (commandParts.length > 2) {
            return PopCommandExecutor.WRONG_COMMAND_INPUT + " Use: LIST [message_number]";
        }
        Maildrop maildrop = session.getMaildrop();

        return commandParts.length == 2 ? executeListWithArgGiven(maildrop, commandParts[1]) : executeListNoArgGiven(maildrop);
    }

    private String executeListWithArgGiven(Maildrop maildrop, String arg) {
        try {
            int messageNumber = Integer.parseInt(arg);
            if (maildrop.getMessages().size() < messageNumber || messageNumber < 1) {
                return "-ERR no such message";
            } else if (maildrop.getMessagesMarkedForDeletion().contains(maildrop.getMessages().get(messageNumber - 1))) {
                return "-ERR message " + messageNumber + " marked for deletion";
            } else {
                Message message = maildrop.getMessages().get(messageNumber - 1);

                return "+OK " + messageNumber + " " + message.getOctetCount();
            }
        } catch (NumberFormatException e) {
            return "-ERR wrong input, please provide an integer";
        }
    }

    private String executeListNoArgGiven(Maildrop maildrop) {
        int messageNumber;
        StringBuilder response = new StringBuilder("+OK " + maildrop.getNotMarkedMessageCount() + " (" + maildrop.getNotMarkedMessageOctetCount() + " octets)\n");
        for (Message message : maildrop.getMessages()) {
            messageNumber = message.getMessageNumber();
            if (!maildrop.getMessagesMarkedForDeletion().contains(maildrop.getMessages().get(messageNumber - 1))) {
                response.append(messageNumber);
                response.append(" ");
                response.append(message.getOctetCount());
                response.append("\n");
            }
        }
        response.append(".");

        return response.toString();
    }

    public static class Builder {
        public ListCommandExecutor build() {
            return new ListCommandExecutor();
        }
    }
}
