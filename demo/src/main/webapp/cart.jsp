<jsp:include page="/ConnServlet"/>
<%@ page import="model.*" %>
<%@ page import="model.dao.*" %>
<%@ page import="java.util.*" %>

<%
    CartDAO cartDAO = (CartDAO) session.getAttribute("cartDAO");
    DeviceDAO deviceDAO = (DeviceDAO) session.getAttribute("deviceDAO");
    Integer cartId = (Integer) session.getAttribute("cartId"); // Should be userID, get cartID from userID
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
            Device cartItem = deviceDAO.getDeviceById(entry.getKey());
            Integer quantity = entry.getValue();
        %>
        <tr>
            <td><%= cartItem.getId() %></td>
            <td>
                <!-- Decrement button -->
                <form method="POST" action="CartServlet" style="display:inline;">
                    <input type="hidden" name="action" value="decrement"/>
                    <input type="hidden" name="cartItemId" value="${cartItem.getId()}"/>
                    <button type="submit">-</button>
                </form>

                <!-- Display quantity -->
                <span><%= quantity %></span>

                <!-- Increment button -->
                <form method="POST" action="CartServlet" style="display:inline;">
                    <input type="hidden" name="action" value="increment"/>
                    <input type="hidden" name="cartItemId" value="${cartItem.getId()}"/>
                    <button type="submit">+</button>
                </form>
            </td>
            <td><%= cartItem.getPrice() %></td>
            <td><%= cartItem.getPrice() * quantity %></td>
            <td>
            <form action="CartServlet" method="POST" style="display:inline;">
                <input type="hidden" name="action" value="remove">
                <input type="hidden" name="productId" value="<%= cartItem.getId() %>">
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