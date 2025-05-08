package controller;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.*;

// @WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int orderItemId;
        int quantity;
        OrderDAO orderDAO = (OrderDAO) session.getAttribute("orderDAO");

        try {
            switch (action) {
                case "increment":
                    orderItemId = Integer.parseInt(request.getParameter("orderItemId"));
                    quantity = Integer.parseInt(request.getParameter("quantity"));
                    orderDAO.updateOrderItem(orderId, orderItemId, quantity + 1);
                    response.sendRedirect("order.jsp");
                    break;
                case "decrement":
                    orderItemId = Integer.parseInt(request.getParameter("orderItemId"));
                    quantity = Integer.parseInt(request.getParameter("quantity"));
                    if (quantity == 1) {
                        orderDAO.removeOrderItem(orderId, orderItemId);
                    } else {
                        orderDAO.updateOrderItem(orderId, orderItemId, quantity - 1);
                    }
                    response.sendRedirect("order.jsp");
                    break;
                case "cancel":
                    orderDAO.updateOrderStatus(orderId, "cancelled");
                    response.sendRedirect("order.jsp");
                    break;
                default:
                    throw new ServletException("Invalid action: " + action);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Database update failed", ex);
        }

    }

}
