package dev.shph.digapply.controller.command.impl;

import dev.shph.digapply.controller.command.CommandName;
import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.RequestAttribute;
import dev.shph.digapply.controller.command.RequestParameter;
import dev.shph.digapply.entity.Faculty;
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
import java.util.List;
import java.util.Optional;

@Component
@DiscoverableCommand(CommandName.LIST_FACULTIES_COMMAND)
public class ListFacultiesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final long ELEMENTS_PER_PAGE = 4L;
    private static final long DEFAULT_PAGE_NUMBER = 1L;
    private static final String FIRST_PAGE_PARAMETER = "&page=1";
    private static final String SEARCH_PARAMETER = "&search=";

    @Autowired
    private FacultyService facultyService;

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
