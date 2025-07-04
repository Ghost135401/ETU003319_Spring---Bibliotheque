<%@page import="app.Models.*"%>
<%@page import="java.util.*"%>
<%
    String basePath = request.getContextPath();
%>
<%
Produit produit=(Produit) request.getAttribute("produit");
List<CategorieProduit> categories=(List<CategorieProduit>) request.getAttribute("categories"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enregistrement de produits</title>
</head>
<body>
    <div>
        <form action="<%=basePath%>/produit/update?id=<%=produit.getId_produit()%>" method="post">
            <input type="text" name="nom" value="<%=produit.getNom()%>">
            <select name="categorie" id="categorie">
                <% for(CategorieProduit categorie:categories){ %>
                    <option value="<%= categorie.getId_categorie_produit() %>"><%= categorie.getNom() %></option>
                <% } %>
            <input type="number" name="prix" value="<%=produit.getPrix()%>">

            <input type="text" name="descri" value="<%=produit.getDescri()%>">
            <input type="submit" value="Creer">
        </form>
    </div>
    
    <a href="<%=basePath%>/produit/all">Liste des produits</a>
</body>
</html>
<script>
    document.getElementById("categorie").value = "<%=produit.getId_categorie_produit()%>";
</script>