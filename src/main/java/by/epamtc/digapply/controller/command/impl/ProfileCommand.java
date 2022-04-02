package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.CommandName;
import by.epamtc.digapply.controller.command.PagePath;
import by.epamtc.digapply.controller.command.RequestAttribute;
import by.epamtc.digapply.controller.command.SessionAttribute;
import by.epamtc.digapply.entity.Application;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.util.Locale;

@DiscoverableCommand(CommandName.PROFILE_COMMAND)
public class ProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        ApplicationService applicationService = ServiceFactory.getInstance().getApplicationService();
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
                FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
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
