package model;

import java.util.ArrayList;

public class Order {
    private int orderId;
    private String orderDate;
    private int orderStatus;
    private String orderStatusName;
    private int orderAddress;
    private int orderSendingMethod;
    private int orderUserId;
    private int finished;


    public Order(String orderDate, int orderStatus, int orderAddress, int orderSendingMethod, int orderUserId, int finished) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderAddress = orderAddress;
        this.orderSendingMethod = orderSendingMethod;
        this.orderUserId = orderUserId;
        this.finished = finished;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public int getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(int orderUserId) {
        this.orderUserId = orderUserId;
    }

    private ArrayList<OrderItem> orderItems;

    public Order( ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public int getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(int orderAddress) {
        this.orderAddress = orderAddress;
    }

    public int getOrderSendingMethod() {
        return orderSendingMethod;
    }

    public void setOrderSendingMethod(int orderSendingMethod) {
        this.orderSendingMethod = orderSendingMethod;
    }

    public Order(int orderId, String orderDate, int orderStatus) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }
}
