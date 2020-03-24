package pop.service.popcommand;

import maildrop.entity.Maildrop;
import maildrop.entity.Message;
import pop.entity.Session;
import pop.enumeration.State;

import java.util.HashSet;

public class DeleCommandExecutor implements PopCommandExecutorInterface {
    private DeleCommandExecutor() {}

    public String execute(String command, Session session) {
        if (session.getState() != State.TRANSACTION) {
            return PopCommandExecutor.COMMAND_UNAVAILABLE;
        }

        String[] commandParts = command.split(" ");
        if (commandParts.length != 2) {
            return PopCommandExecutor.WRONG_COMMAND_INPUT + " Use: DELE [message_number]";
        }
        Maildrop maildrop = session.getMaildrop();

        try {
            int messageNumber = Integer.parseInt(commandParts[1]);
            if (maildrop.getMessages().size() < messageNumber || messageNumber < 1) {
                return "-ERR no such message";
            } else if (maildrop.getMessagesMarkedForDeletion().contains(maildrop.getMessages().get(messageNumber - 1))) {
                return "-ERR message " + messageNumber + " already marked for deletion";
            } else {
                markMessageForDeletion(maildrop, messageNumber);

                return "+OK message " + messageNumber + " marked for deletion";
            }
        } catch (NumberFormatException e) {
            return "-ERR wrong input, please provide an integer";
        }
    }

    private void markMessageForDeletion(Maildrop maildrop, int messageNumber) {
        Message message = maildrop.getMessages().get(messageNumber - 1);

        int updatedNotMarkedMessageCount = maildrop.getNotMarkedMessageCount() - 1;
        int updatedNotMarkedMessageOctetCount = maildrop.getNotMarkedMessageOctetCount() - message.getOctetCount();
        HashSet<Message> updatedMessagesMarkedForDeletion = maildrop.getMessagesMarkedForDeletion();
        updatedMessagesMarkedForDeletion.add(message);

        maildrop.setNotMarkedMessageCount(updatedNotMarkedMessageCount);
        maildrop.setNotMarkedMessageOctetCount(updatedNotMarkedMessageOctetCount);
        maildrop.setMessagesMarkedForDeletion(updatedMessagesMarkedForDeletion);
    }

    public static class Builder {
        public DeleCommandExecutor build() {
            return new DeleCommandExecutor();
        }
    }
}
