<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.CheatSheet.Model.HomeBean" %>
<!DOCTYPE html>
<html>
<head>
    <title>Language Topics</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #f1f5f9; padding: 40px; }
        .container { max-width: 900px; margin: 0 auto; }
        .topic-card {
            background: white; margin-bottom: 12px; padding: 20px;
            border-radius: 10px; display: flex; justify-content: space-between;
            text-decoration: none; color: #1e293b; font-weight: 600;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05); transition: 0.2s;
        }
        .topic-card:hover { transform: translateX(10px); border-left: 5px solid #8b5cf6; }
        .back-link { margin-bottom: 20px; display: block; color: #6366f1; text-decoration: none; }
    </style>
</head>
<body>
    <div class="container">
        <a href="Home" class="back-link">← Back to All Languages</a>
        <h1>Topics</h1>
        
        <% 
        List<HomeBean> topics = (List<HomeBean>) request.getAttribute("snippets");
            if(topics != null && !topics.isEmpty()) {
                for(HomeBean t : topics) { 
        %>
            <a href="ViewSnippet?id=<%= t.getId() %>" class="topic-card">
                <span><%= t.getTitle() %></span>
                <span style="color: #8b5cf6;">View Detail →</span>
            </a>
        <%      } 
            } else { %>
                <p>No topics found for this language.</p>
        <% } %>
    </div>
</body>
</html>