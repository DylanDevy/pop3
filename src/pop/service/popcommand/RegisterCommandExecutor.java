package pop.service.popcommand;

import database.entity.UserDetails;
import database.manager.UserDetailsManager;
import database.repository.UserDetailsRepository;
import pop.entity.Session;
import pop.enumeration.State;
import security.service.EncryptorInterface;

import java.sql.SQLException;

public class RegisterCommandExecutor implements PopCommandExecutorInterface {
    private final EncryptorInterface encryptor;
    private final UserDetailsRepository userDetailsRepository;
    private final UserDetailsManager userDetailsManager;

    private RegisterCommandExecutor(
            EncryptorInterface encryptor,
            UserDetailsRepository userDetailsRepository,
            UserDetailsManager userDetailsManager
    ) {
        this.encryptor = encryptor;
        this.userDetailsRepository = userDetailsRepository;
        this.userDetailsManager = userDetailsManager;
    }

    public String execute(String command, Session session) {
        if (session.getState() != State.AUTHORIZATION) {
            return PopCommandExecutor.COMMAND_UNAVAILABLE;
        }

        String[] commandParts = command.split(" ");
        if (commandParts.length != 3) {
            return PopCommandExecutor.WRONG_COMMAND_INPUT + " Use: REGISTER email password";
        }
        String email = commandParts[1];

        try {
            if (userDetailsRepository.findByEmail(email) == null) {
                return registerUser(email, commandParts[2])
                        ? "+OK user " + email + " registered successfully"
                        : PopCommandExecutor.SERVER_ERROR_MESSAGE;
            }

            return "-ERR hmm it seems that I already know " + email;
        } catch (SQLException e) {
            return PopCommandExecutor.SERVER_ERROR_MESSAGE;
        }
    }

    private boolean registerUser(String email, String password) throws SQLException {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(email);
        userDetails.setHash(encryptor.encrypt(password));

        return userDetailsManager.createUserDetails(userDetails) > 0;
    }

    public static class Builder {
        private EncryptorInterface encryptor;
        private UserDetailsRepository userDetailsRepository;
        private UserDetailsManager userDetailsManager;

        public Builder setEncryptor(EncryptorInterface encryptor) {
            this.encryptor = encryptor;

            return this;
        }

        public Builder setUserDetailsRepository(UserDetailsRepository userDetailsRepository) {
            this.userDetailsRepository = userDetailsRepository;

            return this;
        }

        public Builder setUserDetailsManager(UserDetailsManager userDetailsManager) {
            this.userDetailsManager = userDetailsManager;

            return this;
        }

        public RegisterCommandExecutor build() {
            return new RegisterCommandExecutor(encryptor, userDetailsRepository, userDetailsManager);
        }
    }
}
