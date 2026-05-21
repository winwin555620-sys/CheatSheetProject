<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.CheatSheet.Model.AdminDashboardBean" %>
<%@ page import="com.CheatSheet.Model.TopicBean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CheatSheet Admin Dashboard</title>
<style>
    * { box-sizing: border-box; margin: 0; padding: 0; }
    body { font-family: 'Segoe UI', sans-serif; background: #f0f2f5; display: flex; height: 100vh; }

    /* SIDEBAR */
    .sidebar { width: 260px; background: #1e293b; color: white; display: flex; flex-direction: column; padding: 20px; flex-shrink: 0; }
    .sidebar h2 { color: #3b82f6; margin-bottom: 30px; font-size: 1.4rem; }
    .sidebar-nav button { background: none; border: none; color: #cbd5e1; text-align: left; padding: 12px; width: 100%; cursor: pointer; border-radius: 6px; font-size: 1rem; margin-bottom: 5px; }
    .sidebar-nav button.active { background: #334155; color: white; }

    /* MAIN CONTENT */
    .main-content { flex: 1; overflow-y: auto; padding: 40px; }
    .card { background: white; padding: 25px; border-radius: 12px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); width: 100%; position: relative; }
    .tab-content { display: none; }
    .tab-content.active { display: block; }

    /* MATERIAL STATS CARDS */
    .stats-card { margin-top: 20px; padding-top: 40px; }
    .icon-box { 
        position: absolute; top: -15px; left: 20px; padding: 15px; 
        border-radius: 4px; color: white; font-size: 1.5rem; 
        box-shadow: 0 4px 20px rgba(0,0,0,0.14); 
    }
    .bg-orange { background: linear-gradient(60deg, #ffa726, #fb8c00); }
    .bg-green { background: linear-gradient(60deg, #66bb6a, #43a047); }
    .bg-red { background: linear-gradient(60deg, #ef5350, #e53935); }

    /* PERCENTAGE CHART VISUAL */
    .chart-container { display: flex; align-items: flex-end; justify-content: space-around; height: 150px; padding: 10px; margin-top: 20px; }
    .bar { width: 35px; background: rgba(255,255,255,0.3); border-radius: 4px 4px 0 0; }
    .bar.highlight { background: #fff; }

    /* FORM & TABLE STYLES */
    .form-group { margin-bottom: 15px; }
    label { display: block; margin-bottom: 5px; font-weight: 600; color: #475569; }
    input, select, textarea { width: 100%; padding: 10px; border: 1px solid #e2e8f0; border-radius: 6px; }
    textarea.code-area { background: #1e293b; color: #f8fafc; font-family: 'Courier New', monospace; height: 200px; }

    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
    th { text-align: left; padding: 12px; background: #f8fafc; border-bottom: 2px solid #e2e8f0; }
    td { padding: 12px; border-bottom: 1px solid #f1f5f9; }

    .btn-primary { background: #3b82f6; color: white; border: none; padding: 10px 20px; border-radius: 6px; cursor: pointer; }
    .btn-success { background: #10b981; color: white; border: none; padding: 10px 20px; border-radius: 6px; cursor: pointer; }
    .btn-danger { color: #ef4444; text-decoration: none; font-weight: bold; }
</style>
</head>
<body>

<div class="sidebar">
    <h2>CheatSheet Admin</h2>
    <nav class="sidebar-nav">
        <button class="tab-link active" onclick="showTab(event, 'overviewTab')">🏠 Overview</button>
        <button class="tab-link" onclick="showTab(event, 'langTab')">📊 Languages</button>
        <button class="tab-link" onclick="showTab(event, 'topicTab')">📝 Create Topic</button>
        <button class="tab-link" onclick="showTab(event, 'userTab')">👥 Users</button>
    </nav>
    <div style="margin-top: auto; padding-top: 20px;">
        <a href="Home" style="color: #94a3b8; text-decoration: none;">← Exit to Site</a>
    </div>
</div>

<div class="main-content">
    
    <div id="overviewTab" class="tab-content active">
        <h1>Dashboard Overview</h1>
        
        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); gap: 30px; margin-top: 20px;">
            <div class="card stats-card">
                <div class="icon-box bg-orange">👥</div>
                <div style="text-align: right;">
                    <p style="color: #999;">Total Users</p>
                    <h3 style="font-size: 1.8rem;">${totalUsers}</h3>
                </div>
                <hr style="margin: 15px 0; border: 0; border-top: 1px solid #eee;">
                <p style="color: #666; font-size: 0.8rem;">Users registered on platform</p>
            </div>

            <div class="card stats-card">
                <div class="icon-box bg-green">📊</div>
                <div style="text-align: right;">
                    <p style="color: #999;">Languages</p>
                    <h3 style="font-size: 1.8rem;"><c:out value="${languages.size()}" default="0"/></h3>
                </div>
                <hr style="margin: 15px 0; border: 0; border-top: 1px solid #eee;">
                <p style="color: #666; font-size: 0.8rem;">Active programming categories</p>
            </div>
        </div>

        <div style="margin-top: 60px;">
            <div class="card" style="padding: 0; overflow: visible;">
                <div class="bg-red" style="margin: -20px 20px 0; padding: 20px; border-radius: 8px; box-shadow: 0 4px 20px rgba(0,0,0,0.14);">
                    <h3 style="color: white;">User Growth Percentage</h3>
                    <div class="chart-container">
                        <div class="bar" style="height: 30%;"></div>
                        <div class="bar" style="height: 50%;"></div>
                        <div class="bar" style="height: 40%;"></div>
                        <div class="bar" style="height: 80%;"></div>
                        <div class="bar highlight" style="height: 100%;"></div>
                    </div>
                </div>
                <div style="padding: 25px;">
                    <h4 style="color: #4caf50;">↑ 55% growth in user registrations</h4>
                    <p style="color: #999; font-size: 0.8rem; margin-top: 5px;">Performance compared to last week</p>
                    <hr style="margin: 15px 0; border: 0; border-top: 1px solid #eee;">
                    <p style="color: #666; font-size: 0.8rem;">Updated 4 minutes ago</p>
                </div>
            </div>
        </div>
    </div>

    <div id="langTab" class="tab-content">
    <h1>Manage Programming Languages</h1>
    
    <div class="card" style="margin-top: 20px;">
        <form action="AdminDashboard" method="post" style="display:flex; gap:10px; margin-bottom: 20px;">
            <input type="hidden" name="action" value="addLanguage">
            <input type="text" name="name" placeholder="Language Name" required>
            <input type="text" name="imagePath" placeholder="CSS Class (e.g. bg-php)" required>
            <button type="submit" class="btn-success">+ Add</button>
        </form>
        <table>
            <thead>
                <tr><th>Name</th><th>CSS Class</th><th>Actions</th></tr>
            </thead>
            <tbody>
                <c:forEach var="l" items="${languages}">
                    <tr>
                        <form action="AdminDashboard" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="updateLanguage">
                            <input type="hidden" name="id" value="${l.id}">
                            <td><input type="text" name="name" value="${l.name}"></td>
                            <td><input type="text" name="imagePath" value="${l.imagePath}"></td>
                            <td>
                                <button type="submit" class="btn-primary">Update</button>
                                <a href="AdminDashboard?action=delete&id=${l.id}" class="btn-danger" onclick="return confirm('Delete?')">Delete</a>
                            </td>
                        </form>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <h1 style="margin-top: 40px;">Manage All Topics & Snippets</h1>

<div class="card" style="margin-top: 20px;">

    <table>
        <thead>
            <tr>
                <th>Language</th>
                <th>Topic Title</th>
                <th>Description</th>
                <th style="width: 180px;">Actions</th>
            </tr>
        </thead>

        <tbody>

            <c:forEach var="topic" items="${allTopics}">

                <tr>

                    <td style="font-weight: bold; color: #3b82f6;">
                        ${topic.languageName}
                    </td>

                    <td>
                        ${topic.title}
                    </td>

                    <td style="font-size: 0.85rem; color: #64748b;">
                        ${topic.description}
                    </td>

                    <td>

                        <!-- EDIT BUTTON -->
                        <button type="button"
                                class="btn-primary"

                                data-id="${topic.id}"
                                data-title="${topic.title}"
                                data-desc="${topic.description}"
                                data-lang="${topic.languageId}"
                                data-code="<c:out value='${topic.codeContent}'/>"

                                onclick="editTopic(this)">

                            Edit

                        </button>


                        <!-- DELETE BUTTON -->
                        <a href="AdminDashboard?action=deleteTopic&id=${topic.id}"
                           class="btn-danger"
                           style="margin-left:10px; text-decoration:none;"
                           onclick="return confirm('Are you sure delete?')">

                            Delete

                        </a>

                    </td>

                </tr>

            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
    <div id="topicTab" class="tab-content">

    <h1>Create New Topic & Snippet</h1>

    <div class="card" style="margin-top: 20px; max-width: 800px;">

        <form action="AdminDashboard" method="post">

            <!-- ACTION -->
            <input type="hidden"
                   name="action"
                   id="form-action"
                   value="addTopic">

            <!-- TOPIC ID -->
            <input type="hidden"
                   name="id"
                   id="topic-id">

            <!-- LANGUAGE -->
            <div class="form-group">

                <label>Select Language</label>

                <select name="languageId"
                        id="topic-language"
                        required>

                    <option value="">
                        -- Choose Language --
                    </option>

                    <c:forEach var="l" items="${languages}">

                        <option value="${l.id}">
                            ${l.name}
                        </option>

                    </c:forEach>

                </select>

            </div>

            <!-- TITLE -->
            <div class="form-group">

                <label>Topic Title</label>

                <input type="text"
                       name="title"
                       id="topic-title"
                       required>

            </div>

            <!-- DESCRIPTION -->
            <div class="form-group">

                <label>Description</label>

                <textarea name="description"
                          id="topic-description"></textarea>

            </div>

            <!-- CODE -->
            <div class="form-group">

                <label>Code Snippet</label>

                <textarea name="codeContent"
                          id="topic-code"
                          class="code-area"
                          required></textarea>

            </div>

            <button type="submit"
                    class="btn-primary">

                Save Topic

            </button>

        </form>

    </div>

</div>

    <div id="userTab" class="tab-content">
        <h1>Registered Users</h1>
        <div class="card" style="margin-top: 20px;">
            <h3 style="margin-bottom: 15px; color: #475569;">Total Users: ${totalUsers}</h3>
            <table>
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Name</th>
                        <th>Email</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${userList}">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td>${user.email}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty userList}">
                        <tr>
                            <td colspan="3" style="text-align:center; padding: 20px;">No users registered yet.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

</div> 



<script>


function editTopic(btn){

    // form action change
    document.getElementById("form-action").value = "updateTopic";

    // set values
    document.getElementById("topic-id").value =
        btn.getAttribute("data-id");

    document.getElementById("topic-title").value =
        btn.getAttribute("data-title");

    document.getElementById("topic-description").value =
        btn.getAttribute("data-desc");

    document.getElementById("topic-language").value =
        btn.getAttribute("data-lang");

    document.getElementById("topic-code").value =
        btn.getAttribute("data-code");

    // go to topic tab
    document.getElementById("topicTab").classList.add("active");

    // remove other tabs
    let tabs = document.getElementsByClassName("tab-content");
    for(let i=0;i<tabs.length;i++){
        tabs[i].classList.remove("active");
    }

    document.getElementById("topicTab").classList.add("active");
}

    function showTab(evt, tabId) {
        var i, tabcontent, tablinks;
        tabcontent = document.getElementsByClassName("tab-content");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].classList.remove("active");
        }
        tablinks = document.getElementsByClassName("tab-link");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].classList.remove("active");
        }
        document.getElementById(tabId).classList.add("active");
        evt.currentTarget.classList.add("active");
    }
</script>

</body>
</html>