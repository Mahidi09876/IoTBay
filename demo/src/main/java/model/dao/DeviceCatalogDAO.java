package model.dao;

import model.Device;
import java.sql.*;
import java.util.*;

public class DeviceCatalogDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/iotbaydb";
    private String jdbcUsername = "root";
    private String jdbcPassword = "password";

    private static final String SELECT_ALL_DEVICES = "SELECT * FROM device";

    protected Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<Device> getAllDevices() {
        List<Device> devices = new ArrayList<>();
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_DEVICES)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("device_id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                devices.add(new Device(id, name, type, price, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return devices;
    }
}
