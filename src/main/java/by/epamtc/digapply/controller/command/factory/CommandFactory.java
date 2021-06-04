package by.epamtc.digapply.controller.command.factory;

import by.epamtc.digapply.controller.command.Command;
import by.epamtc.digapply.controller.command.LoginCommand;
import by.epamtc.digapply.controller.command.LogoutCommand;
import by.epamtc.digapply.controller.command.ShowPageCommand;

public class CommandFactory {
    private static final String LOGIN = "login";
    private static final String LOGOUT = "logout";
    private static final String SHOW_PAGE = "show-page";

    private static class Holder {
        static final CommandFactory INSTANCE = new CommandFactory();
    }

    private CommandFactory() {}

    public static CommandFactory getInstance() {
        return Holder.INSTANCE;
    }

    public Command create(String commandName) {
        switch (commandName) {
            case LOGIN:
                return new LoginCommand();
            case LOGOUT:
                return new LogoutCommand();
            default:
                return new ShowPageCommand();
        }
    }
}
