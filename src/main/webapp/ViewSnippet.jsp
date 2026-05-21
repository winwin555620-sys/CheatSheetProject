<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.CheatSheet.Model.HomeBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${snippet.title} - Details</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/themes/prism.min.css" rel="stylesheet" />
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #f8fafc; padding: 40px; color: #1e293b; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 40px; border-radius: 16px; box-shadow: 0 4px 20px rgba(0,0,0,0.08); position: relative; }
        .back-btn { text-decoration: none; color: #6366f1; font-weight: 600; margin-bottom: 25px; display: inline-block; }
        
        /* Language badge in the top right corner like your photo */
        .lang-badge { 
            position: absolute; top: 20px; right: 20px;
            background: #8b5cf6; color: white; padding: 6px 16px; 
            border-radius: 8px; font-size: 14px; font-weight: bold; 
        }
        
        h1 { margin: 10px 0; font-size: 2.8rem; color: #1e293b; }
        .desc { font-size: 1.2rem; color: #64748b; margin-bottom: 40px; }
        
        /* THE VERTICAL FIX CSS */
        pre { 
            background: #fdfdfd !important; 
            border: 1px solid #e2e8f0 !important;
            border-radius: 12px !important; 
            padding: 25px !important;
            /* Forces text to wrap and follow line breaks */
            white-space: pre-wrap !important; 
            word-wrap: break-word !important;
        }
        code { font-size: 16px !important; line-height: 1.8 !important; }
    </style>
</head>
<body>

    <div class="container">
<a href="Topics?id=${langId}" class="back-btn">← Back</a>
         <div>
            <h1>${snippet.title}</h1>
            <p class="desc">${snippet.description}</p>
        </div>

        <h3 style="margin-bottom: 15px; color: #334155;">Example Snippet:</h3>
        
        <pre><code class="language-java"><% 
            // This Java logic adds the vertical line breaks after each semicolon
            HomeBean snip = (HomeBean)request.getAttribute("snippet");
            if(snip != null && snip.getCode_content() != null) {
                out.print(snip.getCode_content().replace(";", ";\n"));
            }
        %></code></pre>
        
        
        
        <div class="notepad-container" style="margin-top: 30px; padding: 20px; background: white; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);">
    <h3 style="color: #1e293b; margin-bottom: 15px; font-size: 18px;">📝 My Personal Notes</h3>
    
    <form action="SaveNote" method="POST">
        <input type="hidden" name="snippetId" value="<%= request.getParameter("id") %>">
        <input type="hidden" name="languageId" value="<%= snip.getCategories_id() %>">
        
        <textarea name="noteContent" rows="6" 
            style="width: 100%; padding: 15px; border: 1px solid #e2e8f0; border-radius: 8px; font-family: inherit; resize: vertical;" 
            placeholder="Can you write for notes"><%= (request.getAttribute("existingNote") != null) ? request.getAttribute("existingNote") : "" %></textarea>
        
        <div style="text-align: right; margin-top: 10px;">
            <button type="submit" style="background: #4f46e5; color: white; padding: 10px 25px; border: none; border-radius: 6px; font-weight: 600; cursor: pointer; transition: 0.2s;">
                Save
            </button>
        </div>
    </form>
</div>
        
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/prism.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/components/prism-java.min.js"></script>
</body>
</html>