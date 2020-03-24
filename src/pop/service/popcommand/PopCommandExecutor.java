package pop.service.popcommand;

import pop.entity.Session;

import java.util.HashMap;
import java.util.Map;

public class PopCommandExecutor {
    static final String SERVER_ERROR_MESSAGE = "-ERR sorry server problem";
    static final String COMMAND_UNAVAILABLE = "-ERR sorry this command is unavailable at this state";
    static final String WRONG_COMMAND_INPUT = "-ERR sorry wrong command input.";

    private final Map<String, PopCommandExecutorInterface> commands;

    private PopCommandExecutor(Map<String, PopCommandExecutorInterface> commands) {
        this.commands = commands;
    }

    private void addCommand(PopCommandExecutorInterface command, String commandKey) {
        commands.put(commandKey, command);
    }

    public String execute(String command, Session session) {
        String parsedCommand = command.split(" ")[0];

        PopCommandExecutorInterface commandHandler = commands.get(parsedCommand.toUpperCase());

        return commandHandler != null ? commandHandler.execute(command, session) : "-ERR sorry no such command found";
    }

    public static class Builder {
        private Map<String, PopCommandExecutorInterface> commands;

        public Builder() {
            commands = new HashMap<>();
        }

        public Builder addPopCommand(PopCommandExecutorInterface command, String commandKey) {
            commands.put(commandKey, command);

            return this;
        }

        public PopCommandExecutor build() {
            return new PopCommandExecutor(commands);
        }
    }
}
