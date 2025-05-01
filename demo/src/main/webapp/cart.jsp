<jsp:include page="/ConnServlet"/>
<%@ page import="model.*" %>
<%@ page import="model.dao.*" %>
<%@ page import="java.util.*" %>

<%
    CartDAO cartDAO = (CartDAO) session.getAttribute("cartDAO");
    Integer cartId = (Integer) session.getAttribute("cartId");
    Map<Integer, Integer> items = new HashMap<>();

    // if (cartId != null) {
    //     // items = cartDAO.getCartItems(cartId); 
    //     items = cartDAO.getCartItems(1001); 
    // }

    items = cartDAO.getCartItems(1001); 

%>

<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart</title>

        <script>
        // Send a GET request to /ConnServlet before loading the page
        document.addEventListener("DOMContentLoaded", function () {
            fetch('/ConnServlet')
                .then(response => {
                    if (!response.ok) {
                        console.error('Failed to load ConnServlet:', response.statusText);
                        return;
                    }
                    console.log('ConnServlet loaded successfully');
                })
                .catch(error => {
                    console.error('Error loading ConnServlet:', error);
                });
        });
    </script>

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
    </table>

</body>
</html>