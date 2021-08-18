package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
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
    private static final long DEFAULT_PAGE_NUMBER = 1L;
    private static final String FIRST_PAGE_PARAMETER = "&page=1";
    private static final String SEARCH_PARAMETER = "&search=";

    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> page = Optional.ofNullable(request.getParameter(RequestParameter.PAGE));
        long pageNumber = DEFAULT_PAGE_NUMBER;

        if (page.isPresent()) {
            try {
                pageNumber = Integer.parseInt(page.get());
            } catch (NumberFormatException e) {
                return Routing.ERROR_404;
            }
        }

        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        List<Faculty> facultyList;
        long numberOfPages;
        try {
            Optional<String> searchPatternParam = Optional.ofNullable(request.getParameter(RequestParameter.SEARCH));
            if (searchPatternParam.isPresent() && !searchPatternParam.get().equals("")) {
                String searchPattern = searchPatternParam.get();
                numberOfPages = facultyService.countPagesForSearchResult(searchPattern, ELEMENTS_PER_PAGE);
                if (pageNumber < 1 || pageNumber > numberOfPages) {
                    return new Routing(
                            PagePath.FACULTIES_PAGE_REDIRECT + FIRST_PAGE_PARAMETER + SEARCH_PARAMETER + searchPattern,
                            RoutingType.REDIRECT
                    );
                }
                facultyList = facultyService.searchFaculties(searchPattern, pageNumber, ELEMENTS_PER_PAGE);
            } else {
                numberOfPages = facultyService.countPages(ELEMENTS_PER_PAGE);
                if (pageNumber < 1 || pageNumber > numberOfPages) {
                    return new Routing(PagePath.FACULTIES_PAGE_REDIRECT + FIRST_PAGE_PARAMETER, RoutingType.REDIRECT);
                }
                facultyList = facultyService.retrieveFacultiesByPage(pageNumber, ELEMENTS_PER_PAGE);
            }
        } catch (ServiceException e) {
            logger.error("Unable to retrieve list of faculties. {}", e.getMessage());
            return Routing.ERROR_500;
        }
        request.setAttribute(RequestAttribute.FACULTIES, facultyList);
        request.setAttribute(RequestAttribute.NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(RequestAttribute.PAGE, pageNumber);

        return new Routing(PagePath.FACULTIES_PAGE, RoutingType.FORWARD);
    }
}
