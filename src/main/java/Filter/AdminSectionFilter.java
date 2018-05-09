package Filter;

import Enums.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/secured/teacher-section/admin-section/*")
public class AdminSectionFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String welcomeURL = request.getContextPath() + "/secured/welcome.xhtml";

        boolean loggedIn = (session != null) && (session.getAttribute("email") != null) && session.getAttribute("role") == Role.ADMIN;
        if(loggedIn) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect(welcomeURL);
        }
    }

    public void destroy() {
    }
}