<%@ page import="model.User" %>

<!DOCTYPE html>
<html>
<head>
    <title>IoTBay - Login</title>
</head>
<body>
    <h1>Login</h1>

    <%
    String errorMessage = (String) session.getAttribute("errMsg");
    %>

    <form action="LoginServlet" method="POST">
        Email: <input type="text" name="email" required><br>
        Password: <input type="password" name="password" required><br>
        <input type="submit" name="loginButton" value="Login">
    </form>
    <h2 style="color: red;">
        <%= errorMessage %>
    </h2>
</body>
</html>
