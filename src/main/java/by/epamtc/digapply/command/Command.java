package by.epamtc.digapply.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface of executable controller command.
 */
@FunctionalInterface
public interface Command {
    /**
     * Called by controller when certain command is called.
     * @param request HttpServletRequest from incoming request
     * @param response HttpServletResponse for incoming request
     * @return Routing to page with routing type
     */
    Routing execute(HttpServletRequest request, HttpServletResponse response);

}
