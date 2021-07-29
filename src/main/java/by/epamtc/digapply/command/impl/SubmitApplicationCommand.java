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
    private static final long INVALID_FACULTY_ID = -1;
    private static final String SUBJECT_ID_PREFIX = "sid-";
    private static final String CERTIFICATE_ID_PREFIX = "cid-";

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.FACULTY_ID));
        long facultyId = getFacultyId(facultyIdString);
        if (facultyId == INVALID_FACULTY_ID) {
            return new Routing(PagePath.ERROR_404_PAGE, RoutingType.FORWARD);
        }

        Map<String, String[]> parameters = request.getParameterMap();
        Map<String, String> scores = new HashMap<>();
        Map<String, String> certificates = new HashMap<>();
        for (Map.Entry<String, String[]> parameter : parameters.entrySet()) {
            if (parameter.getKey().startsWith(SUBJECT_ID_PREFIX)) {
                scores.put(parameter.getKey(), parameter.getValue()[0]);
            } else if (parameter.getKey().startsWith(CERTIFICATE_ID_PREFIX)) {
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
                return new Routing(PagePath.ERROR_PAGE, RoutingType.FORWARD);
            }
        } catch (ServiceException e) {
            return new Routing(PagePath.ERROR_500_PAGE, RoutingType.FORWARD);
        }
    }

    private long getFacultyId(Optional<String> facultyIdString) {
        long facultyId = INVALID_FACULTY_ID;
        if (facultyIdString.isPresent()) {
            try {
                facultyId = Long.parseLong(facultyIdString.get());
            } catch (NumberFormatException e) {
                facultyId = INVALID_FACULTY_ID;
            }
        }
        return facultyId;
    }
}