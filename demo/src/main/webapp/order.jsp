<jsp:include page="/ConnServlet"/>
<%@ page import="model.*" %>
<%@ page import="model.dao.*" %>
<%@ page import="java.util.*" %>

<%
    OrderDAO orderDAO = (OrderDAO) session.getAttribute("orderDAO");
    DeviceDAO deviceDAO = (DeviceDAO) session.getAttribute("deviceDAO");
    Map<Integer, Integer> items = new HashMap<>();

    // if (orderId != null) {
    //     // items = orderDAO.getOrderItems(orderId); 
    //     items = orderDAO.getOrderItems(1001); 
    // }

    int orderId = 1;
    items = orderDAO.getOrderItems(orderId); 

%>

<!DOCTYPE html>
<html>
<head>
    <title>Orders</title>

</head>
<body>
    <h1>Your Orders</h1>
    <table border="1">
        <tr>
            <th>Product</th>
            <th>Product Name</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Total</th>
            <th>Actions</th>
        </tr>

        <%
        for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
            Device orderItem = deviceDAO.getDeviceById(entry.getKey());
            Integer quantity = entry.getValue();
        %>
        <tr>
            <td><%= orderItem.getId() %></td>
            <td><%= orderItem.getName() %></td>
            <td>
                <!-- Decrement button -->
                <form method="POST" action="OrderServlet" style="display:inline;">
                    <input type="hidden" name="action" value="decrement"/>
                    <input type="hidden" name="orderId" value="<%= orderId %>"/>
                    <input type="hidden" name="orderItemId" value="<%= orderItem.getId() %>"/>
                    <input type="hidden" name="quantity" value="<%= quantity %>"/>
                    <button type="submit">-</button>
                </form>

                <!-- Display quantity -->
                <span><%= quantity %></span>

                <!-- Increment button -->
                <form method="POST" action="OrderServlet" style="display:inline;">
                    <input type="hidden" name="action" value="increment"/>
                    <input type="hidden" name="orderId" value="<%= orderId %>"/>
                    <input type="hidden" name="orderItemId" value="<%= orderItem.getId() %>"/>
                    <input type="hidden" name="quantity" value="<%= quantity %>"/>
                    <button type="submit">+</button>
                </form>
            </td>
            <td><%= orderItem.getPrice() %></td>
            <td><%= orderItem.getPrice() * quantity %></td>
            <td>
            <form action="OrderServlet" method="POST" style="display:inline;">
                <input type="hidden" name="action" value="remove">
                <input type="hidden" name="productId" value="<%= orderItem.getId() %>">
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