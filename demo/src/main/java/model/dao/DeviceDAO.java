package model.dao;
import model.Device;
import java.sql.*;

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
                        resultSet.getInt("stock")
                    );
                }
            }
        }
        return null;
    }

    public void updateDeviceStock(int deviceId, int newStock) throws SQLException {
        String query = "UPDATE device SET stock = ? WHERE device_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, newStock);
            statement.setInt(2, deviceId);
            statement.executeUpdate();
        }
    }

}
