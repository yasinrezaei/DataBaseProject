package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Product;

public class MainPage extends BorderPane {
    private TableView<Product> productTableView ;
    private Button addBtn,deleteBtn,exitBtn;
    public MainPage() {
        createNewElement();
        TableColumn<Product,Integer> productIdCol = new TableColumn<>("Id");
        TableColumn<Product,String> productNameCol = new TableColumn<>("Name");
        TableColumn<Product,Integer> productPriceCol = new TableColumn<>("Price");

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

        productTableView.getColumns().addAll(productIdCol,productNameCol,productPriceCol);

        VBox vBox = new VBox(addBtn,deleteBtn,exitBtn);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.BOTTOM_CENTER);


        productTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.setCenter(productTableView);
        this.setRight(vBox);


    }

    private void createNewElement(){
        productTableView = new TableView<>();
        addBtn = new Button("add product");
        addBtn.setMaxWidth(Double.MAX_VALUE);
        deleteBtn = new Button("delete product");
        deleteBtn.setMaxWidth(Double.MAX_VALUE);
        exitBtn = new Button("Exit");
        exitBtn.setMaxWidth(Double.MAX_VALUE);
    }


    public TableView<Product> getProductTableView() {
        return productTableView;
    }

    public void setProductTableView(TableView<Product> productTableView) {
        this.productTableView = productTableView;
    }

    public Button getAddBtn() {
        return addBtn;
    }

    public void setAddBtn(Button addBtn) {
        this.addBtn = addBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public void setDeleteBtn(Button deleteBtn) {
        this.deleteBtn = deleteBtn;
    }

    public Button getExitBtn() {
        return exitBtn;
    }

    public void setExitBtn(Button editBtn) {
        this.exitBtn = editBtn;
    }
}
