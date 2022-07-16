package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Category;
import model.OrderItem;
import model.Status;
import model.Ticket;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class OrderDetailPageController implements Initializable {
    private final Stage thisStage;
    private int orderId;
    @FXML
    private TableView<OrderItem> orderAllItemsTable;
    @FXML
    private Button setOrderStatusBtn;
    @FXML
    private ComboBox orderStatusCombo;
    ArrayList<Status> statusArrayList;
    private MainPageController mainPageController;



    public OrderDetailPageController(int orderId,MainPageController mainPageController) {
        this.mainPageController = mainPageController;
        this.orderId = orderId;
        thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/order_detail_page.fxml"));
        try {
            loader.setController(this);
            loader.load();
            thisStage.setScene(new Scene((Parent) loader.getRoot()));
            thisStage.setTitle("Order Detail");


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        setOrderStatusBtn.setOnAction(e->{
            try {
                DataBase.updateOrderStatus(orderId,statusArrayList.get(orderStatusCombo.getSelectionModel().getSelectedIndex()).getStatusId());
                mainPageController.setAllLastOrdersTable();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //-----is admin--------------
        if(Preferences.userNodeForPackage(OrderDetailPageController.class).getInt("isAdmin",0)==0){
            orderStatusCombo.setVisible(false);
            setOrderStatusBtn.setVisible(false);
        }

        //--------------------------

        TableColumn<OrderItem,String> orderItemProduct = new TableColumn<>("Product");
        TableColumn<OrderItem,Integer> orderItmeQuantity = new TableColumn<>("Quantity");
        orderItemProduct.setCellValueFactory(new PropertyValueFactory<>("orderProductName"));
        orderItmeQuantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        orderAllItemsTable.getColumns().addAll(orderItemProduct,orderItmeQuantity);

        try {
            ArrayList<OrderItem> orderItems  = DataBase.getAllOrderOrderItems(orderId);
            for(OrderItem orderItem:orderItems){
                orderAllItemsTable.getItems().add(orderItem);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //status combo
        ArrayList<String> statuses =new ArrayList<>();
        statusArrayList = new ArrayList<>();
        try {
            statusArrayList = DataBase.getAllStatuses();
            for(Status status:statusArrayList){
                statuses.add(status.getStatusName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        orderStatusCombo.setItems(FXCollections.observableArrayList(statuses));



    }

    public void show(){
        thisStage.show();
    }
}
