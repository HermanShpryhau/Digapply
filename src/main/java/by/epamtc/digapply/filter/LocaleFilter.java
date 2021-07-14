package by.epamtc.digapply.filter;

import by.epamtc.digapply.resource.RequestParameter;
import by.epamtc.digapply.resource.SessionAttribute;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@WebFilter(filterName = "locale-filter", urlPatterns = "/*")
public class LocaleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Optional<String> sessionLocale = Optional.ofNullable(request.getParameter(RequestParameter.LOCALE));
        if (sessionLocale.isPresent()) {
            request.getSession().setAttribute(SessionAttribute.LOCALE, sessionLocale.get());
            String requestString = buildRequestString(request);
            response.sendRedirect(requestString);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String buildRequestString(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder requestString = new StringBuilder(request.getContextPath() + "/controller?");
        parameterMap.entrySet().stream()
                .filter(e -> !RequestParameter.LOCALE.equals(e.getKey()))
                .forEach(e -> requestString.append(e.getKey()).append("=").append(e.getValue()[0]).append("&"));
        requestString.deleteCharAt(requestString.length() - 1);
        return requestString.toString();
    }
}
