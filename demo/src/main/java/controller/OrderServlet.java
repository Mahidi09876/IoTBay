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
        int cartId = Integer.parseInt(request.getParameter("cartId"));
        int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        OrderDAO orderDAO = (OrderDAO) session.getAttribute("orderDAO");

        try {
            if ("increment".equals(action)) {
                orderDAO.updateOrderItem(cartId, cartItemId, quantity + 1);
                response.sendRedirect("cart.jsp");
            } else if ("decrement".equals(action)) {
                orderDAO.updateOrderItem(cartId, cartItemId, quantity - 1);
                response.sendRedirect("cart.jsp");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Database update failed", ex);
        }

    }

}
