package dev.shph.digapply.controller.filter;

import dev.shph.digapply.controller.command.RequestParameter;
import dev.shph.digapply.controller.command.SessionAttribute;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class LocaleFilter implements Filter {
    private static final String DEFAULT_LOCALE_PARAM = "defaultLocale";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        if (request.getSession().getAttribute(SessionAttribute.LOCALE) == null || request.getSession().getAttribute(SessionAttribute.LOCALE).equals("")) {
            session.setAttribute(SessionAttribute.LOCALE, request.getServletContext().getInitParameter(DEFAULT_LOCALE_PARAM));
        }
        Optional<String> requestedLocale = Optional.ofNullable(request.getParameter(RequestParameter.LOCALE));
        if (requestedLocale.isPresent()) {
            session.setAttribute(SessionAttribute.LOCALE, requestedLocale.get());
            String requestString = removeLocaleParameter(request);
            response.sendRedirect(requestString);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String removeLocaleParameter(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder requestString = new StringBuilder(request.getContextPath() + "/controller?");
        parameterMap.entrySet().stream()
                .filter(e -> !RequestParameter.LOCALE.equals(e.getKey()))
                .forEach(e -> requestString.append(e.getKey()).append("=").append(e.getValue()[0]).append("&"));
        requestString.deleteCharAt(requestString.length() - 1);
        return requestString.toString();
    }
}
