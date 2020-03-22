package pop.service.popcommand;

import pop.entity.Session;
import pop.enumeration.State;
import user.entity.User;
import user.repository.UserRepository;

import java.sql.SQLException;

public class UserCommandExecutor implements PopCommandExecutorInterface {
    private final UserRepository userRepository;

    private UserCommandExecutor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String execute(String command, Session session) {
        if (session.getState() != State.AUTHORIZATION) {
            return PopCommandExecutor.COMMAND_UNAVAILABLE;
        }

        String[] commandParts = command.split(" ");
        if (commandParts.length != 2){
            return PopCommandExecutor.WRONG_COMMAND_INPUT + " Use: USER email";
        }
        String email = commandParts[1];

        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                return "-ERR haven't heard of " + email;
            }
            session.setUser(user);

            return "+OK hello, " + email;
        } catch (SQLException e) {
            return PopCommandExecutor.SERVER_ERROR_MESSAGE;
        }
    }

    public static class Builder {
        private UserRepository userRepository;

        public Builder setUserRepository(UserRepository userRepository) {
            this.userRepository = userRepository;

            return this;
        }

        public UserCommandExecutor build() {
            return new UserCommandExecutor(userRepository);
        }
    }
}
