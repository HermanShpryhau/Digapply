package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.Command;
import by.epamtc.digapply.controller.command.Routing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        return Routing.ERROR_404;
    }
}
