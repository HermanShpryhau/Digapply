package by.epamtc.digapply.controller.command.impl;

import by.epamtc.digapply.controller.command.CommandName;
import by.epamtc.digapply.controller.command.ErrorKey;
import by.epamtc.digapply.controller.command.PagePath;
import by.epamtc.digapply.controller.command.RequestParameter;
import by.epamtc.digapply.controller.command.SessionAttribute;
import by.epamtc.digapply.service.ServiceException;
import by.epamtc.digapply.service.ServiceFactory;
import by.epamtc.digapply.service.UserService;
import dev.shph.commandeur.Command;
import dev.shph.commandeur.annotation.DiscoverableCommand;
import dev.shph.commandeur.routing.Redirect;
import dev.shph.commandeur.routing.Routing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@DiscoverableCommand(CommandName.UPDATE_PASSWORD_COMMAND)
public class UpdatePasswordCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Routing result(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> password = Optional.ofNullable(request.getParameter(RequestParameter.PASSWORD));
        if (password.isPresent()) {
            UserService userService = ServiceFactory.getInstance().getUserService();
            HttpSession session = request.getSession();
            try {
                if (userService.updatePassword((Long) session.getAttribute(SessionAttribute.USER_ID), password.get())) {
                    session.invalidate();
                    return new Redirect(PagePath.PROFILE_PAGE_REDIRECT);
                } else {
                    request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_PASSWORD);
                    return new Redirect(PagePath.CHANGE_PASSWORD_FORM_PAGE_REDIRECT);
                }
            } catch (ServiceException e) {
                logger.error("Unable to update user's password. {}", e.getMessage());
                return new Redirect(PagePath.ERROR_500_PAGE_REDIRECT);
            }
        }
        request.getSession().setAttribute(SessionAttribute.ERROR_KEY, ErrorKey.INVALID_PASSWORD);
        return new Redirect(PagePath.CHANGE_PASSWORD_FORM_PAGE_REDIRECT);
    }
}
