package com.CheatSheet.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.CheatSheet.Model.HomeBean;
import com.CheatSheet.Model.RegisterLoginBean;
import com.CheatSheet.Repository.HomeRepository;


@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HomeRepository homeRepo = new HomeRepository();
    
    public Home() {
        super();
       
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
    HomeRepository homeRepo = new HomeRepository();
    List<HomeBean> languages = null;
    List<HomeBean> snippets = null;
    List<Map<String, String>> userNotes = null; // User notes အတွက် list အသစ်

    try {
        languages = homeRepo.getAllLanguages(); 
        snippets = homeRepo.getAllSnippets(); 
        
        // ၁။ Login ဝင်ထားသော User ရှိမရှိ စစ်ဆေးပါ
        RegisterLoginBean user = (RegisterLoginBean) request.getSession().getAttribute("loggedUser");
        
        if (user != null) {
            // ၂။ Login ဝင်ထားလျှင် ထို user ၏ note များကို repository မှ ယူပါ
            userNotes = homeRepo.getAllUserNotes(user.getId()); 
        }
        
    } catch (Exception e) {
        e.printStackTrace();
    }

    request.setAttribute("languages", languages);
    request.setAttribute("snippets", snippets);
    
    // ၃။ User notes များကို JSP သို့ ပို့ပေးပါ
    request.setAttribute("userNotes", userNotes); 
    
    request.setAttribute("selectedLanguage", null); 
    
    request.getRequestDispatcher("Home.jsp").forward(request, response);
}
   	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
