package pop.service.popcommand;

import pop.entity.Session;

public interface PopCommandExecutorInterface {
    String execute(String command, Session session);
}
