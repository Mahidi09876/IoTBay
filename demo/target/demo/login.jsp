<%@ page import="model.User" %>

<!DOCTYPE html>
<html>
<head>
    <title>IoTBay - Login</title>
</head>
<body>
    <h1>Login</h1>
    <%
        // Check if the login button was clicked
        String loginButton = request.getParameter("loginButton");

        if (loginButton != null) {
            String loginEmail = request.getParameter("email");
            String loginPassword = request.getParameter("password");
            User registeredUser = (User) session.getAttribute("user");

            if (registeredUser != null &&
                loginEmail.equals(registeredUser.getEmail()) && loginPassword.equals(registeredUser.getPassword())) {
                // Login successful, redirect to welcome page
                response.sendRedirect("welcome_page.jsp");
            } else {
                // Login failed
    %>
                <p style="color: red;">Invalid email or password. Please try again.</p>
    <%
            }
        }
    %>
    <form action="LoginServlet" method="POST">
        Email: <input type="text" name="email" required><br>
        Password: <input type="password" name="password" required><br>
        <input type="submit" name="loginButton" value="Login">
    </form>
</body>
</html>
