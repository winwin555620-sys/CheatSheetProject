<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.CheatSheet.Model.HomeBean, com.CheatSheet.Model.RegisterLoginBean" %>
<%@ page import="java.util.List, java.util.Map, com.CheatSheet.Model.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home - CheatSheet</title>
    <style>
        /* General Page Styling */
        body { 
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; 
            background-color: #f1f5f9; 
            margin: 0; 
            padding: 20px;
        }

        /* --- INSERTED HERO SECTION STYLE --- */
        .hero-banner {
            background: linear-gradient(135deg, #7c3aed 0%, #4f46e5 100%);
            color: white;
            padding: 60px 20px;
            text-align: center;
            border-radius: 12px;
            margin-bottom: 40px;
            box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
        }
        .hero-banner h1 {
            font-size: 48px;
            font-weight: 800;
            margin: 0 0 10px 0;
            line-height: 1.1;
        }
        .hero-banner p {
            font-size: 18px;
            max-width: 650px;
            margin: 0 auto 30px auto;
            color: #e0e7ff;
        }
        .hero-btns {
            display: flex;
            justify-content: center;
            gap: 15px;
            flex-wrap: wrap;
        }
        .btn-pill {
            background: rgba(255, 255, 255, 0.1);
            border: 1px solid rgba(255, 255, 255, 0.2);
            color: white;
            padding: 10px 24px;
            border-radius: 50px;
            text-decoration: none;
            font-size: 14px;
            font-weight: 500;
            transition: 0.2s;
        }
        .btn-pill:hover { background: rgba(255, 255, 255, 0.2); }

        /* Existing Styles */
        .logout-btn { 
            color: #4338ca; 
            text-decoration: none; 
            font-size: 16px; 
            font-weight: 500;
            border-bottom: 2px solid #4338ca; 
            padding-bottom: 2px;
            transition: opacity 0.2s;
        }
        .logout-btn:hover { opacity: 0.7; }

        .bg-bash { background-color: #71717a; color: white; }
        .bg-powershell { background-color: #60a5fa; color: white; }
        .bg-java { background-color: #ef4444; color: white; }
        .bg-python { background-color: #64748b; color: white; }
        .bg-js { background-color: #fde047; color: #1e293b; }
        .bg-php { background-color: #a5b4fc; color: #312e81; }

        .section-header {
            border-bottom: 2px solid #e2e8f0;
            margin-bottom: 25px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .section-title {
            font-size: 20px;
            font-weight: 600;
            color: #1e293b;
            padding-bottom: 8px;
            border-bottom: 3px solid #8b5cf6; 
            margin: 0;
        }
        .grid-container { 
            display: grid; 
            grid-template-columns: repeat(auto-fill, minmax(220px, 1fr)); 
            gap: 16px; 
        }
        .card { 
            background: white;
            padding: 16px; 
            border-radius: 8px; 
            color: #334155; 
            text-decoration: none; 
            font-size: 15px; 
            font-weight: 500; 
            display: flex; 
            align-items: center; 
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
            transition: all 0.2s ease;
        }
        .card:hover { transform: translateY(-2px); box-shadow: 0 4px 6px rgba(0,0,0,0.1); }

        /* --- INSERTED SNIPPET ROW STYLE --- */
        .snippet-section { margin-top: 40px; }
        .snippet-row {
            background: white;
            padding: 15px 20px;
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            text-decoration: none;
            color: #1e293b;
            border: 1px solid #e2e8f0;
            margin-bottom: 10px;
            transition: all 0.2s ease;
        }
        .snippet-row:hover { border-color: #8b5cf6; background: #f8fafc; transform: translateX(5px); }

        .admin-badge { 
            background: #fef08a; 
            color: #854d0e; 
            padding: 2px 8px; 
            border-radius: 4px; 
            font-size: 11px; 
            font-weight: bold;
            margin-right: 10px;
        }
    </style>
</head>
<body>

    <div class="hero-banner">
    <div style="margin-bottom: 25px;">
        <span style="background: rgba(255,255,255,0.2); padding: 8px 20px; border-radius: 12px; font-weight: bold; font-size: 22px; border: 1px solid rgba(255,255,255,0.3);">
            cheatsheets<span style="color: #a5b4fc;">.zip</span>
        </span>
    </div>

    <h1>Cheat Sheets <br> for Developers</h1>
    <p>Welcome to the ultimate repository of sheets and quick references meticulously crafted by the open community to <strong>boost your productivity.</strong></p>
    
    <div style="max-width: 500px; margin: 30px auto; position: relative;">
        <form action="SearchServlet" method="GET">
            <input type="text" name="query" placeholder="Search for cheatsheet (e.g. Python, Java)..." 
                   style="width: 100%; padding: 15px 25px; border-radius: 50px; border: 1px solid rgba(255,255,255,0.4); background: rgba(255,255,255,0.1); color: white; outline: none; font-size: 16px;">
            <button type="submit" style="position: absolute; right: 15px; top: 50%; transform: translateY(-50%); background: none; border: none; color: white; cursor: pointer; font-size: 18px;">
                🔍
            </button>
        </form>
    </div>

    <div class="hero-btns">
        <a href="#" class="btn-pill">⭐ Star on GitHub <small style="opacity:0.7; margin-left:5px;">10.5k</small></a>
        <a href="#" class="btn-pill">Follow us on X →</a>
        <a href="#" class="btn-pill">☕ Buy Me a Coffee</a>
    </div>
</div>

    <%
        RegisterLoginBean user = (RegisterLoginBean) session.getAttribute("loggedUser");
    List<HomeBean> languages = (List<HomeBean>) request.getAttribute("languages");
    List<HomeBean> snippets = (List<HomeBean>) request.getAttribute("snippets");
    List<Map<String, String>> userNotes = (List<Map<String, String>>) request.getAttribute("userNotes");
    %>

   <div class="section-header">
    <h2 class="section-title">Programming</h2>
    <div>
        <% if (user != null && user.getRoleName() != null && user.getRoleName().equalsIgnoreCase("Admin")) { %>
            <span class="admin-badge">ADMIN</span>
            <a href="AdminDashboard" style="margin-right:15px; color:#8b5cf6; text-decoration:none; font-weight:bold;">Dashboard</a>
        <% } %>
        
        <span style="font-size: 14px; color: #64748b; margin-right: 15px;">
            Welcome, <%= (user != null) ? user.getUsername() : "Guest" %>
        </span>
        
        
            <a href="UserLogin" class="logout-btn">Logout</a>
            <a href="UserLogin" class="logout-btn" style="border-bottom:none;">Login</a>
       
    </div>
</div>



  <div class="grid-container">
    <% if (languages != null && !languages.isEmpty()) {
        for (HomeBean lang : languages) { %>
            <a href="Topics?id=<%= lang.getId() %>" class="card">
                <span><%= lang.getName() %></span> 
                <% if (user != null && "Admin".equalsIgnoreCase(user.getRoleName())) { %>
                    <span class="admin-badge">ADMIN</span>
                <% } %>
            </a> 
    <%  } 
    } %>
</div>




	<% if (user != null && userNotes != null && !userNotes.isEmpty()) { %>
    <div style="margin-top: 50px;">
        <div class="section-header">
            <h2 class="section-title">My Personal Notes</h2>
        </div>
        <div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 20px;">
            <% for (Map<String, String> note : userNotes) { %>
                <div class="note-card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px;">
                        <div>
                            <span class="note-lang-tag"><%= note.get("langName") %></span>
                            <span style="font-size: 13px; color: #6366f1; font-weight: 500; margin-left: 5px;">
                                <%= note.get("snippetTitle") != null ? "- " + note.get("snippetTitle") : "" %>
                            </span>
                        </div>
                        
                        <a href="ViewSnippet?id=<%= note.get("snippetId") %>" style="font-size: 12px; color: #8b5cf6; text-decoration: none; font-weight: bold;">
                            View Details →
                        </a>
                    </div>
                    
                    <p style="color: #475569; font-size: 14px; line-height: 1.6; margin: 0; font-style: italic;">
                        "<%= note.get("content") %>"
                    </p>
                    
                    <div style="margin-top: 10px; text-align: right;">
                         <a href="Topics?id=<%= note.get("langId") %>" style="font-size: 11px; color: #94a3b8; text-decoration: none;">
                            Related <%= note.get("langName") %> Topics
                         </a>
                    </div>
                </div>
            <% } %>
        </div>
    </div>
<% } %>





<div class="snippet-section">
    <div class="section-header">
        <% 
    String searchQueryText = (String) request.getAttribute("selectedLanguage"); 
    if (searchQueryText != null) { 
%>
    <div class="snippet-section" style="margin-top: 50px; background: #ffffff; padding: 25px; border-radius: 12px; border: 1px solid #e2e8f0; box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);">
        <div class="section-header" style="border-bottom: 2px solid #8b5cf6;">
            <h2 class="section-title">Search results for: <%= searchQueryText %></h2>
            
            <a href="Home" style="text-decoration: none; color: #ef4444; font-size: 14px; font-weight: bold;">
                Clear Search &times;
            </a>
        </div>
        
        <div style="margin-top: 20px;">
            <% if (snippets != null && !snippets.isEmpty()) {
                for (HomeBean snip : snippets) { %>
                    <a href="ViewSnippet?id=<%= snip.getId() %>" class="snippet-row">
                        <div style="display: flex; align-items: center; gap: 15px;">
                            <div style="width: 4px; height: 20px; background: #8b5cf6; border-radius: 2px;"></div>
                            <span style="font-weight: 500;"><%= snip.getTitle() %></span>
                        </div>
                        <span style="color: #94a3b8; font-size: 13px;">View Snippet →</span>
                    </a>
            <%  } 
            } else { %>
                <div style="padding: 40px; text-align: center; color: #64748b;">
                    <p>No related information found for "<%= searchQueryText %>".</p>
                </div>
            <% } %>
        </div>
    </div>
<% } %>
    </div>
</div>
</body>
</html>