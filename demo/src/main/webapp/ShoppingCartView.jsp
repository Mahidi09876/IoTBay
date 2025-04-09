<%@ page import="model.ShoppingCart" %>

<%
    ShoppingCart cart = new ShoppingCart("Bao");
%>

<%= cart.getUser() %>
