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
    <form action="landing_page.jsp" method="post" style="display:inline;">
        <button type="submit" onclick="<% session.invalidate(); %>">Log Out</button>
    </form>
</body>
</html>