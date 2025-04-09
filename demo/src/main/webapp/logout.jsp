<%
    session.invalidate(); // Destroy session
    response.sendRedirect("index.jsp"); // Redirect to home
%>