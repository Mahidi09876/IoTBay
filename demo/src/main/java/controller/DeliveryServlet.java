package controller;

import model.dao.DeliveryDAO;
import model.Delivery;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// @WebServlet("/DeliveryServlet")
public class DeliveryServlet extends HttpServlet {

    /**
     * Handles POST requests for creating or updating delivery records.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        System.out.println("DeliveryServlet.doPost() called with action: " + action);

        DeliveryDAO deliveryDAO = (DeliveryDAO) session.getAttribute("deliveryDAO");
        if (deliveryDAO == null) {
            deliveryDAO = new DeliveryDAO();
            session.setAttribute("deliveryDAO", deliveryDAO);
        }

        try {
            switch (action) {
                case "create":
                    createDelivery(request, response, deliveryDAO);
                    break;
                case "update":
                    updateDelivery(request, response, deliveryDAO);
                    break;
                default:
                    throw new ServletException("Invalid action: " + action);
            }
        } catch (SQLException e) {
            Logger.getLogger(DeliveryServlet.class.getName()).log(Level.SEVERE, null, e);
            throw new ServletException("Database operation failed", e);
        }
    }

    /**
     * Handles GET requests for viewing or deleting deliveries.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        System.out.println("DeliveryServlet.doGet() called with action: " + action);

        DeliveryDAO deliveryDAO = (DeliveryDAO) session.getAttribute("deliveryDAO");
        if (deliveryDAO == null) {
            deliveryDAO = new DeliveryDAO();
            session.setAttribute("deliveryDAO", deliveryDAO);
        }

        try {
            switch (action) {
                case "viewAll":
                    viewAllDeliveries(request, response, deliveryDAO);
                    break;
                case "delete":
                    deleteDelivery(request, response, deliveryDAO);
                    break;
                default:
                    response.sendRedirect("index.jsp");
            }
        } catch (SQLException e) {
            Logger.getLogger(DeliveryServlet.class.getName()).log(Level.SEVERE, null, e);
            throw new ServletException("Database fetch failed", e);
        }
    }
}
