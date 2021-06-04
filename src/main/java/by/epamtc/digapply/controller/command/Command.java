package by.epamtc.digapply.controller.command;

import by.epamtc.digapply.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException;

}
