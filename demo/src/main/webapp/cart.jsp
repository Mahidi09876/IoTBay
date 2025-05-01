<%@ page import="model.*" %>
<%@ page import="model.dao.*" %>
<%@ page import="java.util.*" %>
<%
    CartDAO cartDAO = (CartDAO) session.getAttribute("manager");
    Integer cartId = (Integer) session.getAttribute("cartId");
    Map<Integer, Integer> items = new HashMap<>();
    if (cartId != null) {
        items = cartDAO.getCartItems(cartId); 
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>
    <h1>Your Cart</h1>
    <table border="1">
        <tr>
            <th>Product</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Total</th>
            <th>Actions</th>
        </tr>

        <%
        for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
            Integer productId = entry.getKey();
            Integer quantity = entry.getValue();
        %>
        <tr>
            <td><%= productId %></td>
            <td><%= quantity %></td>
            <%-- <td><%= cartDAO.getProductPrice(productId) %></td>
            <td><%= cartDAO.getProductPrice(productId) * quantity %></td> --%>
            <td>
            <form method="post" style="display:inline;">
                <input type="hidden" name="action" value="remove">
                <input type="hidden" name="productId" value="<%= productId %>">
                <button type="submit">Remove</button>
            </form>
            </td>
        </tr>
        <%
            }
        %>

        <%-- <tr>
            <td><%= item.getProductId() %></td>
            <td><%= item.getQuantity() %></td>
            <td><%= item.getPrice() %></td>
            <td><%= item.getPrice() * item.getQuantity() %></td>
            <td>
                <form action="OrderServlet" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="remove">
                    <input type="hidden" name="productId" value="<%= item.getProductId() %>">
                    <button type="submit">Remove</button>
                </form>
            </td>
        </tr> --%>
    </table>
    <jsp:include page="/ConnServlet" flush="true" />

</body>
</html>