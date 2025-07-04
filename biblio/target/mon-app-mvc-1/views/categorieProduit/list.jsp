<%@page import="app.Models.*"%>
<%@page import="java.util.*"%>
<% List<CategorieProduit> categories=(List<CategorieProduit>) request.getAttribute("categories"); %>
<%
    String basePath=request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des categories de produits</title>
</head>
<body>
    <h3>Liste des categories de produit</h3>
    <table>
            <tr>
                <th>id</th>
                <th>nom</th>
                <th>descri</th>
                <th></th>
                <th></th>
            

            </tr>
        <% for(CategorieProduit categorie:categories){ %>
            <tr>
                <td><%=categorie.getId_categorie_produit()%></td>
                <td><%=categorie.getNom() %></td>
                <td><%=categorie.getDescri()%></td>
                <td><a href="<%=basePath%>/categorieProduit/update?id=<%=categorie.getId_categorie_produit()%>">Modifier</a></td>
                <td><a href="<%=basePath%>/categorieProduit/delete?id=<%=categorie.getId_categorie_produit()%>">Supprimer</a></td>
            </tr>
        <% } %>
        </table>
    </div>
</body>
</html>