package by.epamtc.digapply.controller.filter;

import by.epamtc.digapply.entity.Role;
import by.epamtc.digapply.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorizationFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(AuthorizationFilter.class);

    private final Map<Role, List<String>> authorizedCommands = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        initCommands();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Role role = Role.GUEST;
        session.setAttribute("role", role.name());
        if (user != null) {
            if (user.getRoleId() == 1) {
                role = Role.ADMIN;
                session.setAttribute("role", role.name());
            } else if (user.getRoleId() == 2) {
                role = Role.USER;
                session.setAttribute("role", role.name());
            }
        }

        String command = request.getParameter("command");
        if (!authorizedCommands.get(role).contains(command)) {
            if (role == Role.GUEST) {
                request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, servletResponse);
            } else {
                request.setAttribute("errorMessage", "error.notEnoughRights");
                request.getRequestDispatcher("WEB-INF/pages/home.jsp").forward(request, servletResponse);
            }
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private void initCommands() {
        authorizedCommands.put(Role.ADMIN, Arrays.asList("logout", "show-page"));
        authorizedCommands.put(Role.USER, Arrays.asList("logout", "show-page"));
        authorizedCommands.put(Role.GUEST, Arrays.asList("login"));
    }
}
