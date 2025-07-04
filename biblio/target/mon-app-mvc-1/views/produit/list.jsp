<%@page import="app.Models.*"%>
<%@page import="java.util.*"%>
<% List<Produit> produits=(List<Produit>) request.getAttribute("produits"); %>
<%
    String basePath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des produits</title>
</head>
<body>
    <h3>Liste des produits</h3>
    <div>
        <table>
            <tr>
                <th>id</th>
                <th>nom</th>
                <th>prix</th>
                <th>descri</th>
                <th>categorie</th>
                <th></th>
                <th></th>
            

            </tr>
        <% for(Produit produit:produits){ %>
            <tr>
                <td><%=produit.getId_produit()%></td>
                <td><%=produit.getNom() %></td>
                <td><%=produit.getPrix()%></td>
                <td><%=produit.getDescri()%></td>
                <td><%=produit.getId_categorie_produit()%></td>
                <td><a href="<%=basePath%>/produit/update?id=<%=produit.getId_produit()%>">Modifier</a></td>
                <td><a href="<%=basePath%>/produit/delete?id=<%=produit.getId_produit()%>">Supprimer</a></td>
            </tr>
        <% } %>
        </table>
    </div>
</body>
</html>