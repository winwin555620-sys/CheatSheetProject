package com.CheatSheet.Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.CheatSheet.Model.RegisterLoginBean;
import com.CheatSheet.Repository.HomeRepository;

@WebServlet("/SaveNote")
public class SaveNote extends HttpServlet {
	private static final long serialVersionUID = 1L;
	com.CheatSheet.Repository.HomeRepository homeRepo = new com.CheatSheet.Repository.HomeRepository();
	
    public SaveNote() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String content = request.getParameter("noteContent");
	    
	    // langId parameter ရှိမရှိ သေချာအောင် စစ်ဆေးပါ
	    String langIdRaw = request.getParameter("languageId");
	    
	    int snippetId = Integer.parseInt(request.getParameter("snippetId"));
	    
	    if (langIdRaw == null || langIdRaw.isEmpty()) {
	        response.sendRedirect("Home");
	        return;
	    }
	    
	    int langId = Integer.parseInt(langIdRaw);
	    RegisterLoginBean user = (RegisterLoginBean) request.getSession().getAttribute("loggedUser");
	    
	    if (user != null) {
	        try {
	        	homeRepo.saveOrUpdateNote(user.getId(), langId, snippetId, content);
	            
	            // Home.jsp မဟုတ်ဘဲ Servlet ဖြစ်တဲ့ "Home" ကိုပဲ redirect လုပ်ပါ
	            response.sendRedirect("Home"); 
	        } catch (SQLException e) {
	            e.printStackTrace();
	            response.sendRedirect("Home?error=1");
	        }
	    } else {
	        // Login မဝင်ထားရင် Login page ကို တန်းပို့တာက ပိုကောင်းပါတယ်
	        response.sendRedirect("UserLogin.jsp"); 
	    }
	}
}