package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Cart;
import model.Comment;
import model.OrderItem;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class ProductDetailPageController implements Initializable {
    @FXML
    private Label productNameLbl;
    @FXML
    private Label productPriceLbl;
    @FXML
    private Label productCategoryLbl;

    @FXML
    private TableView<Product> allSuggestedProductsTable;

    @FXML
    private TableView<Comment> allProductCommentsTable;

    @FXML
    private Button addCommentBtn;

    @FXML
    private Label addCommentLbl;

    @FXML
    private Button backBtn;



    private int productId;
    private Product product;
    private final Stage thisStage;
    public ProductDetailPageController(Product product) {
        this.product= product;
        thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/product_detail_page.fxml"));
        try {
            loader.setController(this);
            loader.load();
            thisStage.setTitle("Gigi Kala");
            thisStage.setScene(new Scene((Parent) loader.getRoot()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //product detail labels
        productNameLbl.setText("Name: "+product.getProductName());
        productCategoryLbl.setText("Category: "+product.getCategoryName());
        productPriceLbl.setText("Price: "+product.getProductPrice()+"$");



        //allSuggestedProductsTable
        TableColumn<Product,String> productNameCol = new TableColumn<>("Name");
        TableColumn<Product,Integer> productPriceCol = new TableColumn<>("Price");

        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

        allSuggestedProductsTable.getColumns().addAll(productNameCol,productPriceCol);
        try {
            ArrayList<Product> allSuggestedProducts =DataBase.getSuggestedProducts(product.getProductPrice(),product.getCategoryId());
            for(Product product1:allSuggestedProducts){
                if(product1.getProductId()!=product.getProductId()){
                    allSuggestedProductsTable.getItems().add(product1);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //allCommentsTable
        TableColumn<Comment,String> commentTextCol = new TableColumn<>("Text");
        TableColumn<Comment,String> commentUserCol = new TableColumn<>("User");

        commentTextCol.setCellValueFactory(new PropertyValueFactory<>("commentText"));
        commentUserCol.setCellValueFactory(new PropertyValueFactory<>("commentUserName"));

        allProductCommentsTable.getColumns().addAll(commentTextCol,commentUserCol);
        try {
            ArrayList<Comment> allProductComments =DataBase.getProductComments(product.getProductId());
            for(Comment comment:allProductComments){
                allProductCommentsTable.getItems().add(comment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        //addCommentBtn
        addCommentBtn.setOnAction(e->{
            try {
                boolean canComment = false;
                ArrayList<OrderItem> allLasetOrders = DataBase.getAllFinishedOrderItems(Integer.valueOf(Preferences.userNodeForPackage(MainPageController.class).get("userId","0")));
                for(OrderItem orderItem:allLasetOrders){
                    if(orderItem.getProductId()==product.getProductId()){
                        canComment= true;
                        break;
                    }
                }
                if(canComment){
                    TextInputDialog dialog = new TextInputDialog("");
                    dialog.setTitle("Comment");
                    dialog.setContentText("Please enter your comment :");

                    // Traditional way to get the response value.
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){

                        Comment comment =new Comment(result.get(),Integer.valueOf(Preferences.userNodeForPackage(MainPageController.class).get("userId","0")),product.getProductId());
                        if(DataBase.createComment(comment)!=-1){
                            for ( int i = 0; i<allProductCommentsTable.getItems().size(); i++) {
                                allProductCommentsTable.getItems().clear();
                            }
                            ArrayList<Comment> allProductComments =DataBase.getProductComments(product.getProductId());
                            for(Comment comment1:allProductComments){
                                allProductCommentsTable.getItems().add(comment1);
                            }

                        }
                    }
                }else {
                    addCommentLbl.setText("You cant comment on this product");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });

        //backBtn
        backBtn.setOnAction(e->{
            MainPageController mainPageController =new MainPageController();
            mainPageController.show();
            backBtn.getScene().getWindow().hide();
        });


    }
    public void show(){
        thisStage.show();
    }
}
