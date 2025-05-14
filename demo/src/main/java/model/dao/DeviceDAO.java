package model.dao;

import model.Device;
import java.sql.*;
import java.util.*;

public class DeviceDAO {
    private Connection connection;

    public DeviceDAO(Connection connection) {
        this.connection = connection;
    }

    public Device getDeviceById(int id) throws SQLException {
        String query = "SELECT * FROM device WHERE device_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Device(
                            resultSet.getInt("device_id"),
                            resultSet.getString("name"),
                            resultSet.getString("type"),
                            resultSet.getDouble("unit_price"),
                            resultSet.getInt("stock"));
                }
            }
        }
        return null; // Return null if no device is found
    }

    public int getStock(int deviceId) throws SQLException {
        String query = "SELECT stock FROM Device WHERE device_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, deviceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stock");
                }
                throw new SQLException("Device not found: " + deviceId);
            }
        }
    }

    public void updateStock(int deviceId, int newStock) throws SQLException {
        String query = "UPDATE device SET stock = ? WHERE device_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, newStock);
            statement.setInt(2, deviceId);
            statement.executeUpdate();
        }
    }

}
