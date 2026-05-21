package com.CheatSheet.Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.CheatSheet.Model.RegisterLoginBean;
import com.CheatSheet.Repository.UserLoginRepository;

@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // Moved instantiation inside a method or init if you want to be more standard, 
    // but this works fine for small projects.
    UserLoginRepository userRepo = new UserLoginRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Usually, GET on /UserLogin should just show the login page
        request.getRequestDispatcher("UserLogin.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userParam = request.getParameter("username");
        String passParam = request.getParameter("password");

        try {
            RegisterLoginBean loggedUser = userRepo.loginUser(userParam, passParam);

            if (loggedUser != null) {
                // Check if account is active (enabled = 1)
                if (loggedUser.getEnabled() == 0) {
                    request.setAttribute("error", "Account is disabled.");
                    request.getRequestDispatcher("UserLogin.jsp").forward(request, response);
                    return;
                }

                HttpSession session = request.getSession();
                session.setAttribute("loggedUser", loggedUser);

                // Role-based navigation
                if ("Admin".equalsIgnoreCase(loggedUser.getRoleName())) {
                    response.sendRedirect("AdminDashboard");
                } else {
                    response.sendRedirect("Home");
                }
            } else {
            	
                request.setAttribute("error", "Invalid Username or Password.");
                request.getRequestDispatcher("UserLogin.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 1. Set a friendly error message
            request.setAttribute("error", "Database connection error. Please try again later.");
            
            // 2. Use the correct filename (UserLogin.jsp) and forward instead of redirect
            request.getRequestDispatcher("UserLogin.jsp").forward(request, response);
        }
    }
}