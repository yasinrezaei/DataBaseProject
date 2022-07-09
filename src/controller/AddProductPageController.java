package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import model.Product;
import view.AddProductPage;

public class AddProductPageController {
    private AddProductPage addProductPage;
    private MainPageController mainPageController;

    public AddProductPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
        addProductPage =new AddProductPage();

    /*
        //addBtn
        addProductPage.getAddBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int productId = Integer.valueOf(addProductPage.getProductId().getText());
                String productName = addProductPage.getProductName().getText();
                int productPrice = Integer.valueOf(addProductPage.getProductPrice().getText());
                Product newProduct =new Product(productId,productName,productPrice);
                mainPageController.addProductToTable(newProduct);

            }
        });

     */

    }

    public AddProductPage getAddProductPage() {
        return addProductPage;
    }
}
