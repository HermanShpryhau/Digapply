package by.epamtc.digapply.command.factory;

import by.epamtc.digapply.command.Command;
import by.epamtc.digapply.command.LoginCommand;
import by.epamtc.digapply.command.LogoutCommand;
import by.epamtc.digapply.command.ShowPageCommand;
import by.epamtc.digapply.resource.Commands;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static Map<String, Command> commands = new HashMap<>();

    private static class Holder {
        static final CommandFactory INSTANCE = new CommandFactory();
    }

    private CommandFactory() {
        commands.put(Commands.LOGIN_COMMAND, new LoginCommand());
        commands.put(Commands.LOGOUT_COMMAND, new LogoutCommand());
        commands.put(Commands.SHOW_PAGE_COMMAND, new ShowPageCommand());
    }

    public static CommandFactory getInstance() {
        return Holder.INSTANCE;
    }

    public Command getCommand(String name) {
        return commands.get(name);
    }
}
