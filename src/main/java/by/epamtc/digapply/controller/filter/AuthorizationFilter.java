package by.epamtc.digapply.controller.filter;

import by.epamtc.digapply.command.CommandFactory;
import by.epamtc.digapply.entity.RoleEnum;
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
        CommandFactory commandFactory = CommandFactory.getInstance();
        if (!commandFactory.commandExists(command)) {
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
        authorizedCommands.put(RoleEnum.ADMIN.getId(), Arrays.asList(
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
                CommandName.DELETE_SUBJECT_COMMAND,
                CommandName.SHOW_USERS_TABLE_COMMAND,
                CommandName.DELETE_USER_COMMAND,
                CommandName.SHOW_PROFILE_EDIT_FORM_COMMAND,
                CommandName.UPDATE_PROFILE_COMMAND,
                CommandName.CHANGE_PASSWORD_COMMAND,
                CommandName.UPDATE_PASSWORD_COMMAND,
                CommandName.GIVE_ADMIN_RIGHTS_COMMAND,
                CommandName.REVOKE_ADMIN_RIGHTS_COMMAND
        ));
        authorizedCommands.put(RoleEnum.USER.getId(), Arrays.asList(
                CommandName.LOGOUT_COMMAND,
                CommandName.PROFILE_COMMAND,
                CommandName.HOME_COMMAND,
                CommandName.LIST_FACULTIES_COMMAND,
                CommandName.SHOW_FACULTY_COMMAND,
                CommandName.NEW_APPLICATION_COMMAND,
                CommandName.SUBMIT_APPLICATION_COMMAND,
                CommandName.CANCEL_APPLICATION_COMMAND,
                CommandName.SHOW_PROFILE_EDIT_FORM_COMMAND,
                CommandName.UPDATE_PROFILE_COMMAND,
                CommandName.CHANGE_PASSWORD_COMMAND,
                CommandName.UPDATE_PASSWORD_COMMAND
        ));
        authorizedCommands.put(RoleEnum.GUEST.getId(), Arrays.asList(
                CommandName.LOGIN_COMMAND,
                CommandName.SHOW_SIGN_IN_COMMAND,
                CommandName.SIGNUP_COMMAND,
                CommandName.HOME_COMMAND,
                CommandName.LIST_FACULTIES_COMMAND,
                CommandName.SHOW_FACULTY_COMMAND,
                CommandName.SHOW_SIGNUP_COMMAND
        ));
    }
}
