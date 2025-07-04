<%@page import="app.Models.*"%>
<%@page import="java.util.*"%>
<%
    String basePath = request.getContextPath();
%>
<% CategorieProduit categorie=(List<CategorieProduit>) request.getAttribute("categorie"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modification de categorie</title>
</head>
<body>
    <div>
        <form action="<%=basePath%>/categorieProduit/update" method="post">
            <input type="text" name="nom" value="<%=categorie.getNom()%>">
            <input type="text" name="descri" value="<%=categorie.getDescri()%>">
            
            <input type="submit" value="Modifier">
        </form>
    </div>
    
    <a href="<%=basePath%>/categorieProduit/all">Liste des categories</a>
</body>
</html>