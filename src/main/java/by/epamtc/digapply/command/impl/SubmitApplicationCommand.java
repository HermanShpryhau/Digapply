package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SubmitApplicationCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_ID));
        long facultyId = RequestParameterParser.parsePositiveLong(facultyIdString);
        if (facultyId == RequestParameterParser.INVALID_POSITIVE_LONG) {
            return Routing.ERROR_404;
        }

        Map<String, String[]> parameters = request.getParameterMap();
        Map<String, String> scores = new HashMap<>();
        Map<String, String> certificates = new HashMap<>();
        for (Map.Entry<String, String[]> parameter : parameters.entrySet()) {
            if (parameter.getKey().startsWith(RequestParameter.SUBJECT_ID_PREFIX)) {
                scores.put(parameter.getKey(), parameter.getValue()[0]);
            } else if (parameter.getKey().startsWith(RequestParameter.CERTIFICATE_ID_PREFIX)) {
                certificates.put(parameter.getKey(), parameter.getValue()[0]);
            }
        }

        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
        try {
            long userId = (long) request.getSession().getAttribute(SessionAttribute.USER_ID);
            if (applicationService.saveApplication(userId, facultyId, scores, certificates)) {
                return new Routing(PagePath.PROFILE_PAGE_REDIRECT, RoutingType.REDIRECT);
            } else {
                // TODO set error data
                return Routing.ERROR;
            }
        } catch (ServiceException e) {
            return Routing.ERROR_500;
        }
    }
}
