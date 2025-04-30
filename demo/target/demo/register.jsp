<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>IoTBay - Register</title>
</head>
<body>
    <h1>Register</h1>
    <form method="POST">
        Name: <input type="text" name="name"><br>
        Email: <input type="text" name="email"><br>
        Password: <input type="password" name="password"><br>
        <input type="submit" value="Register">
    </form>

    <%
        if (request.getMethod().equalsIgnoreCase("POST")) {
            // Retrieve form data
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Create a User object (assuming you have a User class)
            User user = new User(name, email, password);

            // Store the User object in the session
            session.setAttribute("user", user);
            response.sendRedirect("login.jsp");
        }
    %>
</body>
</html>