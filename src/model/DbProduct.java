package model;

public class DbProduct {
    private int id;
    private String productName;

    public DbProduct(String productName) {
        this.productName = productName;
    }

    public DbProduct(int id, String productName) {
        this.id = id;
        this.productName = productName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
