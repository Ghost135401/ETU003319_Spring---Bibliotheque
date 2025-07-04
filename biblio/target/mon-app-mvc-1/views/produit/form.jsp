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
        <form action="<%=basePath%>/produit/create" method="post">
            <input type="text" name="nom">
            <select name="categorie">
                <% for(CategorieProduit categorie:categories){ %>
                    <option value="<%= categorie.getId_categorie_produit() %>"><%= categorie.getNom() %></option>
                <% } %>
            <input type="number" name="prix">

            <input type="text" name="descri">
            <input type="submit" value="Creer">
        </form>
    </div>
    
    <a href="<%=basePath%>/produit/all">Liste des produits</a>
</body>
</html>