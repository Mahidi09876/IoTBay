package controller;

import model.Payment;
import model.dao.PaymentDAO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class PaymentServlet extends HttpServlet {
    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        Connection conn = (Connection) getServletContext().getAttribute("dbConnection");
        paymentDAO = new PaymentDAO(conn);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "create":
                    createPayment(request, response);
                    break;
                case "update":
                    updatePayment(request, response);
                    break;
                case "delete":
                    deletePayment(request, response);
                    break;
                default:
                    response.sendRedirect("payment.jsp");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void createPayment(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String method = request.getParameter("method");
        String cardNumber = request.getParameter("cardNumber");
        double amount = Double.parseDouble(request.getParameter("amount"));

        Payment payment = new Payment(0, orderId, method, cardNumber, amount, null, "draft");
        paymentDAO.createPayment(payment);

        response.sendRedirect("payment.jsp");
    }

    private void updatePayment(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int paymentId = Integer.parseInt(request.getParameter("paymentId"));
        String method = request.getParameter("method");
        String cardNumber = request.getParameter("cardNumber");
        double amount = Double.parseDouble(request.getParameter("amount"));

        Payment payment = new Payment(paymentId, 0, method, cardNumber, amount, null, "draft");
        paymentDAO.updatePayment(payment);

        response.sendRedirect("payment.jsp");
    }

    private void deletePayment(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int paymentId = Integer.parseInt(request.getParameter("paymentId"));
        paymentDAO.deletePayment(paymentId);

        response.sendRedirect("payment.jsp");
    }
}