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

// @WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
        CartDAO cartDAO = (CartDAO) session.getAttribute("cartDAO");

        if (item != null) {
            // Logic to add item to cart (simplified)
            session.setAttribute("cart", item);
        }
        response.sendRedirect("CartServlet");
    }

}
