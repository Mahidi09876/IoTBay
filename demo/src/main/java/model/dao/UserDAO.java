package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;

public class UserDAO {

    // Create a new user in the database
    public boolean createUser(User user) {
        String sql = "INSERT INTO Users (userID, name, email, password, phoneNumber, address) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            // Establish database connection
            DBConnector db = new DBConnector();
            Connection conn = db.openConnection();

            // Prepare SQL statement
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getUserID());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getPhoneNumber());
            stmt.setString(6, user.getAddress());

            // Execute insert operation
            boolean result = stmt.executeUpdate() > 0;

            // Close connection
            db.closeConnection();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve a user by their userID
    public User getUserById(int userID) {
        String sql = "SELECT * FROM Users WHERE userID = ?";

        try {
            DBConnector db = new DBConnector();
            Connection conn = db.openConnection();

            // Prepare query
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            // Populate User object from result
            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("userID"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setAddress(rs.getString("address"));
                db.closeConnection();
                return user;
            }

            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Update an existing user's information
    public boolean updateUser(User user) {
        String sql = "UPDATE Users SET name=?, email=?, password=?, phoneNumber=?, address=? WHERE userID=?";

        try {
            DBConnector db = new DBConnector();
            Connection conn = db.openConnection();

            // Set updated values
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setString(5, user.getAddress());
            stmt.setInt(6, user.getUserID());

            boolean result = stmt.executeUpdate() > 0;
            db.closeConnection();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a user from the database
    public boolean deleteUser(int userID) {
        String sql = "DELETE FROM Users WHERE userID = ?";

        try {
            DBConnector db = new DBConnector();
            Connection conn = db.openConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);

            boolean result = stmt.executeUpdate() > 0;
            db.closeConnection();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if a userID already exists (used to ensure uniqueness)
    public boolean userIdExists(int userID) {
        String sql = "SELECT userID FROM Users WHERE userID = ?";

        try {
            DBConnector db = new DBConnector();
            Connection conn = db.openConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            boolean exists = rs.next();
            db.closeConnection();
            return exists;

        } catch (Exception e) {
            e.printStackTrace();
            return true; // Assume it exists in case of DB error
        }
    }
}
