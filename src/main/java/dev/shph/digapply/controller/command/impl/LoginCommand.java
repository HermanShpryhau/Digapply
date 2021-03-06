package dev.shph.digapply.controller.command.impl;

import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.ErrorKey;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestParameter;
import dev.shph.digapply.controller.command.SessionAttribute;
import dev.shph.digapply.entity.User;
import dev.shph.digapply.service.ServiceException;
import dev.shph.digapply.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Optional;

@Component(CommandName.LOGIN_COMMAND)
public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String CONTROLLER_COMMAND = "/controller?";
    private static final String DEFAULT_LOCALE_PARAM = "defaultLocale";

    @Autowired
    private UserService userService;

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        String email = request.getParameter(RequestParameter.EMAIL);
        if (email == null || request.getParameter((RequestParameter.PASSWORD)) == null) {
            request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_LOGIN_DATA);
            return new Redirect(PagePath.LOGIN_PAGE_REDIRECT);
        }
        char[] password = request.getParameter(RequestParameter.PASSWORD).toCharArray();

        User user;
        try {
            user = userService.login(email, String.valueOf(password));
        } catch (ServiceException e) {
            logger.error("Unable to test user sign in data. {}", e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
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
            return new Redirect(PagePath.LOGIN_PAGE_REDIRECT);
        }

        Routing routing;
        Optional<String> previousCommand =
                Optional.ofNullable((String) session.getAttribute(SessionAttribute.PREVIOUS_COMMAND));
        if (previousCommand.isPresent()) {
            routing = new Redirect(CONTROLLER_COMMAND + previousCommand.get());
            session.removeAttribute(SessionAttribute.PREVIOUS_COMMAND);
        } else {
            routing = new Redirect(PagePath.HOME_PAGE_REDIRECT);
        }
        return routing;
    }
}
