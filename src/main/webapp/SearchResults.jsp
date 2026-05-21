<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


		<h2>Results for "<%= request.getAttribute("searchQuery") %>"</h2>
<div class="results-grid">
    <c:forEach var="item" items="${searchResults}">
        <div class="result-card">
            <h4>${item.title}</h4>
            <p>${item.description}</p>
            <a href="ViewSnippet?id=${item.id}">Learn More →</a>
        </div>
    </c:forEach>
</div>





</body>
</html>