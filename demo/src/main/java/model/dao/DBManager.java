package model.dao;

import model.*;
import java.sql.*;

/* 
* DBManager is the primary DAO class to interact with the database. 
* Complete the existing methods of this classes to perform CRUD operations with the db.
*/

public class DBManager {

    private Connection conn;

    public DBManager(Connection conn) throws SQLException {
        this.conn = conn;
    }

    // Find user by email and password in the database
    public User findUser(String email, String password) throws SQLException {
        // setup the select sql query string
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, email);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            return new User(rs.getString("name"), rs.getString("email"), rs.getString("password"));
        }
        return null;
    }


    // update a user details in the database
    public void updateUser(String email, String name, String password, String gender, String favcol)
            throws SQLException {
        // code for update-operation

    }

    // delete a user from the database
    public void deleteUser(String email) throws SQLException {
        // code for delete-operation

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

    public ShoppingCart findCart(int cartId) throws SQLException {
        String query = "SELECT * FROM CartItem WHERE cart_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ShoppingCart(rs.getInt("user_id"), rs.getInt("cart_id"), rs.getTimestamp("created_at"), rs.getString("status"));
            }
            return null;
        }
    }

    public void addCartItem(int cartId, Device item) throws SQLException {
        String query = "INSERT INTO CartItem (cart_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, item.getId());
            stmt.setInt(3, item.getQuantity());
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

    public void submitCart(int cartId) throws SQLException {
        String query = "UPDATE Cart SET status = 'submitted' WHERE cart_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
        }
    }

}
