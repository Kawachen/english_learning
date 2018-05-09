package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/secured/*")
public class AuthorizationFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String loginURL = request.getContextPath() + "/login.xhtml";

        boolean loggedIn = (session != null) && (session.getAttribute("email") != null);
        boolean loginRequest = request.getRequestURI().equals(loginURL);
        if (loggedIn || loginRequest) {
            filterChain.doFilter(request, response); // So, just continue request.
        } else {
            response.sendRedirect(loginURL);
        }
    }

    public void destroy() {
    }
}
