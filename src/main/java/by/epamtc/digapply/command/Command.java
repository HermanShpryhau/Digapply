package by.epamtc.digapply.command;

import by.epamtc.digapply.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface Command {

    CommandResult execute(HttpServletRequest request, HttpServletResponse response);

}
