package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.RoleEnum;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class CancelApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> userIdString = Optional.ofNullable(request.getParameter(RequestParameter.USER_ID));
        long userId = RequestParameterParser.parsePositiveLong(userIdString);
        if (userId == RequestParameterParser.INVALID_POSITIVE_INT) {
            return Routing.ERROR_404;
        }
        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
        try {
            if (applicationService.cancelApplication(userId)) {
                if ((long) request.getSession().getAttribute(SessionAttribute.ROLE) == RoleEnum.ADMIN.getId()) {
                    return new Routing(PagePath.APPLICATION_TABLE_PAGE_REDIRECT, RoutingType.REDIRECT);
                }
                return new Routing(PagePath.PROFILE_PAGE_REDIRECT, RoutingType.REDIRECT);
            } else {
                return Routing.ERROR_404;
            }
        } catch (ServiceException e) {
            logger.error("Unable to cancel application of user-{}. {}", userId, e.getMessage());
            return Routing.ERROR_500;
        }
    }
}
