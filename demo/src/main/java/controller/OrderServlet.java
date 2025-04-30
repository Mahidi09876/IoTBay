package controller;
import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.DBManager;

public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        DBManager manager = (DBManager) session.getAttribute("manager");

        // Retrieve or create the cart ID
        Integer cartId = (Integer) session.getAttribute("cartId");
        if (cartId == null) {
            try {
                cartId = manager.createCart((Integer) session.getAttribute("userId")); // Assuming userId is stored in session
                session.setAttribute("cartId", cartId);
            } catch (SQLException e) {
                throw new ServletException("Error creating cart", e);
            }
        }


    }

}
