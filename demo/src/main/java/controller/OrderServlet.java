package controller;
import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.CartDAO;
import model.dao.DBManager;

public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        CartDAO cartDAO = (CartDAO) session.getAttribute("manager");

        // Retrieve or create the cart ID
        Integer cartId = (Integer) session.getAttribute("cartId");
        if (cartId == null) {
            try {
                cartId = cartDAO.createCart((int) session.getAttribute("userId")); // Assuming userId is stored in session
                session.setAttribute("cartId", cartId);
            } catch (SQLException e) {
                throw new ServletException("Error creating cart", e);
            }
        }


    }

}
