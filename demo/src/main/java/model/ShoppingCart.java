package model;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart implements Serializable {
    private String user;
    private Map<Integer, Integer> items;

    public ShoppingCart(String user) {
        this.user = user;
        this.items = new HashMap<>();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
                "user='" + user + '\'' +
                ", items=" + items +
                '}';
    }
}