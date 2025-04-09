<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>IoTBay - Welcome</title>
</head>
<body>
    <h1>Welcome, ${user.name}!</h1>
    <p>Email: ${user.email}</p>
    <a href="main.jsp">Go to Dashboard</a>
</body>
</html>