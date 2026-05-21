<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CheatSheet - Login</title>
    <style>
        body { 
            font-family: Arial, sans-serif; 
            display: flex; 
            justify-content: center; 
            align-items: center; 
            height: 100vh; 
            margin: 0;
            background-color: #f8f9fa; 
        }
        .login-card { 
            background: white; 
            padding: 40px; 
            border-radius: 10px; 
            box-shadow: 0 4px 15px rgba(0,0,0,0.1); 
            width: 350px; 
        }
        h2 { margin-top: 0; font-size: 28px; font-weight: bold; }
        label { display: block; margin-top: 15px; font-weight: 500; font-size: 16px; }
        .input-field { 
            width: 100%; 
            padding: 12px; 
            margin-top: 8px; 
            border: 1px solid #ced4da; 
            border-radius: 6px; 
            box-sizing: border-box; 
            font-size: 16px;
        }
        .error-msg { color: #dc3545; font-size: 14px; margin-bottom: 10px; }
        .toggle-container { 
            display: flex; 
            align-items: center; 
            margin-top: 8px; 
            font-size: 14px; 
            cursor: pointer;
        }
        .toggle-container input { width: auto; margin-right: 8px; }
        .sign-in-btn { 
            width: 100%; 
            padding: 12px; 
            background-color: #28a745; 
            color: white; 
            border: none; 
            border-radius: 6px; 
            cursor: pointer; 
            font-size: 16px; 
            font-weight: bold;
            margin-top: 20px;
        }
        .sign-in-btn:hover { background-color: #218838; }
    </style>
</head>
<body>

<div class="login-card">
    <h2>Register</h2>
    
    <% if (request.getAttribute("error") != null) { %>
        <p class="error-msg"><%= request.getAttribute("error") %></p>
    <% } %>

    <form action="RegisterLogin" method="post">
        <label for="username">Username</label>
        <input type="text" id="username" name="username" class="input-field" 
               placeholder="Enter username" required>

        <label for="email">Email</label>
        <input type="email" id="email" name="email" class="input-field" 
               placeholder="Enter email" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" class="input-field" 
               placeholder="Enter password" required>
        
        <label class="toggle-container">
            <input type="checkbox" onclick="togglePasswordVisibility()"> Show Password
        </label>

        <button type="submit" class="sign-in-btn">Sign In</button>
    </form>
</div>

<script>
    function togglePasswordVisibility() {
        var x = document.getElementById("password");
        if (x.type === "password") {
            x.type = "text";
        } else {
            x.type = "password";
        }
    }
</script>

</body>
</html>