package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.*;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListFacultiesCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        List<Faculty> facultyList = facultyService.retrieveAllFaculties();
        request.setAttribute(RequestAttribute.FACULTIES, facultyList);
        return new CommandResult(PagePath.FACULTIES_PAGE, RoutingType.FORWARD);
    }
}
