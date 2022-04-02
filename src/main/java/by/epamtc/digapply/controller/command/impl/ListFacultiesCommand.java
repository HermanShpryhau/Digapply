package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;
import by.epamtc.digapply.model.Faculty;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.routing.Forward;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@DiscoverableCommand(CommandName.LIST_FACULTIES_COMMAND)
public class ListFacultiesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final long ELEMENTS_PER_PAGE = 4L;
    private static final long DEFAULT_PAGE_NUMBER = 1L;
    private static final String FIRST_PAGE_PARAMETER = "&page=1";
    private static final String SEARCH_PARAMETER = "&search=";

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> page = Optional.ofNullable(request.getParameter(RequestParameter.PAGE));
        long pageNumber = DEFAULT_PAGE_NUMBER;

        if (page.isPresent()) {
            try {
                pageNumber = Integer.parseInt(page.get());
            } catch (NumberFormatException e) {
                return new Redirect(PagePath.ERROR_404_PAGE_REDIRECT);
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
                    return new Redirect(
                            PagePath.FACULTIES_PAGE_REDIRECT + FIRST_PAGE_PARAMETER + SEARCH_PARAMETER + searchPattern
                    );
                }
                facultyList = facultyService.searchFaculties(searchPattern, pageNumber, ELEMENTS_PER_PAGE);
            } else {
                numberOfPages = facultyService.countPages(ELEMENTS_PER_PAGE);
                if (pageNumber < 1 || pageNumber > numberOfPages) {
                    return new Redirect(PagePath.FACULTIES_PAGE_REDIRECT + FIRST_PAGE_PARAMETER);
                }
                facultyList = facultyService.retrieveFacultiesByPage(pageNumber, ELEMENTS_PER_PAGE);
            }
        } catch (ServiceException e) {
            logger.error("Unable to retrieve list of faculties. {}", e.getMessage());
            return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
        }
        request.setAttribute(RequestAttribute.FACULTIES, facultyList);
        request.setAttribute(RequestAttribute.NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(RequestAttribute.PAGE, pageNumber);

        return new Forward(PagePath.FACULTIES_PAGE);
    }
}
