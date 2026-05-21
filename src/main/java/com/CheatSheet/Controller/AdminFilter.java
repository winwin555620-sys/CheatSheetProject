package com.CheatSheet.Controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.CheatSheet.Model.RegisterLoginBean;

// This annotation tells Tomcat to run this filter for the AdminDashboard URL
@WebFilter("/AdminDashboard") 
public class AdminFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {}
    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        // Retrieve the user from the session
        RegisterLoginBean user = (session != null) ? (RegisterLoginBean) session.getAttribute("loggedUser") : null;

     // In AdminFilter.java
        if (user != null && "Admin".equalsIgnoreCase(user.getRoleName())) {
            chain.doFilter(request, response); // Only wai gets through
        } else {
            res.sendRedirect("UserLogin.jsp"); // Everyone else is kicked out
        }
    }
}