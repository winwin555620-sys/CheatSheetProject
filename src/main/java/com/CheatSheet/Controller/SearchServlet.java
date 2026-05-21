package com.CheatSheet.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.CheatSheet.Model.HomeBean;
import com.CheatSheet.Repository.HomeRepository;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public SearchServlet() {
        super();
       
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        HomeRepository repo = new HomeRepository();
        
        try {
            // Database မှ Java နှင့်ဆိုင်သော snippets များကိုသာ ရှာယူသည်
            List<HomeBean> searchResults = repo.searchSnippets(query);
            
            // JSP ဆီသို့ snippets attribute ဖြင့်သာ ပို့ပေးပါသည်
            request.setAttribute("snippets", searchResults);
            
            // ခေါင်းစဉ်တွင် "Search results for: Java" ဟု ပြောင်းလဲရန်
            request.setAttribute("selectedLanguage", "Search results for: " + query);
            
            // Programming languages grid ကို ခေတ္တဖျောက်ထားချင်ပါက languages attribute ကို မပို့ဘဲ ထားနိုင်ပါသည်
            // သို့မဟုတ် ရှာဖွေမှုရလဒ် စာမျက်နှာအဖြစ် သီးသန့်ပြသပါ
            request.getRequestDispatcher("Home.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
