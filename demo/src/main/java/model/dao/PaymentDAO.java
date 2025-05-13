package model.dao;

import model.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private Connection conn;

    public PaymentDAO(Connection conn) {
        this.conn = conn;
    }

    // Get payments by search query
    public List<Payment> getPaymentsBySearchQuery(int userId, String paymentId, String paymentDate) throws SQLException {
        String sql = "SELECT * FROM Payment WHERE order_id IN (SELECT order_id FROM `Order` WHERE user_id = ?)";
        boolean hasIdFilter = paymentId != null && !paymentId.trim().isEmpty();
        boolean hasDateFilter = paymentDate != null && !paymentDate.trim().isEmpty();

        if (hasIdFilter) {
            sql += " AND CAST(payment_id AS CHAR) LIKE ?";
        }
        if (hasDateFilter) {
            sql += " AND DATE(paid_at) = ?";
        }

        List<Payment> payments = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int idx = 1;
            ps.setInt(idx++, userId);
            if (hasIdFilter) {
                ps.setString(idx++, "%" + paymentId.trim() + "%");
            }
            if (hasDateFilter) {
                ps.setString(idx++, paymentDate);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    payments.add(new Payment(
                        rs.getInt("payment_id"),
                        rs.getInt("order_id"),
                        rs.getString("method"),
                        rs.getString("card_number"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("paid_at"),
                        rs.getString("status")
                    ));
                }
            }
        }
        return payments;
    }

    // Create a new payment
    public void createPayment(Payment payment) throws SQLException {
        String sql = "INSERT INTO Payment (order_id, method, card_number, amount, status) VALUES (?, ?, ?, ?, 'draft')";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, payment.getOrderId());
            ps.setString(2, payment.getMethod());
            ps.setString(3, payment.getCardNumber());
            ps.setDouble(4, payment.getAmount());
            ps.executeUpdate();
        }
    }

    // Update an existing payment
    public void updatePayment(Payment payment) throws SQLException {
        String sql = "UPDATE Payment SET method = ?, card_number = ?, amount = ? WHERE payment_id = ? AND status = 'draft'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, payment.getMethod());
            ps.setString(2, payment.getCardNumber());
            ps.setDouble(3, payment.getAmount());
            ps.setInt(4, payment.getPaymentId());
            ps.executeUpdate();
        }
    }

    // Delete a payment
    public void deletePayment(int paymentId) throws SQLException {
        String sql = "DELETE FROM Payment WHERE payment_id = ? AND status = 'draft'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
            ps.executeUpdate();
        }
    }
}