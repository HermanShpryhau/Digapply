package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.MailService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ApproveApplicationCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> applicationIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        long applicationId = RequestParameterParser.parsePositiveLong(applicationIdString);
        if (applicationId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return new Routing(PagePath.ERROR_404_PAGE, RoutingType.FORWARD);
        }
        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
        try {
            if (applicationService.approveApplication(applicationId)) {
                MailService mailService = ServiceFactory.getInstance().getMailService();
                String sessionLocale = (String) request.getSession().getAttribute(SessionAttribute.LOCALE);
                mailService.sendApplicationApprovalMessage(applicationId, sessionLocale);
                return new Routing(PagePath.APPLICATION_TABLE_PAGE_REDIRECT, RoutingType.REDIRECT);
            } else {
                return Routing.ERROR_404;
            }
        } catch (ServiceException e) {
            return Routing.ERROR_500;
        }
    }
}
