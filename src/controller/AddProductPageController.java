package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Category;
import model.Product;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddProductPageController implements Initializable {
    private final Stage thisStage;
    private ArrayList<Category> categoryArrayList ;
    private boolean isAdd=true;
    private MainPageController mainPageController;
    @FXML
    private TextField productNameTf;
    @FXML
    private TextField productPriceTf;
    @FXML
    private ComboBox categoryCombo;
    @FXML
    private Button addNewProductBtn;

    public AddProductPageController(boolean isAdd, MainPageController mainPageController) {
        this.isAdd = isAdd;
        this.thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/add_ptoduct_page.fxml"));
        try {
            loader.setController(this);
            loader.load();
            thisStage.setScene(new Scene((Parent) loader.getRoot()));
            thisStage.setTitle("Add new product");
            if(!isAdd){
                thisStage.setTitle("Edit product");
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        if(!isAdd){
            addNewProductBtn.setText("Edit");
            productNameTf.setText(mainPageController.getAllProductsTable().getSelectionModel().getSelectedItem().getProductName());
            productPriceTf.setText(String.valueOf(mainPageController.getAllProductsTable().getSelectionModel().getSelectedItem().getProductPrice()));
        }
        //add button
        addNewProductBtn.setOnAction(e->{
            if(isAdd){
                Product product =new Product(productNameTf.getText(),Integer.valueOf(productPriceTf.getText()),categoryArrayList.get(categoryCombo.getSelectionModel().getSelectedIndex()).getId());
                try {
                    int id = DataBase.createProduct(product);
                    product.setProductId(id);
                    product.setCategoryName(DataBase.getCategoryDetail(product.getCategoryId()));
                    mainPageController.getAllProductsTable().getItems().add(product);
                    addNewProductBtn.getScene().getWindow().hide();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else{
                Product product = new Product(mainPageController.getAllProductsTable().getSelectionModel().getSelectedItem().getProductId(),productNameTf.getText(),Integer.valueOf(productPriceTf.getText()),categoryArrayList.get(categoryCombo.getSelectionModel().getSelectedIndex()).getId());
                try {
                    DataBase.updateProduct(product);
                    product.setCategoryName(DataBase.getCategoryDetail(product.getCategoryId()));
                    mainPageController.getAllProductsTable().getItems().add(mainPageController.getAllProductsTable().getSelectionModel().getSelectedIndex(),product);
                    mainPageController.getAllProductsTable().getItems().remove(mainPageController.getAllProductsTable().getSelectionModel().getSelectedIndex());
                    addNewProductBtn.getScene().getWindow().hide();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });




    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> categories =new ArrayList<>();

        try {
            categoryArrayList = DataBase.getAllCategories();
            for(Category category:categoryArrayList){
                categories.add(category.getCategoryName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        categoryCombo.setItems(FXCollections.observableArrayList(categories));








    }
    public void show(){
        thisStage.show();
    }


}
