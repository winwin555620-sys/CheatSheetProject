package com.CheatSheet.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.CheatSheet.Model.AdminDashboardBean;
import com.CheatSheet.Model.HomeBean;
import com.CheatSheet.Model.TopicBean;
import com.CheatSheet.Repository.AdminDashboardRepository;
import com.CheatSheet.Repository.HomeRepository;


@WebServlet("/AdminDashboard")
public class AdminDashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdminDashboardRepository repo = new AdminDashboardRepository();
	AdminDashboardRepository topicRepo = repo;
    public AdminDashboard() {
        super();
     
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            // 1. Delete Logic
            if ("delete".equals(action)) {
                // Language ဖျက်ရန် logic
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    int id = Integer.parseInt(idStr);
                    repo.deleteLanguage(id);
                }
                response.sendRedirect("AdminDashboard");
                return;
            } 
            else if ("deleteTopic".equals(action)) {
                // Topic/Snippet ဖျက်ရန် logic (အသစ်ထည့်ထားသည်)
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    int id = Integer.parseInt(idStr);
                    repo.deleteTopic(id); // Repository မှာ deleteTopic(id) ရှိရမည်
                }
                response.sendRedirect("AdminDashboard");
                return;
            }

            // 2. Fetch Data for Dashboard Tabs
            
            // Overview Tab အတွက် User count
            int totalUsers = repo.getUserCount();
            request.setAttribute("totalUsers", totalUsers);

            // User Tab အတွက် User List
            List<AdminDashboardBean> userList = repo.getAllUsers();
            request.setAttribute("userList", userList);

            // Languages List (Languages Tab နှင့် Dropdown အတွက်)
            List<AdminDashboardBean> languages = repo.getAllLanguages();
            request.setAttribute("languages", languages);

            // --- ဒီအပိုင်းက အပြာရောင်မျဉ်းနေရာမှာ ပေါ်မယ့် Topic List အတွက် ဖြစ်သည် ---
            List<TopicBean> allTopics = repo.getAllTopics(); // DB ထဲက Topic အားလုံးယူခြင်း
            request.setAttribute("allTopics", allTopics); 
            // ---------------------------------------------------------

            // 3. Forward to JSP
            request.getRequestDispatcher("AdminDashboard.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("Error in AdminDashboard doGet: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("Home");
        }
    }

	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        try {
            // 1. Handle Language Actions (Add/Update)
            if ("addLanguage".equals(action)) {
                String name = request.getParameter("name");
                String imagePath = request.getParameter("imagePath");
                repo.addLanguage(name, imagePath);
            } 
            else if ("updateLanguage".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String imagePath = request.getParameter("imagePath");
                repo.updateLanguage(id, name, imagePath);
            }
            
            // 2. Handle Topic Action (Add Topic) - ဒီအပိုင်းကို အဓိက ပြင်ထားပါတယ်
            else if ("addTopic".equals(action)) {
                TopicBean topic = new TopicBean();
                
                // JSP ဘက်က <select name="languageId"> ကနေ ပို့လိုက်တဲ့ ID ကို ယူပါတယ်
                String langIdParam = request.getParameter("languageId");
                
                if (langIdParam != null && !langIdParam.isEmpty()) {
                    int languageId = Integer.parseInt(langIdParam);
                    
                    topic.setTitle(request.getParameter("title"));
                    topic.setDescription(request.getParameter("description"));
                    topic.setCodeContent(request.getParameter("codeContent"));
                    
                    // Debugging: Console မှာ စစ်ဆေးရန်
                    System.out.println("DEBUG: Saving Topic for Language ID: " + languageId);
                    
                    // Repo method ဆီကို String အစား int languageId ကို ပို့ပေးပါ
                    repo.addTopic(topic, languageId); 
                }
            }

            
            else if ("updateTopic".equals(action)) {
                String idStr = request.getParameter("id"); // From the hidden input #topic-id
                String langIdStr = request.getParameter("languageId");
                
                if (idStr != null && langIdStr != null) {
                    TopicBean topic = new TopicBean();
                    topic.setId(Integer.parseInt(idStr)); // Set the ID so SQL knows which row to update
                    topic.setTitle(request.getParameter("title"));
                    topic.setDescription(request.getParameter("description"));
                    topic.setCodeContent(request.getParameter("codeContent"));
                    
                    int languageId = Integer.parseInt(langIdStr);
                    
                    // Call the repository method we just verified
                    repo.updateTopic(topic, languageId); 
                    System.out.println("DEBUG: Successfully updated topic ID: " + idStr);
                }
            }
            
            response.sendRedirect("AdminDashboard");
            
        } catch (Exception e) { 
            e.printStackTrace(); 
            // Error ဖြစ်ရင်လည်း dashboard ကိုပဲ ပြန်သွားအောင် လုပ်နိုင်ပါတယ်
            doGet(request, response);
        }
    }
}