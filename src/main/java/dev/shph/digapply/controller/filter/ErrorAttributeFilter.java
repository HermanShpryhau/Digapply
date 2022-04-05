package dev.shph.digapply.controller.filter;


import dev.shph.digapply.controller.command.RequestAttribute;
import dev.shph.digapply.controller.command.SessionAttribute;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ErrorAttributeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        String errorKey = (String) session.getAttribute(SessionAttribute.ERROR_KEY);
        session.removeAttribute(SessionAttribute.ERROR_KEY);
        request.setAttribute(RequestAttribute.ERROR_KEY, errorKey);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
