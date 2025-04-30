<%@ page import="model.Device" %>
<%@ page import="model.dao.DBManager" %>
<%@ page import="java.util.*" %>
<%
    DBManager manager = (DBManager) session.getAttribute("manager");
    Integer cartId = (Integer) session.getAttribute("cartId");
    List<Device> items = new ArrayList<>();
    if (cartId != null) {
        items = manager.getCartItems(cartId); // Add a method in DBManager to fetch cart items
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
            for (Device item : items) {
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
        <%
            }
        %>
    </table>
</body>
</html>