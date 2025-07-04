<%@page import="app.Models.*"%>
<%@page import="java.util.*"%>
<%
    String basePath = request.getContextPath();
%>
<% List<CategorieProduit> categories=(List<CategorieProduit>) request.getAttribute("categories"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enregistrement de produits</title>
</head>
<body>
    <div>
        <form action="<%=basePath%>/categorieProduit/create" method="post">
            <input type="text" name="nom">
            <input type="text" name="descri">
            
            <input type="submit" value="Creer">
        </form>
    </div>
    
    <a href="<%=basePath%>/categorieProduit/all">Liste des categories</a>
</body>
</html>