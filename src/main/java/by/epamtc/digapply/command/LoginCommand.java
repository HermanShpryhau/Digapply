package by.epamtc.digapply.command;

import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.resource.Attributes;
import by.epamtc.digapply.resource.Parameters;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.UserService;
import by.epamtc.digapply.service.factory.ServiceFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class LoginCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();

        String email = request.getParameter(Parameters.EMAIL_PARAMETER);
        char[] password = request.getParameter(Parameters.PASSWORD_PARAMETER).toCharArray();

        UserService userService = ServiceFactory.getInstance().getUserService();
        User user = userService.login(email, Arrays.toString(password));
        Arrays.fill(password, ' ');
        if (user != null) {
            String username = user.getName();
            session.setAttribute(Attributes.USERNAME_ATTRIBUTE, username);

            session.setAttribute(Attributes.ROLE_ATTRIBUTE, user.getRoleId() == 1 );
        }

        return new CommandResult(request.getRequestURI(), CommandResultType.REDIRECT);
    }
}
