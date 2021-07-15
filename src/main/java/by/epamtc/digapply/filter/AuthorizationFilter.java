package by.epamtc.digapply.filter;

import by.epamtc.digapply.entity.Role;
import by.epamtc.digapply.resource.SessionAttribute;
import by.epamtc.digapply.command.CommandName;
import by.epamtc.digapply.resource.Page;

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
        Long roleId = (Long) session.getAttribute(SessionAttribute.ROLE);
        if (roleId == null) {
            roleId = 3L;
        }

        String command = request.getParameter("command");
        if (!authorizedCommands.get(roleId).contains(command)) {
            session.setAttribute(SessionAttribute.PREVIOUS_COMMAND, command);
            request.getRequestDispatcher(Page.LOGIN_PAGE).forward(request, response);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private void initCommands() {
        authorizedCommands.put(Role.ADMIN.getId(), Arrays.asList(
                CommandName.LOGOUT_COMMAND,
                CommandName.PROFILE_COMMAND,
                CommandName.SHOW_PAGE_COMMAND,
                CommandName.HOME_COMMAND,
                CommandName.LIST_FACULTIES_COMMAND
        ));
        authorizedCommands.put(Role.USER.getId(), Arrays.asList(
                CommandName.LOGOUT_COMMAND,
                CommandName.PROFILE_COMMAND,
                CommandName.SHOW_PAGE_COMMAND,
                CommandName.HOME_COMMAND,
                CommandName.LIST_FACULTIES_COMMAND
        ));
        authorizedCommands.put(Role.GUEST.getId(), Arrays.asList(
                CommandName.LOGIN_COMMAND,
                CommandName.SHOW_SIGN_IN_COMMAND,
                CommandName.SIGNUP_COMMAND,
                CommandName.SHOW_PAGE_COMMAND,
                CommandName.HOME_COMMAND,
                CommandName.LIST_FACULTIES_COMMAND
        ));
    }
}
