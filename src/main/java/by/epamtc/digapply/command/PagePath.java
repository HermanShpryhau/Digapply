package by.epamtc.digapply.command;

/**
 * Contains string constants that represent paths to jsp pages in WEB-INF.
 */
public class PagePath {
    public static final String ERROR_404_PAGE = "WEB-INF/pages/error-404.jsp";
    public static final String ERROR_500_PAGE = "WEB-INF/pages/error-500.jsp";
    public static final String ERROR_PAGE = "WEB-INF/pages/error.jsp";

    public static final String HOME_PAGE = "WEB-INF/pages/home.jsp";
    public static final String HOME_PAGE_REDIRECT = "/controller?command=home";

    public static final String LOGIN_PAGE = "WEB-INF/pages/login.jsp";
    public static final String LOGIN_PAGE_REDIRECT = "/controller?command=show-sign-in";

    public static final String SIGNUP_PAGE = "WEB-INF/pages/signup.jsp";
    public static final String SIGNUP_PAGE_REDIRECT = "/controller?command=show-page&page=WEB-INF/jsp/signup.jsp";

    public static final String PROFILE_PAGE = "WEB-INF/pages/profile.jsp";
    public static final String PROFILE_PAGE_REDIRECT = "/controller?command=profile";

    public static final String FACULTIES_PAGE = "WEB-INF/pages/faculties.jsp";
    public static final String FACULTIES_PAGE_REDIRECT = "/controller?command=list-faculties";

    public static final String FACULTY_DETAIL_PAGE = "WEB-INF/pages/faculty-detail.jsp";
    public static final String FACULTY_DETAIL_PAGE_REDIRECT = "/controller?command=show-faculty&id=";

    public static final String FACULTY_FORM_PAGE = "WEB-INF/pages/faculty-form.jsp";

    public static final String DASHBOARD_PAGE = "WEB-INF/pages/admin-dashboard.jsp";

    private PagePath() {}
}
