package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ListFacultiesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final long ELEMENTS_PER_PAGE = 4L;

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> page = Optional.ofNullable(request.getParameter(RequestParameter.PAGE));
        long pageNumber = 1L;

        if (page.isPresent()) {
            try {
                pageNumber = Integer.parseInt(page.get());
            } catch (NumberFormatException e) {
                return new CommandResult(PagePath.ERROR_404_PAGE, RoutingType.FORWARD);
            }
        }

        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        List<Faculty> facultyList;
        long numberOfPages;
        try {
            facultyList = facultyService.retrieveFacultiesByPage(pageNumber, ELEMENTS_PER_PAGE);
            numberOfPages = facultyService.countPages(ELEMENTS_PER_PAGE);
        } catch (ServiceException e) {
            logger.error("Unable to retrieve list of faculties.", e);
            return new CommandResult(PagePath.ERROR_500_PAGE, RoutingType.FORWARD);
        }
        request.setAttribute(RequestAttribute.FACULTIES, facultyList);
        request.setAttribute(RequestAttribute.NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(RequestAttribute.PAGE, pageNumber);

        return new CommandResult(PagePath.FACULTIES_PAGE, RoutingType.FORWARD);
    }
}
