package by.epamtc.digapply.command;

public class PagePath {
    public static final String ERROR_404_PAGE = "WEB-INF/jsp/error-404.jsp";
    public static final String ERROR_500_PAGE = "WEB-INF/jsp/error-500.jsp";
    public static final String ERROR_PAGE = "WEB-INF/jsp/error.jsp";

    public static final String HOME_PAGE = "WEB-INF/jsp/home.jsp";
    public static final String HOME_PAGE_REDIRECT = "/controller?command=show-page&page=WEB-INF/jsp/home.jsp";

    public static final String LOGIN_PAGE = "WEB-INF/jsp/login.jsp";
    public static final String LOGIN_PAGE_REDIRECT = "/controller?command=show-sign-in";

    public static final String SIGNUP_PAGE = "WEB-INF/jsp/signup.jsp";
    public static final String SIGNUP_PAGE_REDIRECT = "/controller?command=show-page&page=WEB-INF/jsp/signup.jsp";

    public static final String PROFILE_PAGE = "WEB-INF/jsp/profile.jsp";
    public static final String PROFILE_PAGE_REDIRECT = "/controller?command=profile";

    public static final String FACULTIES_PAGE = "WEB-INF/jsp/faculties.jsp";
    public static final String FACULTIES_PAGE_REDIRECT = "/controller?command=list-faculties";

    public static final String FACULTY_DETAIL_PAGE = "WEB-INF/jsp/faculty-detail.jsp";

    private PagePath() {}
}
