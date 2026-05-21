package com.CheatSheet.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TopicController
 */
@WebServlet("/Topics")
public class TopicController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopicController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Capture the 'id' from the URL (e.g., Home?id=10)
        String langIdParam = request.getParameter("id");
        
        // 2. Initialize your Repository
        com.CheatSheet.Repository.HomeRepository homeRepo = new com.CheatSheet.Repository.HomeRepository();
        
        List<com.CheatSheet.Model.HomeBean> languages = null;
        List<com.CheatSheet.Model.HomeBean> snippets = null;

        try {
            // 3. Always fetch all languages to keep the top grid visible
            languages = homeRepo.getAllLanguages(); 
            
            // 4. Logic to decide between Filtered view or Full view
            if (langIdParam != null && !langIdParam.trim().isEmpty()) {
                // If an ID is present, call your existing repo method
                // Note: Your repo takes a String for this method
                snippets = homeRepo.getSnippetsByCategoryId(langIdParam);
                
                // To show "Java Snippets" in the UI, we find the name matching the ID
                if (languages != null) {
                    for (com.CheatSheet.Model.HomeBean lang : languages) {
                        if (String.valueOf(lang.getId()).equals(langIdParam)) {
                            request.setAttribute("selectedLanguage", lang.getName());
                            break;
                        }
                    }
                }
            } else {
                // If no ID is clicked, show all snippets as usual
                snippets = homeRepo.getAllSnippets(); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

     // TopicController.java ထဲမှာ ပြင်ရန်
        request.setAttribute("languages", languages);
        request.setAttribute("snippets", snippets);

        // Home.jsp အစား Topics တွေကိုပဲ သီးသန့်ပြမယ့် JSP ဖိုင်အမည်ကို ရေးပါ
        request.getRequestDispatcher("Topics.jsp").forward(request, response);
    }	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
