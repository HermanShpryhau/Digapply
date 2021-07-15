package by.epamtc.digapply.command.factory;

import by.epamtc.digapply.command.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static Map<String, Command> commands = new HashMap<>();

    private CommandFactory() {
        commands.put(CommandName.LOGIN_COMMAND, new LoginCommand());
        commands.put(CommandName.LOGOUT_COMMAND, new LogoutCommand());
        commands.put(CommandName.SHOW_PAGE_COMMAND, new ShowPageCommand());
        commands.put(CommandName.SIGNUP_COMMAND, new SignUpCommand());
        commands.put(CommandName.PROFILE_COMMAND, new ProfileCommand());
        commands.put(CommandName.SHOW_SIGN_IN_COMMAND, new ShowSignInFormCommand());
        commands.put(CommandName.HOME_COMMAND, new HomeCommand());
    }

    public static CommandFactory getInstance() {
        return Holder.INSTANCE;
    }

    public Command getCommand(String name) {
        return commands.get(name);
    }

    private static class Holder {
        static final CommandFactory INSTANCE = new CommandFactory();
    }
}
