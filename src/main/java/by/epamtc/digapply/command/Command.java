package by.epamtc.digapply.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface Command {

    Routing execute(HttpServletRequest request, HttpServletResponse response);

}
