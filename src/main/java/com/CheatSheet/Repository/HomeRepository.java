package com.CheatSheet.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.CheatSheet.Config.DBConnect;
import com.CheatSheet.Model.HomeBean;

public class HomeRepository {
  Connection con = null;

  
  public List<HomeBean> getAllLanguages() throws SQLException {
        List<HomeBean> list = new ArrayList<>();
        con = DBConnect.getConnection();
        
        String sql = "SELECT * FROM languages"; 
        
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                HomeBean lang = new HomeBean();
                lang.setId(rs.getInt("id"));
                lang.setName(rs.getString("name"));
            
                list.add(lang);
            }
        }
        return list;
    }
  
  public List<HomeBean> getAllSnippets() throws SQLException {
      List<HomeBean> list = new ArrayList<>();
      // ပြင်ဆင်ချက်: categories_id အစား languages_id ကို သုံးပါ
      String sql = "SELECT id, languages_id, title, description, code_content FROM snippets";
      con = DBConnect.getConnection();
      try (PreparedStatement ps = con.prepareStatement(sql);
           ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
              HomeBean snip = new HomeBean();
              snip.setId(rs.getInt("id"));
              // rs.getString("languages_id") လို့ ပြင်ပါ
              snip.setCategories_id(rs.getString("languages_id"));
              snip.setTitle(rs.getString("title"));
              snip.setDescription(rs.getString("description"));
              snip.setCode_content(rs.getString("code_content"));
              list.add(snip);
          }
      }
      return list;
  }
  
  
  public HomeBean getSnippetById(int id) throws SQLException {
      HomeBean snip = null;
      String sql = "SELECT * FROM snippets WHERE id = ?";
      con = DBConnect.getConnection();
      try (PreparedStatement ps = con.prepareStatement(sql)) {
          ps.setInt(1, id);
          try (ResultSet rs = ps.executeQuery()) {
              if (rs.next()) {
                  snip = new HomeBean();
                  snip.setId(rs.getInt("id"));
                  snip.setTitle(rs.getString("title"));
                  snip.setDescription(rs.getString("description"));
                  snip.setCode_content(rs.getString("code_content"));
                  // rs.getString("languages_id") လို့ ပြင်ပါ
                  snip.setCategories_id(rs.getString("languages_id"));
              }
          }
      }
      return snip;
  }
  
  
  
  
  
  public List<HomeBean> getSnippetsByCategoryId(String langId) throws SQLException {
	    List<HomeBean> list = new ArrayList<>();
	    
	    // Direct Query: Get snippets where the languages_id matches the ID from the card
	    String sql = "SELECT * FROM snippets WHERE languages_id = ?"; 
	    
	    // It is safer to get the connection inside the try block to ensure it closes
	    try (Connection connection = DBConnect.getConnection();
	         PreparedStatement ps = connection.prepareStatement(sql)) {
	        
	        ps.setInt(1, Integer.parseInt(langId)); 
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                HomeBean snip = new HomeBean();
	                snip.setId(rs.getInt("id"));
	                snip.setTitle(rs.getString("title"));
	                snip.setDescription(rs.getString("description"));
	                snip.setCode_content(rs.getString("code_content"));
	                snip.setCategories_id(rs.getString("languages_id"));
	                list.add(snip);
	            }
	        }
	    }
	    return list;
	}  
  
  public void saveOrUpdateNote(int userId, int langId, int snippetId, String content) throws SQLException {
	   // Table column အမည်များကို image_db6c05.png ပါအတိုင်း ပြင်ဆင်ထားပါသည်
	   String sql = "INSERT INTO user_notes (user_id, language_id, snippet_id, note_content) " +
	                "VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE note_content = ?, snippet_id = ?";
	   
	   try (Connection con = DBConnect.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql)) {
	       ps.setInt(1, userId);
	       ps.setInt(2, langId);
	       ps.setInt(3, snippetId);
	       ps.setString(4, content);
	       ps.setString(5, content);
	       ps.setInt(6, snippetId);
	       ps.executeUpdate();
	   }
	}  
  
  
  public List<Map<String, String>> getAllUserNotes(int userId) throws SQLException {
	    List<Map<String, String>> notesList = new ArrayList<>();
	    con = com.CheatSheet.Config.DBConnect.getConnection();
	    
	    // languages ရော snippets table ကိုပါ JOIN လုပ်လိုက်ပါပြီ
	    String sql = "SELECT l.name AS lang_name, s.title AS snippet_title, n.note_content, n.language_id, n.snippet_id " +
	                 "FROM user_notes n " +
	                 "JOIN languages l ON n.language_id = l.id " +
	                 "LEFT JOIN snippets s ON n.snippet_id = s.id " + // snippet_id နဲ့ ချိတ်ပါမယ်
	                 "WHERE n.user_id = ?";
	    
	    try (PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, userId);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Map<String, String> noteMap = new HashMap<>();
	                noteMap.put("langName", rs.getString("lang_name"));
	                noteMap.put("snippetTitle", rs.getString("snippet_title")); // Related Info အတွက် Title
	                noteMap.put("content", rs.getString("note_content"));
	                noteMap.put("langId", rs.getString("language_id"));
	                noteMap.put("snippetId", rs.getString("snippet_id"));
	                notesList.add(noteMap);
	            }
	        }
	    }
	    return notesList;
	}
  
  
  
  
  public List<HomeBean> searchSnippets(String query) throws SQLException {
	    List<HomeBean> list = new ArrayList<>();
	    // Language name သို့မဟုတ် Snippet title ထဲမှာ query ပါဝင်ခြင်း ရှိမရှိ ရှာပါသည်
	    String sql = "SELECT s.* FROM snippets s " +
	                 "JOIN languages l ON s.languages_id = l.id " +
	                 "WHERE l.name LIKE ? OR s.title LIKE ?";
	    
	    try (Connection con = DBConnect.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, "%" + query + "%");
	        ps.setString(2, "%" + query + "%");
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                HomeBean bean = new HomeBean();
	                bean.setId(rs.getInt("id"));
	                bean.setTitle(rs.getString("title"));
	                bean.setDescription(rs.getString("description"));
	                list.add(bean);
	            }
	        }
	    }
	    return list;
	}
  
}