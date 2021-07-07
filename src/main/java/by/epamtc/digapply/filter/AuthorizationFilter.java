package by.epamtc.digapply.filter;

import by.epamtc.digapply.entity.Role;
import by.epamtc.digapply.resource.Attributes;
import by.epamtc.digapply.resource.Commands;
import by.epamtc.digapply.resource.Pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorizationFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(AuthorizationFilter.class);

    private final Map<Long, List<String>> authorizedCommands = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        initCommands();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        Long roleId = (Long) session.getAttribute(Attributes.ROLE_ATTRIBUTE);
        if (roleId == null) {
            roleId = 3L;
        }

        String command = request.getParameter("command");
        if (!authorizedCommands.get(roleId).contains(command)) {
            request.getRequestDispatcher(Pages.ERROR_PAGE).forward(request, response);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private void initCommands() {
        authorizedCommands.put(Role.ADMIN.getId(), Arrays.asList(Commands.LOGOUT_COMMAND, Commands.SHOW_PAGE_COMMAND));
        authorizedCommands.put(Role.USER.getId(), Arrays.asList(Commands.LOGOUT_COMMAND, Commands.SHOW_PAGE_COMMAND));
        authorizedCommands.put(Role.GUEST.getId(), Arrays.asList(Commands.LOGIN_COMMAND, Commands.SHOW_PAGE_COMMAND));
    }
}
