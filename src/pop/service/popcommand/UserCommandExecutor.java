package pop.service.popcommand;

import database.entity.UserDetails;
import database.repository.UserDetailsRepository;
import pop.entity.Session;
import pop.enumeration.State;

import java.sql.SQLException;

public class UserCommandExecutor implements PopCommandExecutorInterface {
    private final UserDetailsRepository userDetailsRepository;

    private UserCommandExecutor(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    public String execute(String command, Session session) {
        if (session.getState() != State.AUTHORIZATION) {
            return PopCommandExecutor.COMMAND_UNAVAILABLE;
        }

        String[] commandParts = command.split(" ");
        if (commandParts.length != 2){
            return PopCommandExecutor.WRONG_COMMAND_INPUT + " Use: USER name";
        }
        String name = commandParts[1];

        try {
            UserDetails userDetails = userDetailsRepository.findByName(name);
            if (userDetails == null) {
                return "-ERR haven't heard of " + name;
            }
            session.setUserDetails(userDetails);

            return "+OK hello, " + name;
        } catch (SQLException e) {
            return PopCommandExecutor.SERVER_ERROR_MESSAGE;
        }
    }

    public static class Builder {
        private UserDetailsRepository userDetailsRepository;

        public Builder setUserDetailsRepository(UserDetailsRepository userDetailsRepository) {
            this.userDetailsRepository = userDetailsRepository;

            return this;
        }

        public UserCommandExecutor build() {
            return new UserCommandExecutor(userDetailsRepository);
        }
    }
}
