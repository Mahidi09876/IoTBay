<%@ page import="java.util.*" %>
<%@ page import="model.*" %>
<%@ page import="model.dao.*" %>

<%
    PaymentDAO paymentDAO = (PaymentDAO) session.getAttribute("paymentDAO");
    Integer userId = 1; // TODO: Retrieve from session
    String searchPaymentId = request.getParameter("paymentId") != null ? request.getParameter("paymentId") : "";
    String searchPaymentDate = request.getParameter("paymentDate") != null ? request.getParameter("paymentDate") : "";
    List<Payment> payments = paymentDAO.getPaymentsBySearchQuery(userId, searchPaymentId, searchPaymentDate);
%>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="css/global.css">
    <link rel="stylesheet" href="css/payment.css">
    <title>Payment Management</title>
</head>
<body>
    <div class="payment-page">
        <h1>Payment Management</h1>

        <!-- Search Form -->
        <form class="search-form" method="GET" action="payment.jsp">
            <input type="text" name="paymentId" placeholder="Search by Payment ID" class="search-input" />
            <input type="date" name="paymentDate" placeholder="Search by Date" class="search-input" />
            <button type="submit" class="search-btn">Search</button>
        </form>

        <!-- Create Payment Form -->
        <h2>Create Payment</h2>
        <form method="POST" action="PaymentServlet" class="create-payment-form">
            <input type="hidden" name="action" value="create" />
            <label for="orderId">Order ID:</label>
            <input type="text" name="orderId" required />
            <label for="method">Payment Method:</label>
            <select name="method" required>
                <option value="Credit Card">Credit Card</option>
                <option value="Debit Card">Debit Card</option>
                <option value="PayPal">PayPal</option>
            </select>
            <label for="cardNumber">Card Number:</label>
            <input type="text" name="cardNumber" required />
            <label for="amount">Amount:</label>
            <input type="number" step="0.01" name="amount" required />
            <button type="submit" class="create-btn">Create Payment</button>
        </form>

        <!-- Payment History -->
        <h2>Payment History</h2>
        <table class="payment-table" border="1">
            <tr>
                <th>Payment ID</th>
                <th>Order ID</th>
                <th>Method</th>
                <th>Card Number</th>
                <th>Amount</th>
                <th>Date</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            <% for (Payment payment : payments) { %>
            <tr>
                <td><%= payment.getPaymentId() %></td>
                <td><%= payment.getOrderId() %></td>
                <td><%= payment.getMethod() %></td>
                <td><%= payment.getCardNumber() %></td>
                <td>$<%= String.format("%.2f", payment.getAmount()) %></td>
                <td><%= payment.getPaidAt() %></td>
                <td><%= payment.getStatus() %></td>
                <td>
                    <!-- Update Payment -->
                    <form method="POST" action="PaymentServlet" class="update-form">
                        <input type="hidden" name="action" value="update" />
                        <input type="hidden" name="paymentId" value="<%= payment.getPaymentId() %>" />
                        <button type="submit" class="update-btn <%= payment.getStatus().equals("completed") ? "disabled" : "" %>">Update</button>
                    </form>

                    <!-- Delete Payment -->
                    <form method="POST" action="PaymentServlet" class="delete-form">
                        <input type="hidden" name="action" value="delete" />
                        <input type="hidden" name="paymentId" value="<%= payment.getPaymentId() %>" />
                        <button type="submit" class="delete-btn <%= payment.getStatus().equals("completed") ? "disabled" : "" %>">Delete</button>
                    </form>
                </td>
            </tr>
            <% } %>
        </table>
    </div>
</body>
</html>