package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.Command;
import by.epamtc.digapply.command.CommandResult;
import by.epamtc.digapply.command.RequestParameter;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class EditFacultyCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Optional<String> facultyIdParameter = Optional.ofNullable(request.getParameter(RequestParameter.ID));
        if (facultyIdParameter.isPresent()) {
            long facultyId = Long.parseLong(facultyIdParameter.get());
            FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
            Faculty faculty = facultyService.retrieveFacultyById(facultyId);
//            request.setAttribute();
        }
        return null;
    }
}
