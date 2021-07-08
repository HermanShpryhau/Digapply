package by.epamtc.digapply.resource;

public class Page {
    public static final String HOME_PAGE = "WEB-INF/jsp/home.jsp";
    public static final String HOME_PAGE_REDIRECT = "/controller?command=show-page&page=WEB-INF/jsp/home.jsp";
    public static final String ERROR_PAGE = "WEB-INF/jsp/error.jsp";
    public static final String ERROR_PAGE_REDIRECT = "/controller?command=show-page&page=WEB-INF/jsp/error.jsp";
    public static final String LOGIN_PAGE = "WEB-INF/jsp/login.jsp";
    public static final String LOGIN_PAGE_REDIRECT = "/controller?command=show-page&page=WEB-INF/jsp/login.jsp";

    private Page() {}
}
