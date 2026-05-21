package com.CheatSheet.Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.CheatSheet.Model.HomeBean;
import com.CheatSheet.Repository.HomeRepository;


@WebServlet("/ViewSnippet")
public class ViewSnippet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HomeRepository homeRepo = new HomeRepository();
    public ViewSnippet() {
        super();
      
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                // Fetch the specific snippet by ID
             // ViewSnippet.java ရဲ့ doGet ထဲမှာ ပြင်ပါ
                HomeBean snippet = homeRepo.getSnippetById(id);
                if (snippet != null) {
                    request.setAttribute("snippet", snippet);
                    
                    // ဤနေရာတွင် langId ကို အသေအချာ ပို့ပေးရပါမည်
                    request.setAttribute("langId", snippet.getCategories_id()); 
                    
                    request.getRequestDispatcher("ViewSnippet.jsp").forward(request, response);
                 } else {
                    response.sendRedirect("Home");
                }
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("Home");
            }
        } else {
            response.sendRedirect("Home");
        }
    }		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
