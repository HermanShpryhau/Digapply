package by.epamtc.digapply.command.impl;

import by.epamtc.digapply.command.Command;
import by.epamtc.digapply.command.CommandResult;
import by.epamtc.digapply.command.PagePath;
import by.epamtc.digapply.command.RoutingType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return new CommandResult(PagePath.PROFILE_PAGE, RoutingType.FORWARD);
    }
}
