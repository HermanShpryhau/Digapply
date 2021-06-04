package by.epamtc.digapply.controller.command;

import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.UserService;
import by.epamtc.digapply.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final String LOGIN_PAGE = "WEB-INF/jsp/login.jsp";
    private static final String HOME_PAGE = "WEB-INF/jsp/home.jsp";

    public LoginCommand () {}

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        UserService userService = ServiceFactory.getInstance().getUserService();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Optional<User> optionalUser = userService.login(email, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return CommandResult.redirect(request.getContextPath() + "/controller?command=show-page&page=" + HOME_PAGE);
        } else {
            return CommandResult.forward(LOGIN_PAGE);
        }
    }
}
