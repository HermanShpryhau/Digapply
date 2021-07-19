package by.epamtc.digapply.command;

import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.DaoFactory;
import by.epamtc.digapply.dao.SubjectDao;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.Subject;
import by.epamtc.digapply.resource.Page;
import by.epamtc.digapply.resource.RequestAttribute;
import by.epamtc.digapply.resource.RequestParameter;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ShowFacultyCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Optional<String> facultyIdString = Optional.ofNullable(request.getParameter(RequestParameter.ID));

        if (facultyIdString.isPresent()) {
            long facultyId;
            try {
                facultyId = Long.parseLong(facultyIdString.get());
            } catch (NumberFormatException e) {
                throw new ServiceException("Wrong ID format.", e);
            }
            FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
            try {
                Faculty faculty = facultyService.retrieveFacultyById(facultyId);
                if (faculty != null) {
                    request.setAttribute(RequestAttribute.FACULTY, faculty);
                    SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
                    List<Subject> subjects = subjectDao.findSubjectsByFaculty(facultyId);
                    request.setAttribute(RequestAttribute.FACULTY_SUBJECTS, subjects);
                } else {
                    // TODO Set error parameter
                    return new CommandResult(Page.ERROR_PAGE, RoutingType.FORWARD);
                }
            } catch (ServiceException e) {
                throw new ServiceException("Unable to retrieve faculty", e);
            } catch (DaoException e) {
                logger.error("Unable to retrieve subjects for faculty.", e);
                throw new ServiceException("Unable to retrieve subjects for faculty.", e);
            }
        }

        return new CommandResult(Page.FACULTY_DETAIL_PAGE, RoutingType.FORWARD);
    }
}
