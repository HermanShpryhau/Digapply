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
        commands.put(CommandName.SIGNUP_COMMAND, new SignUpCommand());
        commands.put(CommandName.PROFILE_COMMAND, new ProfileCommand());
        commands.put(CommandName.SHOW_SIGN_IN_COMMAND, new ShowSignInFormCommand());
        commands.put(CommandName.HOME_COMMAND, new HomeCommand());
        commands.put(CommandName.LIST_FACULTIES_COMMAND, new ListFacultiesCommand());
        commands.put(CommandName.SHOW_FACULTY_COMMAND, new ShowFacultyCommand());
        commands.put(CommandName.SHOW_FACULTY_FORM_COMMAND, new ShowFacultyFormCommand());
        commands.put(CommandName.UPDATE_FACULTY_COMMAND, new UpdateFacultyCommand());
        commands.put(CommandName.ADD_FACULTY_COMMAND, new AddFacultyCommand());
        commands.put(CommandName.DELETE_FACULTY_COMMAND, new DeleteFacultyCommand());
        commands.put(CommandName.SHOW_DASHBOARD_COMMAND, new ShowDashboardCommand());
        commands.put(CommandName.NEW_APPLICATION_COMMAND, new NewApplicationCommand());
        commands.put(CommandName.SUBMIT_APPLICATION_COMMAND, new SubmitApplicationCommand());
        commands.put(CommandName.SHOW_SIGNUP_COMMAND, new ShowSignUpCommand());
        commands.put(CommandName.CANCEL_APPLICATION_COMMAND, new CancelApplicationCommand());
        commands.put(CommandName.APPROVE_APPLICATION_COMMAND, new ApproveApplicationCommand());
        commands.put(CommandName.SHOW_APPLICATIONS_TABLE_COMMAND, new ShowApplicationsTableCommand());
        commands.put(CommandName.SHOW_APPLICATION_EDIT_FORM_COMMAND, new EditApplicationCommand());
        commands.put(CommandName.UPDATE_APPLICATION_COMMAND, new UpdateApplicationCommand());
        commands.put(CommandName.SHOW_SUBJECTS_TABLE_COMMAND, new ShowSubjectsTableCommand());
        commands.put(CommandName.ADD_SUBJECT_COMMAND, new AddSubjectCommand());
        commands.put(CommandName.NEW_SUBJECT_COMMAND, new NewSubjectCommand());
        commands.put(CommandName.UPDATE_SUBJECT_COMMAND, new UpdateSubjectCommand());
        commands.put(CommandName.EDIT_SUBJECT_COMMAND, new EditSubjectCommand());
        commands.put(CommandName.DELETE_SUBJECT_COMMAND, new DeleteSubjectCommand());
        commands.put(CommandName.SHOW_USERS_TABLE_COMMAND, new ShowUsersTableCommand());
        commands.put(CommandName.DELETE_USER_COMMAND, new DeleteUserCommand());
    }

    /**
     * Gets instance of {@link CommandFactory}.
     * @return Instance of singleton.
     */
    public static CommandFactory getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Gets {@link Command} implementation for given name.
     * If there is no implementation for this name, default command will be returned.
     * @param name String containing command name passed as parameter to controller
     * @return Command implementation instance.
     */
    public Command getCommand(String name) {
        return Optional.ofNullable(commands.get(name)).orElse(commands.get(CommandName.DEFAULT_COMMAND));
    }

    /**
     * Checks if command with given name exists.
     * @param commandName Name of command.
     * @return Result of containsKey method called on commands map.
     */
    public boolean commandExists(String commandName) {
        return commands.containsKey(commandName);
    }

    private static class Holder {
        static final CommandFactory INSTANCE = new CommandFactory();
    }
}
