package model;

public class Cart {
    private int cartId;
    private int shoppingCartId;
    private int productId;
    private String productName;
    private int productQuantity;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Cart(int cartId, int shoppingCartId, int productId, int productQuantity) {
        this.cartId = cartId;
        this.shoppingCartId = shoppingCartId;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public Cart(int shoppingCartId, int productId, int productQuantity) {
        this.shoppingCartId = shoppingCartId;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public Cart() {
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(int shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
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
