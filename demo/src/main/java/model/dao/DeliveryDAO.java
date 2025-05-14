package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Delivery;

public class DeliveryDAO {

    public boolean createDelivery(Delivery d) {
        String sql = "INSERT INTO Deliveries (trackingId, orderId, userId, status, estimatedDeliveryDate, carrier) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            DBConnector db = new DBConnector();
            Connection conn = db.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, d.getTrackingId());
            stmt.setString(2, d.getOrderId());
            stmt.setString(3, d.getUserId());
            stmt.setString(4, d.getStatus());
            stmt.setDate(5, new java.sql.Date(d.getEstimatedDeliveryDate().getTime()));
            stmt.setString(6, d.getCarrier());

            boolean result = stmt.executeUpdate() > 0;
            db.closeConnection();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Delivery getDeliveryByTrackingId(String trackingId) {
        String sql = "SELECT * FROM Deliveries WHERE trackingId = ?";
        try {
            DBConnector db = new DBConnector();
            Connection conn = db.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, trackingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Delivery d = new Delivery(
                        rs.getString("trackingId"),
                        rs.getString("orderId"),
                        rs.getString("userId"),
                        rs.getString("status"),
                        rs.getDate("estimatedDeliveryDate"),
                        rs.getString("carrier")
                );
                db.closeConnection();
                return d;
            }
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Delivery> getAllDeliveriesByUser(String userId) {
        List<Delivery> deliveries = new ArrayList<>();
        String sql = "SELECT * FROM Deliveries WHERE userId = ?";

        try {
            DBConnector db = new DBConnector();
            Connection conn = db.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                deliveries.add(new Delivery(
                        rs.getString("trackingId"),
                        rs.getString("orderId"),
                        rs.getString("userId"),
                        rs.getString("status"),
                        rs.getDate("estimatedDeliveryDate"),
                        rs.getString("carrier")
                ));
            }
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deliveries;
    }

    public boolean updateDelivery(Delivery d) {
        String sql = "UPDATE Deliveries SET status=?, estimatedDeliveryDate=?, carrier=? WHERE trackingId=?";
        try {
            DBConnector db = new DBConnector();
            Connection conn = db.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, d.getStatus());
            stmt.setDate(2, new java.sql.Date(d.getEstimatedDeliveryDate().getTime()));
            stmt.setString(3, d.getCarrier());
            stmt.setString(4, d.getTrackingId());

            boolean result = stmt.executeUpdate() > 0;
            db.closeConnection();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDelivery(String trackingId) {
        String sql = "DELETE FROM Deliveries WHERE trackingId = ?";
        try {
            DBConnector db = new DBConnector();
            Connection conn = db.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, trackingId);
            boolean result = stmt.executeUpdate() > 0;
            db.closeConnection();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
