package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewSubjectCommand implements Command {
    @Override
    public Routing execute(HttpServletRequest request, HttpServletResponse response) {
        return new Routing(PagePath.SUBJECT_FORM_PAGE, RoutingType.FORWARD);
    }
}
