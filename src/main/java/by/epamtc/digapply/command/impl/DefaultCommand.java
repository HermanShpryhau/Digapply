package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.Command;
import by.epamtc.digapply.command.Routing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Forwards request to error page for error 404
 */
public class DefaultCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        return Routing.ERROR_404;
    }
}
