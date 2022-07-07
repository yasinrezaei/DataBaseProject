package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Product;
import view.MainPage;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainPageController {
    private MainPage mainPage;

    public MainPageController() throws SQLException {
        mainPage =new MainPage();
        ArrayList<Product> products = DataBase.getAllProducts();
        for(Product product:products){
            mainPage.getProductTableView().getItems().add(product);
        }

        mainPage.getAddBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AddProductPageController addProductPageController =new AddProductPageController(MainPageController.this);
                Stage addProductStage =new Stage();
                addProductStage.setScene(new Scene(addProductPageController.getAddProductPage()));
                addProductStage.show();

            }
        });

        mainPage.getDeleteBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Product selectedProduct = mainPage.getProductTableView().getSelectionModel().getSelectedItem();
                if(selectedProduct!=null){
                    mainPage.getProductTableView().getItems().remove(selectedProduct);
                }
            }
        });

        mainPage.getExitBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });
    }

    public MainPage getMainPage() {
        return mainPage;
    }

    public void addProductToTable(Product product){
        mainPage.getProductTableView().getItems().add(product);
    }


}
