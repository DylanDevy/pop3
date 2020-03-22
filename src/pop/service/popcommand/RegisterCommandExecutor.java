package pop.service.popcommand;

import pop.entity.Session;
import pop.enumeration.State;
import security.service.EncryptorInterface;
import user.entity.User;
import user.repository.UserRepository;
import user.service.UserManager;

import java.sql.SQLException;

public class RegisterCommandExecutor implements PopCommandExecutorInterface {
    private final EncryptorInterface encryptor;
    private final UserRepository userRepository;
    private final UserManager userManager;

    private RegisterCommandExecutor(
            EncryptorInterface encryptor,
            UserRepository userRepository,
            UserManager userManager
    ) {
        this.encryptor = encryptor;
        this.userRepository = userRepository;
        this.userManager = userManager;
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
            if (userRepository.findByEmail(email) == null) {
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
        User user = new User();
        user.setEmail(email);
        user.setHash(encryptor.encrypt(password));

        return userManager.createUser(user) > 0;
    }

    public static class Builder {
        private EncryptorInterface encryptor;
        private UserRepository userRepository;
        private UserManager userManager;

        public Builder setEncryptor(EncryptorInterface encryptor) {
            this.encryptor = encryptor;

            return this;
        }

        public Builder setUserRepository(UserRepository userRepository) {
            this.userRepository = userRepository;

            return this;
        }

        public Builder setUserManager(UserManager userManager) {
            this.userManager = userManager;

            return this;
        }

        public RegisterCommandExecutor build() {
            return new RegisterCommandExecutor(encryptor, userRepository, userManager);
        }
    }
}
