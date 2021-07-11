package by.epamtc.digapply.command;

import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.resource.SessionAttribute;
import by.epamtc.digapply.resource.Page;
import by.epamtc.digapply.resource.RequestParameter;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.UserService;
import by.epamtc.digapply.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class LoginCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();

        String email = request.getParameter(RequestParameter.EMAIL);
        if (email == null || request.getParameter((RequestParameter.PASSWORD)) == null) {
            return new CommandResult(Page.ERROR_PAGE, RoutingType.FORWARD);
        }
        char[] password = request.getParameter(RequestParameter.PASSWORD).toCharArray();

        UserService userService = ServiceFactory.getInstance().getUserService();
        User user = userService.login(email, String.valueOf(password));
        Arrays.fill(password, ' ');
        if (user != null) {
            String username = user.getName() + " " + user.getSurname();
            session.setAttribute(SessionAttribute.USERNAME_ATTRIBUTE, username);
            session.setAttribute(SessionAttribute.ROLE_ATTRIBUTE, user.getRoleId());
        }
        return new CommandResult(Page.HOME_PAGE_REDIRECT, RoutingType.REDIRECT);
    }
}
