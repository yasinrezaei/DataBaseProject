package model;

public class OrderItem {
    private int orderItemId;
    private int oredrId;
    private int productId;
    private int productQuantity;
    private String orderProductName;


    public OrderItem(int productQuantity, String orderProductName) {
        this.productQuantity = productQuantity;
        this.orderProductName = orderProductName;
    }

    public OrderItem(int productId) {
        this.productId = productId;
    }

    public OrderItem(int oredrId, int productId, int productQuantity) {
        this.oredrId = oredrId;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public String getOrderProductName() {
        return orderProductName;
    }

    public void setOrderProductName(String orderProductName) {
        this.orderProductName = orderProductName;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOredrId() {
        return oredrId;
    }

    public void setOredrId(int oredrId) {
        this.oredrId = oredrId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}
