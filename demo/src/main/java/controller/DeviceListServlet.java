package controller;

import java.io.IOException;
import java.sql.Connection;
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

import java.util.List;
import jakarta.servlet.RequestDispatcher;

public class DeviceListServlet extends HttpServlet {
    private DeviceDAO deviceDAO;

    public void init() {
        deviceDAO = new DeviceDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Device> deviceList = deviceDAO.getAllDevices();
        request.setAttribute("deviceList", deviceList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("device_list.jsp");
        dispatcher.forward(request, response);
    }
}
