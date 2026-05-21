package com.CheatSheet.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.CheatSheet.Model.AdminDashboardBean;
import com.CheatSheet.Model.TopicBean;

public class AdminDashboardRepository {
	public Connection dbConnection() throws SQLException {
	    try {
	        // This line causes the ClassNotFoundException
	        Class.forName("com.mysql.cj.jdbc.Driver"); 
	    } catch (ClassNotFoundException e) {
	        System.out.println("Driver not found: " + e.getMessage());
	    }
	    // Make sure your URL, username, and password are correct
	    return DriverManager.getConnection("jdbc:mysql://localhost:3306/cheat_sheet", "root", "root");
	}
	
	
	
	public List<AdminDashboardBean> getAllUsers() throws SQLException {
	    List<AdminDashboardBean> userList = new ArrayList<>();
	    String sql = "SELECT id, username, email FROM users"; 
	    try (Connection con = dbConnection(); 
	         Statement st = con.createStatement(); 
	         ResultSet rs = st.executeQuery(sql)) {
	        while (rs.next()) {
	            AdminDashboardBean user = new AdminDashboardBean();
	            user.setId(rs.getInt("id"));
	            user.setUsername(rs.getString("username")); // variable name ကို သတိထားပါ
	            user.setEmail(rs.getString("email"));
	            userList.add(user);
	        }
	    }
	    return userList;
	}
	
	
	
	
	
	
	public int getUserCount() throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM users";
        try (Connection con = dbConnection(); 
             Statement st = con.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) count = rs.getInt(1);
        }
        return count;
    }
	
	
	
	
	
	// String languageName အစား int languageId လို့ ပြောင်းလိုက်ပါ
	public void addTopic(TopicBean topic, int languageId) throws SQLException, ClassNotFoundException {
	    
	    // languages_id column ထဲကို သွင်းမှာဖြစ်လို့ SQL ကို ဒီလိုပြင်ပါ
	    String sql = "INSERT INTO snippets (languages_id, title, description, code_content, created_at) " +
	                 "VALUES (?, ?, ?, ?, NOW())";

	    try (Connection con = dbConnection(); 
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        // ပထမ parameter က အခု int ဖြစ်သွားပြီမလို့ setInt သုံးရပါမယ်
	        ps.setInt(1, languageId); 
	        
	        ps.setString(2, topic.getTitle());
	        ps.setString(3, topic.getDescription());
	        ps.setString(4, topic.getCodeContent());
	        
	        ps.executeUpdate();
	    }
	}
	
	
    public List<AdminDashboardBean> getAllLanguages() throws SQLException {
        List<AdminDashboardBean> list = new ArrayList<>();
        String sql = "SELECT * FROM languages";
        try (Connection con = dbConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                AdminDashboardBean bean = new AdminDashboardBean();
                bean.setId(rs.getInt("id"));
                bean.setName(rs.getString("name"));
//                bean.setImagePath(rs.getString("image_path"));
                list.add(bean);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
    
    public List<TopicBean> getTopicsByLanguage(int langId) throws SQLException {
        List<TopicBean> topicList = new ArrayList<>();
        String sql = "SELECT * FROM snippets WHERE languages_id = ?";
        try (Connection con = dbConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, langId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TopicBean t = new TopicBean();
                    t.setId(rs.getInt("id"));
                    t.setTitle(rs.getString("title"));
                    t.setDescription(rs.getString("description"));
                    t.setCodeContent(rs.getString("code_content"));
                    topicList.add(t);
                }
            }
        }
        return topicList;
    }
    
    
    public void updateTopic(TopicBean topic, int languageId) throws SQLException {
        // SQL to update the snippet based on its unique ID
        String sql = "UPDATE snippets SET languages_id = ?, title = ?, description = ?, code_content = ? WHERE id = ?";

        try (Connection con = dbConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, languageId);
            ps.setString(2, topic.getTitle());
            ps.setString(3, topic.getDescription());
            ps.setString(4, topic.getCodeContent());
            ps.setInt(5, topic.getId()); // The ID of the snippet you are editing

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("DEBUG: Topic ID " + topic.getId() + " updated successfully.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR in updateTopic: " + e.getMessage());
            throw e;
        }
    }
    
    
    
    
    public List<TopicBean> getAllTopics() throws SQLException {
        List<TopicBean> list = new ArrayList<>();
        // Updated SQL to be explicit and ensure we get the foreign key and the code
        String sql = "SELECT s.id, s.languages_id, s.title, s.description, s.code_content, l.name as langName " +
                     "FROM snippets s " +
                     "JOIN languages l ON s.languages_id = l.id ORDER BY s.id DESC";
        
        try (Connection con = dbConnection(); 
             Statement st = con.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                TopicBean t = new TopicBean();
                t.setId(rs.getInt("id"));
                
                // IMPORTANT: You need these for the Edit Form to fill correctly
                t.setLanguageId(rs.getInt("languages_id")); 
                t.setCodeContent(rs.getString("code_content"));
                
                t.setTitle(rs.getString("title"));
                t.setDescription(rs.getString("description"));
                t.setLanguageName(rs.getString("langName")); 
                
                list.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Error in getAllTopics: " + e.getMessage());
            throw e;
        }
        return list;
    }
    
    public void addLanguage(String name, String imagePath) throws SQLException {
        String sql = "INSERT INTO languages (name, image_path) VALUES (?, ?)";
        try (Connection con = dbConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, imagePath);
            ps.executeUpdate();
        }
    }

    public void updateLanguage(int id, String name, String imagePath) throws SQLException {
        String sql = "UPDATE languages SET name = ?, image_path = ? WHERE id = ?";
        try (Connection con = dbConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, imagePath);
            ps.setInt(3, id);
            ps.executeUpdate();
        }
    }

    
 // Topic/Snippet ကို ID အလိုက် ဖျက်ပေးမည့် method
    public void deleteTopic(int id) throws SQLException {
        String sql = "DELETE FROM snippets WHERE id = ?";
        
        try (Connection con = dbConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
            
            System.out.println("DEBUG: Topic with ID " + id + " deleted successfully.");
        } catch (SQLException e) {
            System.out.println("ERROR in deleteTopic: " + e.getMessage());
            throw e;
        }
    }
    
    
    
    
    
    
    
    public void deleteLanguage(int id) throws SQLException {
        String sql = "DELETE FROM languages WHERE id = ?";
        try (Connection con = dbConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
  
}