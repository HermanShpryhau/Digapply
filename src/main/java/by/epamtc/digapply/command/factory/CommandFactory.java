package by.epamtc.digapply.command.factory;

import by.epamtc.digapply.command.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static Map<String, Command> commands = new HashMap<>();

    private static class Holder {
        static final CommandFactory INSTANCE = new CommandFactory();
    }

    private CommandFactory() {}

    public static CommandFactory getInstance() {
        return Holder.INSTANCE;
    }

    public Command getCommand(String name) {
        return commands.get(name);
    }
}
