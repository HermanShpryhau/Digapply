package dev.shph.digapply.controller.command;

/**
 * Command names as request parameter values.
 */
public class CommandName {
    public static final String DEFAULT_COMMAND = "default";
    public static final String HOME_COMMAND = "home";

    public static final String SHOW_ERROR_PAGE_COMMAND = "error";
    public static final String SHOW_ERROR_404_PAGE_COMMAND = "error-404";
    public static final String SHOW_ERROR_500_PAGE_COMMAND = "error-500";

    public static final String LOGIN_COMMAND = "login";
    public static final String LOGOUT_COMMAND = "logout";
    public static final String SIGNUP_COMMAND = "signup";
    public static final String SHOW_SIGNUP_COMMAND = "show-sign-up";
    public static final String SHOW_SIGN_IN_COMMAND = "show-sign-in";
    public static final String PROFILE_COMMAND = "profile";
    public static final String SHOW_DASHBOARD_COMMAND = "dashboard";

    public static final String LIST_FACULTIES_COMMAND = "list-faculties";
    public static final String SHOW_FACULTY_COMMAND = "show-faculty";
    public static final String SHOW_FACULTY_FORM_COMMAND = "edit-faculty";
    public static final String UPDATE_FACULTY_COMMAND = "update-faculty";
    public static final String ADD_FACULTY_COMMAND = "add-faculty";
    public static final String DELETE_FACULTY_COMMAND = "delete-faculty";
    public static final String CLOSE_APPLICATION_COMMAND = "close-application";
    public static final String SHOW_ACCEPTED_APPLICATIONS_TABLE_COMMAND = "accepted-applications";

    public static final String NEW_APPLICATION_COMMAND = "new-application";
    public static final String SUBMIT_APPLICATION_COMMAND = "submit-application";
    public static final String CANCEL_APPLICATION_COMMAND = "cancel-application";
    public static final String APPROVE_APPLICATION_COMMAND = "approve-application";
    public static final String SHOW_APPLICATIONS_TABLE_COMMAND = "manage-applications";
    public static final String SHOW_APPLICATION_EDIT_FORM_COMMAND = "edit-application";
    public static final String UPDATE_APPLICATION_COMMAND = "update-application";

    public static final String SHOW_SUBJECTS_TABLE_COMMAND = "manage-subjects";
    public static final String ADD_SUBJECT_COMMAND = "add-subject";
    public static final String NEW_SUBJECT_COMMAND = "new-subject";
    public static final String EDIT_SUBJECT_COMMAND = "edit-subject";
    public static final String UPDATE_SUBJECT_COMMAND = "update-subject";
    public static final String DELETE_SUBJECT_COMMAND = "delete-subject";

    public static final String SHOW_USERS_TABLE_COMMAND = "manage-users";
    public static final String DELETE_USER_COMMAND = "delete-user";
    public static final String SHOW_PROFILE_EDIT_FORM_COMMAND = "edit-profile";
    public static final String UPDATE_PROFILE_COMMAND = "update-profile";
    public static final String CHANGE_PASSWORD_COMMAND = "change-password";
    public static final String UPDATE_PASSWORD_COMMAND = "update-password";
    public static final String GIVE_ADMIN_RIGHTS_COMMAND = "give-admin-rights";
    public static final String REVOKE_ADMIN_RIGHTS_COMMAND = "revoke-admin-rights";

    private CommandName() {}
}
