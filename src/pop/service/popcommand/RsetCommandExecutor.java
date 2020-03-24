package pop.service.popcommand;

import maildrop.entity.Maildrop;
import maildrop.entity.Message;
import pop.entity.Session;
import pop.enumeration.State;

import java.util.ArrayList;
import java.util.List;

public class RsetCommandExecutor implements PopCommandExecutorInterface {
    private RsetCommandExecutor() {}

    public String execute(String command, Session session) {
        if (session.getState() != State.TRANSACTION) {
            return PopCommandExecutor.COMMAND_UNAVAILABLE;
        }

        String[] commandParts = command.split(" ");
        if (commandParts.length != 1) {
            return PopCommandExecutor.WRONG_COMMAND_INPUT + " Use: RSET";
        }

        resetMessagesMarkedForDeletion(session.getMaildrop());

        return "+OK";
    }

    private void resetMessagesMarkedForDeletion(Maildrop maildrop) {
        List<Message> messagesMarkedForDeletion = maildrop.getMessagesMarkedForDeletion();
        int updatedNotMarkedMessageCount = maildrop.getNotMarkedMessageCount();
        int updatedNotMarkedMessageOctetCount = maildrop.getNotMarkedMessageOctetCount();

        for (Message message : messagesMarkedForDeletion) {
            updatedNotMarkedMessageCount++;
            updatedNotMarkedMessageOctetCount += message.getOctetCount();
        }

        maildrop.setNotMarkedMessageCount(updatedNotMarkedMessageCount);
        maildrop.setNotMarkedMessageOctetCount(updatedNotMarkedMessageOctetCount);
        maildrop.setMessagesMarkedForDeletion(new ArrayList<>());
    }

    public static class Builder {
        public RsetCommandExecutor build() {
            return new RsetCommandExecutor();
        }
    }
}
