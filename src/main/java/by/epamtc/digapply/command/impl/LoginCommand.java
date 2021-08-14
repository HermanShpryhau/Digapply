package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.UserService;
import by.epamtc.digapply.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String CONTROLLER_COMMAND = "/controller?";
    private static final String DEFAULT_LOCALE_PARAM = "defaultLocale";

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        String email = request.getParameter(RequestParameter.EMAIL);
        if (email == null || request.getParameter((RequestParameter.PASSWORD)) == null) {
            request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_LOGIN_DATA);
            return Routing.ERROR;
        }
        char[] password = request.getParameter(RequestParameter.PASSWORD).toCharArray();

        UserService userService = ServiceFactory.getInstance().getUserService();
        User user;
        try {
            user = userService.login(email, String.valueOf(password));
        } catch (ServiceException e) {
            logger.error("Unable to test user sign in data. {}", e.getMessage());
            return Routing.ERROR_500;
        }
        Arrays.fill(password, ' ');
        if (user != null) {
            String username = user.getName() + " " + user.getSurname();
            session.setAttribute(SessionAttribute.USER_ID, user.getId());
            session.setAttribute(SessionAttribute.USERNAME, username);
            session.setAttribute(SessionAttribute.ROLE, user.getRole());
            String currentLocale = (String) session.getAttribute(SessionAttribute.LOCALE);
            if (currentLocale == null) {
                ServletContext context = request.getServletContext();
                String defaultLocale = context.getInitParameter(DEFAULT_LOCALE_PARAM);
                session.setAttribute(SessionAttribute.LOCALE, defaultLocale);
            }
        } else {
            session.setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_LOGIN_DATA);
            return Routing.ERROR;
        }

        Routing routing;
        Optional<String> previousCommand = Optional.ofNullable((String) session.getAttribute(SessionAttribute.PREVIOUS_COMMAND));
        if (previousCommand.isPresent()) {
            routing = new Routing(CONTROLLER_COMMAND + previousCommand.get(), RoutingType.REDIRECT);
            session.removeAttribute(SessionAttribute.PREVIOUS_COMMAND);
        } else {
            routing = new Routing(PagePath.HOME_PAGE_REDIRECT, RoutingType.REDIRECT);
        }
        return routing;
    }
}
