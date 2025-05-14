<jsp:include page="/ConnServlet" />
<%@ page import="model.*" %>
<%@ page import="model.dao.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>IoTBay - Dashboard</title>
</head>
<body>
    <h1>Main Dashboard</h1>
    <p>Logged in as: ${user.email}</p>
    <a href="logout.jsp">Logout</a>

    <h2>Device Catalogue</h2>
    <table border="1">
        <tr>
            <th>Device Name</th>
            <th>Type</th>
            <th>Price</th>
            <th>Stock</th>
        </tr>
        <c:forEach var="device" items="${deviceList}">
            <tr>
                <td>${device.name}</td>
                <td>${device.type}</td>
                <td>${device.price}</td>
                <td>${device.stock}</td>
            </tr>
        </c:forEach>
    </table>
    
</body>
</html>