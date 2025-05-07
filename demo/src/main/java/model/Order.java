package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {
    private int cartId;
    private int userId;
    private Timestamp createdAt;
    private String status;
    private Map<Integer, Integer> items;

    public Order(int cartId, int userId, Timestamp createdAt, String status) {
        this.cartId = cartId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.status = status;
        this.items = new HashMap<>();
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<Integer, Integer> getItems() {
        return items;
    }

    public void addItem(Integer itemID, int quantity) {
        items.put(itemID, items.getOrDefault(itemID, 0) + quantity);
    }

    public void removeItem(Integer itemID) {
        items.remove(itemID);
    }

    public void adjustQuantity(Integer itemID, int quantity) {
        if (items.containsKey(itemID)) {
            if (quantity <= 0) {
                items.remove(itemID); // Remove item if quantity is zero or less
            } else {
                items.put(itemID, quantity);
            }
        }
    }

    public void clearCart() {
        items.clear();
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "cartId=" + cartId +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                ", status='" + status + '\'' +
                ", items=" + items +
                '}';
    }
}