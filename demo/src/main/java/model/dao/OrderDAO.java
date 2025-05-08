package model.dao;
import model.*;
import java.sql.*;
import java.util.*;

public class OrderDAO {

    private Connection conn;

    public OrderDAO(Connection conn) throws SQLException {
        this.conn = conn;
    }

    public int createOrder(int userId) throws SQLException {
        String query = "INSERT INTO order (user_id) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
            throw new SQLException("Order creation failed, no ID obtained.");
        }
    }

    public Integer getOrderIdByUserId(int userId) throws SQLException {
        String query = "SELECT Order_id FROM Order WHERE user_id = ? AND status = 'ACTIVE'";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Order_id");
            }
            return -1; 
        }
    }

    public Order findOrder(int orderId) throws SQLException {
        String query = "SELECT * FROM OrderItem WHERE order_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Order(rs.getInt("user_id"), rs.getInt("order_id"), rs.getTimestamp("created_at"),
                        rs.getString("status"));
            }
            return null;
        }
    }

    public void addOrderItem(int orderId, Device item, int quantity) throws SQLException {
        String query = "INSERT INTO OrderItem (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, item.getId());
            stmt.setInt(3, quantity);
            stmt.setDouble(4, item.getPrice());
            stmt.executeUpdate();
        }
    }

    public void removeOrderItem(int orderId, String productId) throws SQLException {
        String query = "DELETE FROM OrderItem WHERE order_id = ? AND product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            stmt.setString(2, productId);
            stmt.executeUpdate();
        }
    }

    public void updateOrderItem(int orderId, int productId, int quantity) throws SQLException {
        String query = "UPDATE orderitem SET quantity = ? WHERE order_id = ? AND device_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, orderId);
            stmt.setInt(3, productId);
            stmt.executeUpdate();
        }
    }

    public List<Integer> getOrderIdsByStatus(int userId, String status) throws SQLException {
        String sql = "SELECT order_id FROM `order` WHERE user_id = ? AND status = ?";
        List<Integer> orderIds = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orderIds.add(rs.getInt("order_id"));
                }
            }
        }
        return orderIds;
    }

    // Returns a list of items in the order with their quantities added to order
    public Map<Integer, Integer> getOrderItems(Integer orderId) throws SQLException {
        String sql = 
            "SELECT " +
            "    d.device_id, " +
            "    d.name, " +
            "    d.type, " +
            "    d.unit_price, " +
            "    d.stock, " +
            "    ci.quantity " +
            "FROM OrderItem ci " +
            "JOIN Device d ON ci.device_id = d.device_id " +
            "WHERE ci.order_id = ?";
        
        Map<Integer, Integer> items = new HashMap<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.put(
                        rs.getInt("device_id"), 
                        rs.getInt("quantity")
                    );
                }
            }
        }
        return items;
    }

    public void submitOrder(int orderId) throws SQLException {
        String query = "UPDATE Order SET status = 'submitted' WHERE order_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
        }
    }

}
