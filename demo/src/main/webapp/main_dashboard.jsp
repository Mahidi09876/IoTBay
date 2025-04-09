<%@ page import="beans.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>IoTBay - Dashboard</title>
</head>
<body>
    <h1>Main Dashboard</h1>
    <p>Logged in as: ${user.email}</p>
    <a href="logout.jsp">Logout</a>
</body>
</html>