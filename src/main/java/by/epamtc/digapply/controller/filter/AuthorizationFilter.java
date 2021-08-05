package by.epamtc.digapply.controller.filter;

import by.epamtc.digapply.entity.Role;
import by.epamtc.digapply.command.SessionAttribute;
import by.epamtc.digapply.command.CommandName;
import by.epamtc.digapply.command.PagePath;

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
import java.util.*;
import java.util.stream.Collectors;

public class AuthorizationFilter implements Filter {
    private final Map<Long, List<String>> authorizedCommands = new HashMap<>();
    private Set<String> availableCommands = new HashSet<>();

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
        if (!availableCommands.contains(command)) {
            request.getRequestDispatcher(PagePath.ERROR_404_PAGE).forward(request, response);
            return;
        }
        if (!authorizedCommands.get(roleId).contains(command)) {
            String requestParameters = buildRequestParametersString(request);
            session.setAttribute(SessionAttribute.PREVIOUS_COMMAND, requestParameters);
            request.getRequestDispatcher(PagePath.LOGIN_PAGE).forward(request, response);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String buildRequestParametersString(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        StringBuilder requestParameters = new StringBuilder();
        for (Map.Entry<String, String[]> parameter : parameters.entrySet()) {
            requestParameters.append(parameter.getKey());
            requestParameters.append('=');
            requestParameters.append(parameter.getValue()[0]);
            requestParameters.append('&');
        }
        requestParameters.deleteCharAt(requestParameters.length() - 1);
        return requestParameters.toString();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private void initCommands() {
        authorizedCommands.put(Role.ADMIN.getId(), Arrays.asList(
                CommandName.LOGOUT_COMMAND,
                CommandName.PROFILE_COMMAND,
                CommandName.HOME_COMMAND,
                CommandName.LIST_FACULTIES_COMMAND,
                CommandName.SHOW_FACULTY_COMMAND,
                CommandName.SHOW_FACULTY_FORM_COMMAND,
                CommandName.UPDATE_FACULTY_COMMAND,
                CommandName.ADD_FACULTY_COMMAND,
                CommandName.DELETE_FACULTY_COMMAND,
                CommandName.SHOW_DASHBOARD_COMMAND,
                CommandName.NEW_APPLICATION_COMMAND,
                CommandName.SUBMIT_APPLICATION_COMMAND,
                CommandName.CANCEL_APPLICATION_COMMAND,
                CommandName.APPROVE_APPLICATION_COMMAND,
                CommandName.SHOW_APPLICATIONS_TABLE_COMMAND,
                CommandName.SHOW_APPLICATION_EDIT_FORM_COMMAND,
                CommandName.UPDATE_APPLICATION_COMMAND,
                CommandName.SHOW_SUBJECTS_TABLE_COMMAND,
                CommandName.ADD_SUBJECT_COMMAND,
                CommandName.NEW_SUBJECT_COMMAND,
                CommandName.EDIT_SUBJECT_COMMAND,
                CommandName.UPDATE_SUBJECT_COMMAND,
                CommandName.DELETE_SUBJECT_COMMAND
        ));
        authorizedCommands.put(Role.USER.getId(), Arrays.asList(
                CommandName.LOGOUT_COMMAND,
                CommandName.PROFILE_COMMAND,
                CommandName.HOME_COMMAND,
                CommandName.LIST_FACULTIES_COMMAND,
                CommandName.SHOW_FACULTY_COMMAND,
                CommandName.NEW_APPLICATION_COMMAND,
                CommandName.SUBMIT_APPLICATION_COMMAND,
                CommandName.CANCEL_APPLICATION_COMMAND
        ));
        authorizedCommands.put(Role.GUEST.getId(), Arrays.asList(
                CommandName.LOGIN_COMMAND,
                CommandName.SHOW_SIGN_IN_COMMAND,
                CommandName.SIGNUP_COMMAND,
                CommandName.HOME_COMMAND,
                CommandName.LIST_FACULTIES_COMMAND,
                CommandName.SHOW_FACULTY_COMMAND,
                CommandName.SHOW_SIGNUP_COMMAND
        ));
        availableCommands = authorizedCommands.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toSet());
    }
}
