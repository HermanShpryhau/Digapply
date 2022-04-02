package dev.shph.digapply.controller.filter;

import dev.shph.digapply.controller.command.PagePath;
import dev.shph.digapply.controller.command.SessionAttribute;
import dev.shph.digapply.entity.User;
import dev.shph.digapply.entity.UserRole;
import dev.shph.digapply.service.ServiceException;
import dev.shph.digapply.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionValidationFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String username = (String) session.getAttribute(SessionAttribute.USERNAME);
        if (username != null) {
            long userId = (long) session.getAttribute(SessionAttribute.USER_ID);
            UserRole role = (UserRole) session.getAttribute(SessionAttribute.ROLE);
            UserService userService = ServiceFactory.getInstance().getUserService();
            try {
                User user = userService.retrieveUserById(userId);
                if (user == null || role != user.getRole()) {
                    session.invalidate();
                    response.sendRedirect(request.getContextPath() + PagePath.LOGIN_PAGE_REDIRECT);
                    return;
                }
            } catch (ServiceException e) {
                logger.error("Unable to retrieve user {} for validation. {}", userId, e.getMessage());
                response.sendRedirect(request.getContextPath() + PagePath.ERROR_500_PAGE_REDIRECT);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
