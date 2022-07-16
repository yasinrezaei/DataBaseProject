package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import jdk.jfr.internal.tool.Main;
import model.*;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class CreateOrderPageController implements Initializable {
    ArrayList<Address> addressArrayList;
    ArrayList<SendingMethod> sendingMethodArrayList;
    @FXML
    private Button setOrderBtn;
    @FXML
    private ComboBox addressCombo,sendingMethodCombo;
    private final Stage thisStage;
    private MainPageController mainPageController;

    public CreateOrderPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
        this.thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/create_order_page.fxml"));
        try {
            loader.setController(this);
            loader.load();
            thisStage.setScene(new Scene((Parent) loader.getRoot()));
            thisStage.setTitle("Create Order");

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        setOrderBtn.setOnAction(e->{
            Order newOrder = new Order(String.valueOf(java.time.LocalDate.now()),1,addressArrayList.get(addressCombo.getSelectionModel().getSelectedIndex()).getAddressId(),sendingMethodArrayList.get(sendingMethodCombo.getSelectionModel().getSelectedIndex()).getSendingMethodId(),Integer.valueOf(Preferences.userNodeForPackage(CreateOrderPageController.class).get("userId","0")),0);
            int orderId = -1;
            try {
                orderId = DataBase.createOrder(newOrder);
                for(int i=0;i<mainPageController.getAllCartsTable().getItems().size();i++){
                    DataBase.createOrderItem(new OrderItem(orderId,mainPageController.getAllCartsTable().getItems().get(i).getProductId(),mainPageController.getAllCartsTable().getItems().get(i).getProductQuantity()));
                }
                for(int i=0;i<mainPageController.getAllCartsTable().getItems().size();i++){
                    DataBase.deleteCart(mainPageController.getAllCartsTable().getItems().get(i).getCartId());

                }
                for(int i=0;i<mainPageController.getAllCartsTable().getItems().size();i++){
                    mainPageController.getAllCartsTable().getItems().clear();
                }
                mainPageController.setAllLastOrdersTable();
                setOrderBtn.getScene().getWindow().hide();


            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        //address comboBox
        ArrayList<String> addresses =new ArrayList<>();
        addressArrayList  =new ArrayList<>();

        try {
            addressArrayList = DataBase.getUserAllAddresses(Integer.valueOf(Preferences.userNodeForPackage(CreateOrderPageController.class).get("userId","0")));
            for(Address address:addressArrayList){
                addresses.add(address.getAddressText());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        addressCombo.setItems(FXCollections.observableArrayList(addresses));


        //sending method comboBox

        ArrayList<String> sendingMethods =new ArrayList<>();
        sendingMethodArrayList  =new ArrayList<>();

        try {
            sendingMethodArrayList = DataBase.getAllSendingMethods();
            for(SendingMethod sendingMethod:sendingMethodArrayList){
                sendingMethods.add(sendingMethod.getSendingMethodName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sendingMethodCombo.setItems(FXCollections.observableArrayList(sendingMethods));



    }
    public void show(){
        thisStage.show();
    }
}
