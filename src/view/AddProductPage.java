package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddProductPage extends VBox {
    private TextField productId,productName,productPrice;
    private Button addBtn;

    public AddProductPage() {
        Label productIdLbl =new Label("Product Id :");
        productId =new TextField();
        HBox productIdHbox =new HBox(productIdLbl,productId);
        productIdHbox.setSpacing(10);
        productIdHbox.setAlignment(Pos.CENTER);

        productName=new TextField();
        Label productNameLbl =new Label("Product Name :");
        HBox productNameHbox =new HBox(productNameLbl,productName);
        productNameHbox.setSpacing(10);
        productNameHbox.setAlignment(Pos.CENTER);


        productPrice=new TextField();
        Label productPriceLbl =new Label("Product Price :");
        HBox productPriceHbox =new HBox(productPriceLbl,productPrice);
        productPriceHbox.setSpacing(10);
        productPriceHbox.setAlignment(Pos.CENTER);

        addBtn =new Button("Add");

        this.getChildren().addAll(productIdHbox,productNameHbox,productPriceHbox,addBtn);
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
    }

    public TextField getProductId() {
        return productId;
    }

    public void setProductId(TextField productId) {
        this.productId = productId;
    }

    public TextField getProductName() {
        return productName;
    }

    public void setProductName(TextField productName) {
        this.productName = productName;
    }

    public TextField getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(TextField productPrice) {
        this.productPrice = productPrice;
    }

    public Button getAddBtn() {
        return addBtn;
    }

    public void setAddBtn(Button addBtn) {
        this.addBtn = addBtn;
    }
}
