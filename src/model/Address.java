package model;

public class Address {
    private int addressId;
    private String addressText;
    private int userId;

    public Address(int addressId, String addressText, int userId) {
        this.addressId = addressId;
        this.addressText = addressText;
        this.userId = userId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddressText() {
        return addressText;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
