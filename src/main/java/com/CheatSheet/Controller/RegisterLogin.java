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
import com.CheatSheet.Repository.RegisterLoginRepository;


@WebServlet("/RegisterLogin")
public class RegisterLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	RegisterLoginRepository userRepo = new RegisterLoginRepository();
    public RegisterLogin() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String username = request.getParameter("username");
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    
	    try {
	        // CHANGE THIS LINE: change 'user' to 'loggedUser'
	        RegisterLoginBean loggedUser = userRepo.validateUser(username, email, password);
	        
	        if (loggedUser != null) {
	            HttpSession session = request.getSession();
	            session.setAttribute("loggedUser", loggedUser);

	            // Now 'loggedUser' exists and this check will work!
	            if ("Admin".equalsIgnoreCase(loggedUser.getRoleName())) {
	                response.sendRedirect("AdminDashboard"); 
	            } else {
	                response.sendRedirect("UserLogin.jsp"); 
	            }
	        } else {
	            request.setAttribute("error", "Invalid username or password.");
	            request.getRequestDispatcher("UserLogin.jsp").forward(request, response);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        response.sendRedirect("Home");
	    }
	}
}
