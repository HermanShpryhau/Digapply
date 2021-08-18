package by.epamtc.digapply.controller.command;

/**
 * Paths and redirect requests to jsp pages.
 */
public final class PagePath {
    public static final String ERROR_404_PAGE = "WEB-INF/pages/error-404.jsp";
    public static final String ERROR_404_PAGE_REDIRECT = "/controller?command=error-404";
    public static final String ERROR_500_PAGE = "WEB-INF/pages/error-500.jsp";
    public static final String ERROR_500_PAGE_REDIRECT = "/controller?command=error-500";
    public static final String ERROR_PAGE = "WEB-INF/pages/error.jsp";
    public static final String ERROR_PAGE_REDIRECT = "/controller?command=error";

    public static final String HOME_PAGE = "WEB-INF/pages/home.jsp";
    public static final String HOME_PAGE_REDIRECT = "/controller?command=home";

    public static final String LOGIN_PAGE = "WEB-INF/pages/login.jsp";
    public static final String LOGIN_PAGE_REDIRECT = "/controller?command=show-sign-in";

    public static final String SIGNUP_PAGE = "WEB-INF/pages/signup.jsp";

    public static final String PROFILE_PAGE = "WEB-INF/pages/profile.jsp";
    public static final String PROFILE_PAGE_REDIRECT = "/controller?command=profile";

    public static final String FACULTIES_PAGE = "WEB-INF/pages/faculties.jsp";
    public static final String FACULTIES_PAGE_REDIRECT = "/controller?command=list-faculties";

    public static final String FACULTY_DETAIL_PAGE = "WEB-INF/pages/faculty-detail.jsp";
    public static final String FACULTY_DETAIL_PAGE_REDIRECT = "/controller?command=show-faculty&id=";

    public static final String FACULTY_FORM_PAGE = "WEB-INF/pages/faculty-form.jsp";

    public static final String DASHBOARD_PAGE = "WEB-INF/pages/admin-dashboard.jsp";

    public static final String APPLICATION_FORM_PAGE = "WEB-INF/pages/application-form.jsp";
    public static final String ONLY_ONE_APPLICATION_PAGE = "WEB-INF/pages/only-one-app.jsp";

    public static final String APPLICATION_TABLE_PAGE = "WEB-INF/pages/application-table.jsp";
    public static final String APPLICATION_TABLE_PAGE_REDIRECT = "/controller?command=manage-applications";

    public static final String APPLICATION_EDIT_FORM_PAGE = "WEB-INF/pages/application-edit-form.jsp";

    public static final String SUBJECT_TABLE_PAGE = "WEB-INF/pages/subject-table.jsp";
    public static final String SUBJECT_TABLE_PAGE_REDIRECT = "/controller?command=manage-subjects";

    public static final String SUBJECT_FORM_PAGE = "WEB-INF/pages/subject-form.jsp";

    public static final String USER_TABLE_PAGE = "WEB-INF/pages/user-table.jsp";
    public static final String USER_TABLE_PAGE_REDIRECT = "/controller?command=manage-users";

    public static final String PROFILE_EDIT_FORM_PAGE = "WEB-INF/pages/profile-edit-form.jsp";
    public static final String PROFILE_EDIT_FORM_PAGE_REDIRECT = "/controller?command=edit-profile";

    public static final String CHANGE_PASSWORD_FORM_PAGE = "WEB-INF/pages/change-password-form.jsp";
    public static final String CHANGE_PASSWORD_FORM_PAGE_REDIRECT = "/controller?command=change-password";

    public static final String ACCEPTED_APPLICATIONS_TABLE = "WEB-INF/pages/accepted-applications-table.jsp";

    private PagePath() {}
}
