package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import view.MainPage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    @FXML
    private TableView<Product> allProductsTable;
    @FXML
    private TableView<Cart> allCartsTable;
    @FXML
    private TableView<Order> allLastOrdersTable;

    public MainPageController() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // tab - products
        TableColumn<Product,Integer> productIdCol = new TableColumn<>("Id");
        TableColumn<Product,String> productNameCol = new TableColumn<>("Name");
        TableColumn<Product,Integer> productPriceCol = new TableColumn<>("Price");
        TableColumn<Product,String> productCategoryCol = new TableColumn<>("Category");

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        productCategoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        allProductsTable.getColumns().addAll(productIdCol,productNameCol,productPriceCol,productCategoryCol);
        ArrayList<Product> products = null;
        ArrayList<Category> categories =null;
        try {
            categories = DataBase.getAllCategories();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        try {
            products = DataBase.getAllProducts();
            for (Product product : products) {
                for(Category category:categories){
                    if(category.getId()==product.getCategoryId()){
                        product.setCategoryName(category.getCategoryName());
                        break;
                    }
                }
                allProductsTable.getItems().add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //tab-shoppingcart
        TableColumn<Cart,Integer> cartProductNameCol = new TableColumn<>("Name");
        TableColumn<Cart,Integer> cartProductPriceCol = new TableColumn<>("Quantity");

        cartProductNameCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        cartProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));


        allCartsTable.getColumns().addAll(cartProductNameCol,cartProductPriceCol);
        ArrayList<Cart> carts = null;
        try {
            carts = DataBase.getCarts(3);
            for (Cart cart : carts) {
                System.out.println(cart.getShoppingCartId());
                allCartsTable.getItems().add(cart);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //tab-lastorders
        TableColumn<Order,String> orderDateCol = new TableColumn<>("Date");
        TableColumn<Order,Integer> orderStatusCol = new TableColumn<>("Status");


        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        orderStatusCol.setCellValueFactory(new PropertyValueFactory<>("orderStatusName"));


        allLastOrdersTable.getColumns().addAll(orderDateCol,orderStatusCol);
        ArrayList<Order> orders = null;
        ArrayList<Status> statuses = null;
        try {
            statuses = DataBase.getAllStatuses();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        try {
            orders = DataBase.getAllOrders(3);
            for (Order order : orders) {
                for(Status status:statuses){
                    if(order.getOrderStatus()==status.getStatusId()){
                        order.setOrderStatusName(status.getStatusName());
                        break;
                    }
                }
                allLastOrdersTable.getItems().add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
    public void addProductToTable(Product product){

    }
}
