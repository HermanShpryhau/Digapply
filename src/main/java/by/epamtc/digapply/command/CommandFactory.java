package by.epamtc.digapply.command;

import by.epamtc.digapply.command.impl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandFactory {
    private static final Map<String, Command> commands = new HashMap<>();

    private CommandFactory() {
        commands.put(CommandName.DEFAULT_COMMAND, new DefaultCommand());
        commands.put(CommandName.LOGIN_COMMAND, new LoginCommand());
        commands.put(CommandName.LOGOUT_COMMAND, new LogoutCommand());
        commands.put(CommandName.SHOW_PAGE_COMMAND, new ShowPageCommand());
        commands.put(CommandName.SIGNUP_COMMAND, new SignUpCommand());
        commands.put(CommandName.PROFILE_COMMAND, new ProfileCommand());
        commands.put(CommandName.SHOW_SIGN_IN_COMMAND, new ShowSignInFormCommand());
        commands.put(CommandName.HOME_COMMAND, new HomeCommand());
        commands.put(CommandName.LIST_FACULTIES_COMMAND, new ListFacultiesCommand());
        commands.put(CommandName.SHOW_FACULTY_COMMAND, new ShowFacultyCommand());
        commands.put(CommandName.SHOW_FACULTY_FORM_COMMAND, new ShowFacultyFormCommand());
        commands.put(CommandName.UPDATE_FACULTY_COMMAND, new UpdateFacultyCommand());
    }

    public static CommandFactory getInstance() {
        return Holder.INSTANCE;
    }

    public Command getCommand(String name) {
        return Optional.ofNullable(commands.get(name)).orElse(commands.get(CommandName.DEFAULT_COMMAND));
    }

    private static class Holder {
        static final CommandFactory INSTANCE = new CommandFactory();
    }
}
