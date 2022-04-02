package dev.shph.digapply.controller.command.impl;

import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestAttribute;
import dev.shph.digapply.controller.command.SessionAttribute;
import dev.shph.digapply.entity.Application;
import dev.shph.digapply.entity.Faculty;
import dev.shph.digapply.service.ApplicationService;
import dev.shph.digapply.service.FacultyService;
import dev.shph.digapply.service.ServiceException;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.util.Locale;

@Component
@DiscoverableCommand(CommandName.PROFILE_COMMAND)
public class ProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private FacultyService facultyService;

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {
            Application application =
                    applicationService.retrieveApplicationByUserId((long) session.getAttribute(SessionAttribute.USER_ID));
            if (application != null) {
                request.setAttribute(RequestAttribute.APPLICATION, application);
                DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT,
                        new Locale((String) session.getAttribute(SessionAttribute.LOCALE)));
                String applyDate = format.format(application.getApplyDate());
                request.setAttribute(RequestAttribute.APPLY_DATE, applyDate);
                if (application.isApproved()) {
                    String approveDate = format.format(application.getApproveDate());
                    request.setAttribute(RequestAttribute.APPROVE_DATE, approveDate);
                }
                Faculty faculty = facultyService.retrieveFacultyById(application.getFacultyId());
                request.setAttribute(RequestAttribute.FACULTY_NAME, faculty.getFacultyName());
                int totalScore = applicationService.calculateTotalScore(application.getId());
                request.setAttribute(RequestAttribute.TOTAL_SCORE, totalScore);
            }
        } catch (ServiceException e) {
            logger.error("Unable to fetch application data. {}", e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
        return new Forward(PagePath.PROFILE_PAGE);
    }
}
