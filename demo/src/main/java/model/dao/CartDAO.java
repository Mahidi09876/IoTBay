package model.dao;
import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CartDAO {

    private Connection conn;

    public CartDAO(Connection conn) throws SQLException {
        this.conn = conn;
    }

    public int createCart(int userId) throws SQLException {
        String query = "INSERT INTO Cart (user_id) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
            throw new SQLException("Cart creation failed, no ID obtained.");
        }
    }

    public Cart findCart(int cartId) throws SQLException {
        String query = "SELECT * FROM CartItem WHERE cart_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cart(rs.getInt("user_id"), rs.getInt("cart_id"), rs.getTimestamp("created_at"),
                        rs.getString("status"));
            }
            return null;
        }
    }

    public void addCartItem(int cartId, Device item, int quantity) throws SQLException {
        String query = "INSERT INTO CartItem (cart_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, item.getId());
            stmt.setInt(3, quantity);
            stmt.setDouble(4, item.getPrice());
            stmt.executeUpdate();
        }
    }

    public void removeCartItem(int cartId, String productId) throws SQLException {
        String query = "DELETE FROM CartItem WHERE cart_id = ? AND product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            stmt.setString(2, productId);
            stmt.executeUpdate();
        }
    }

    public void updateCartItem(int cartId, String productId, int quantity) throws SQLException {
        String query = "UPDATE CartItem SET quantity = ? WHERE cart_id = ? AND product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, cartId);
            stmt.setString(3, productId);
            stmt.executeUpdate();
        }
    }

    // Returns a list of items in the cart with their quantities added to cart
    public Map<Integer, Integer> getCartItems(Integer cartId) throws SQLException {
        String sql = 
            "SELECT " +
            "    d.device_id, " +
            "    d.device_name, " +
            "    d.device_type, " +
            "    d.unit_price, " +
            "    d.stock, " +
            "    ci.quantity " +
            "FROM CartItem ci " +
            "JOIN Device d ON ci.device_id = d.device_id " +
            "WHERE ci.cart_id = ?";
        
        Map<Integer, Integer> items = new HashMap<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
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

    public void submitCart(int cartId) throws SQLException {
        String query = "UPDATE Cart SET status = 'submitted' WHERE cart_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
        }
    }

}
