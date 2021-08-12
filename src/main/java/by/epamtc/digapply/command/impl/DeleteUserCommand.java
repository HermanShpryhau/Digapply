package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class DeleteUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> userIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long userId = RequestParameterParser.parsePositiveLong(userIdString);
        if (userId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return Routing.ERROR_404;
        }
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            if (userService.deleteUser(userId)) {
                return new Routing(PagePath.USER_TABLE_PAGE_REDIRECT, RoutingType.REDIRECT);
            } else {
                request.setAttribute(RequestAttribute.ERROR_KEY, ErrorKey.NO_SUCH_USER);
                return Routing.ERROR;
            }
        } catch (ServiceException e) {
            logger.error("Unable to delete user {}. {}", userId, e.getMessage());
            return Routing.ERROR_500;
        }
    }
}
