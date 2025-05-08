package model;

import java.time.LocalDateTime;

public class Delivery {
    private int deliveryID;
    private int orderID;
    private int userID;
    private String status;
    private String address;
    private LocalDateTime shippedDate;
    private LocalDateTime estimatedArrivalDate;
    private LocalDateTime arrivalDate;

    // Constructors
    public Delivery() {}

    public Delivery(int deliveryID, int orderID, int userID, String status, String address,
                    LocalDateTime shippedDate, LocalDateTime estimatedArrivalDate, LocalDateTime arrivalDate) {
        this.deliveryID = deliveryID;
        this.orderID = orderID;
        this.userID = userID;
        this.status = status;
        this.address = address;
        this.shippedDate = shippedDate;
        this.estimatedArrivalDate = estimatedArrivalDate;
        this.arrivalDate = arrivalDate;
    }

    // Getters and Setters
    public int getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(LocalDateTime shippedDate) {
        this.shippedDate = shippedDate;
    }

    public LocalDateTime getEstimatedArrivalDate() {
        return estimatedArrivalDate;
    }

    public void setEstimatedArrivalDate(LocalDateTime estimatedArrivalDate) {
        this.estimatedArrivalDate = estimatedArrivalDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
