package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import view.MainPage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class MainPageController implements Initializable {
    int userId=-1;
    Preferences pref;
    @FXML
    private Button createSupportBtn;
    @FXML
    private Label createTicketLbl;
    @FXML
    private TableView<Product> allProductsTable;
    @FXML
    private TableView<Cart> allCartsTable;
    @FXML
    private TableView<Order> allLastOrdersTable;
    @FXML
    private TableView<Address> allUserAddressesTable;

    @FXML
    private Label fullNameLbl;
    @FXML
    private Label phoneNumberLbl;

    @FXML
    private Button addToShoppingCartBtn;
    @FXML
    private Button addAddressBtn;

    @FXML
    private Button deleteCartBtn;

    @FXML
    private Button addProductBtn,addStatusBtn,addSendingMethodBtn;
    @FXML
    private Button editProductBtn;

    @FXML
    private Button setFilterBtn;
    @FXML
    private Button resetFilterBtn;

    private final Stage thisStage;

    @FXML
    private ComboBox filterComboBox;
    @FXML
    private Button showProductDetailBtn;
    @FXML
    private TableView<Ticket> allTicketsTable;
    @FXML
    private Button editUserProfileBtn;

    @FXML
    private Button orderDetailBtn,logOutBtn;
    @FXML
    private Button showTicketDetailBtn,createOrderBtn;
    @FXML
    private Tab shoppingCartTab,settingTab;

    @FXML
    private TableView<Status> allStatusesTable;
    @FXML
    private TextField settingDataTf;

    @FXML
    private TableView<SendingMethod> allSendingMethodsTable;
    ArrayList<Product> products = null;


    public MainPageController()  {
        thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/main_page.fxml"));
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
        //-----is admin--------------
        if(Preferences.userNodeForPackage(MainPageController.class).getInt("isAdmin",0)==0){
            addProductBtn.setVisible(false);
            editProductBtn.setVisible(false);
            settingTab.setDisable(true);
        }
        else{
            addToShoppingCartBtn.setVisible(false);
            showProductDetailBtn.setVisible(false);
            shoppingCartTab.setDisable(true);
            allUserAddressesTable.setVisible(false);
            addAddressBtn.setVisible(false);

        }

        //--------------------------




        pref = Preferences.userNodeForPackage(MainPageController.class);
        userId = Integer.valueOf(pref.get("userId","0"));

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
        addToShoppingCartBtn.setOnAction(e->{
            TextInputDialog dialog = new TextInputDialog("1");
            dialog.setTitle("Input");
            dialog.setContentText("Please enter quantity :");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){

                Cart cart =new Cart(Integer.valueOf(pref.get("userShoppingCartId","0")),allProductsTable.getSelectionModel().getSelectedItem().getProductId(),Integer.valueOf(result.get()));
                try {
                    int cartId=DataBase.addToShoppingCart(cart);
                    if(cartId!=-1){
                        cart.setCartId(cartId);
                        cart.setProductName(DataBase.getProductDetail(cart.getProductId()).getProductName());
                        allCartsTable.getItems().add(cart);

                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });

        //admin
        addProductBtn.setOnAction(e->{
            AddProductPageController addProductPageController =new AddProductPageController(true,this);
            addProductPageController.show();
        });
        editProductBtn.setOnAction(e->{
            AddProductPageController addProductPageController =new AddProductPageController(false,this);
            addProductPageController.show();
        });


        //filter combo box
        ArrayList<Category> categoryArrayList ;
        ArrayList<String> categoryList =new ArrayList<>();
        try {
            categoryArrayList = DataBase.getAllCategories();
            for(Category category:categoryArrayList){
                categoryList.add(category.getCategoryName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        filterComboBox.setItems(FXCollections.observableArrayList(categoryList));




        //filter

        setFilterBtn.setOnAction(e->{
            ArrayList<Product> filteredProducts =new ArrayList<>();
            for(Product product:products){
                if(product.getCategoryId() == categoryArrayList.get(filterComboBox.getSelectionModel().getSelectedIndex()).getId()){
                    filteredProducts.add(product);
                }
            }
            for ( int i = 0; i<allProductsTable.getItems().size(); i++) {
                allProductsTable.getItems().clear();
            }
            for(Product product:filteredProducts){
                allProductsTable.getItems().add(product);
            }
        });

        resetFilterBtn.setOnAction(e->{
            for ( int i = 0; i<allProductsTable.getItems().size(); i++) {
                allProductsTable.getItems().clear();
            }
            for(Product product:products){
                allProductsTable.getItems().add(product);
            }
        });

        showProductDetailBtn.setOnAction(e->{
            ProductDetailPageController productDetailPageController =new ProductDetailPageController(allProductsTable.getSelectionModel().getSelectedItem());
            productDetailPageController.show();
            showProductDetailBtn.getScene().getWindow().hide();
        });


        //tab-shoppingcart
        TableColumn<Cart,String> cartProductNameCol = new TableColumn<>("Name");
        TableColumn<Cart,Integer> cartProductPriceCol = new TableColumn<>("Quantity");

        cartProductNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        cartProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));


        allCartsTable.getColumns().addAll(cartProductNameCol,cartProductPriceCol);
        ArrayList<Cart> carts = null;
        ArrayList<Product> productArrayList = null;

        try {
            productArrayList = DataBase.getAllProducts();
            carts = DataBase.getCarts(userId);
            for (Cart cart : carts) {
                for(Product product:productArrayList){
                    if(product.getProductId()== cart.getProductId()){
                        cart.setProductName(product.getProductName());
                        break;
                    }
                }
                allCartsTable.getItems().add(cart);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        deleteCartBtn.setOnAction(e->{
            try {
                DataBase.deleteCart(allCartsTable.getSelectionModel().getSelectedItem().getCartId());
                allCartsTable.getItems().remove(allCartsTable.getSelectionModel().getSelectedItem());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });

        createOrderBtn.setOnAction(e->{

            CreateOrderPageController createOrderPageController =new CreateOrderPageController(this);
            createOrderPageController.show();

        });


        //logout
        logOutBtn.setOnAction(e->{
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/login_page.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            thisStage.setScene(new Scene((Parent) loader.getRoot()));
            thisStage.setWidth(600);
            thisStage.setHeight(400);
            thisStage.show();
            //logOutBtn.getScene().getWindow().hide();
        });










        //tab-lastorders
        TableColumn<Order,String> orderDateCol = new TableColumn<>("Date");
        TableColumn<Order,Integer> orderStatusCol = new TableColumn<>("Status");


        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        orderStatusCol.setCellValueFactory(new PropertyValueFactory<>("orderStatusName"));


        allLastOrdersTable.getColumns().addAll(orderDateCol,orderStatusCol);
        setAllLastOrdersTable();
        //create Ticket
        createSupportBtn.setOnAction(e->{
            try {
                int ticketId = DataBase.createTicket(allLastOrdersTable.getSelectionModel().getSelectedItem().getOrderId());
                if(ticketId!=-1){
                    createTicketLbl.setText("Ticket created successfully");
                    for(int i=0;i<allTicketsTable.getItems().size();i++){
                        allTicketsTable.getItems().clear();
                    }
                    setAllTicketsTableData();
                }
                else {
                    createTicketLbl.setText("Failed to create successfully!");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });





        //
        addAddressBtn.setOnAction(e->{
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Input");
            dialog.setContentText("Please enter address :");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    DataBase.createAddress(result.get(),Integer.valueOf(pref.get("userId","0")));
                    setUserAddressesTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //ticket tab
        TableColumn<Ticket,Integer> ticketIdCol = new TableColumn<>("ID");
        TableColumn<Ticket,String> ticketUserCol = new TableColumn<>("User");
        TableColumn<Ticket,Integer> ticketOrderIdCol = new TableColumn<>("Order");
        TableColumn<Ticket,String> ticketOrderDateCol = new TableColumn<>("Date");

        ticketIdCol.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        ticketUserCol.setCellValueFactory(new PropertyValueFactory<>("ticketUserName"));
        ticketOrderIdCol.setCellValueFactory(new PropertyValueFactory<>("ticketOrderId"));
        ticketOrderDateCol.setCellValueFactory(new PropertyValueFactory<>("ticketOrderDate"));


        allTicketsTable.getColumns().addAll(ticketIdCol,ticketUserCol,ticketOrderIdCol,ticketOrderDateCol);
        setAllTicketsTableData();

        showTicketDetailBtn.setOnAction(e->{
            TicketChatsPageController ticketChatsPageController =new TicketChatsPageController(allTicketsTable.getSelectionModel().getSelectedItem());
            ticketChatsPageController.show();
        });

        //order detail
        orderDetailBtn.setOnAction(e->{
            OrderDetailPageController orderDetailPageController = new OrderDetailPageController(allLastOrdersTable.getSelectionModel().getSelectedItem().getOrderId(),this);
            orderDetailPageController.show();
        });





        //tab-profile
        Profile profile;
        try {
            profile =setProfileDetail();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            setUserAddressesTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        editUserProfileBtn.setOnAction(e->{
            EditProfilePageController editProfilePageController =new EditProfilePageController(profile,this);
            editProfilePageController.show();
        });



        //tab setting
        TableColumn<Status,Integer> statusIdCol = new TableColumn<>("ID");
        TableColumn<Status,String> statusNameCol = new TableColumn<>("Name");
        statusIdCol.setCellValueFactory(new PropertyValueFactory<>("statusId"));
        statusNameCol.setCellValueFactory(new PropertyValueFactory<>("statusName"));
        allStatusesTable.getColumns().addAll(statusIdCol,statusNameCol);
        setAllStatusTableData();

        TableColumn<SendingMethod,Integer> sendingMethodIdCol = new TableColumn<>("ID");
        TableColumn<SendingMethod,String> sendingMethodNameCol = new TableColumn<>("Name");
        sendingMethodIdCol.setCellValueFactory(new PropertyValueFactory<>("sendingMethodId"));
        sendingMethodNameCol.setCellValueFactory(new PropertyValueFactory<>("sendingMethodName"));
        allSendingMethodsTable.getColumns().addAll(sendingMethodIdCol,sendingMethodNameCol);
        setAllSendingMethodsTableData();



        addStatusBtn.setOnAction(e->{
            try {
                DataBase.createStatus(settingDataTf.getText());
                setAllStatusTableData();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        addSendingMethodBtn.setOnAction(e->{
            try {
                DataBase.createSendingMethod(settingDataTf.getText());
                setAllSendingMethodsTableData();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });


    }

    private void setAllSendingMethodsTableData() {
        for(int i=0;i<allSendingMethodsTable.getItems().size();i++){
            allSendingMethodsTable.getItems().clear();
        }
        ArrayList<SendingMethod> sendingMethods = new ArrayList<>();
        try {
            sendingMethods = DataBase.getAllSendingMethods();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(SendingMethod sendingMethod:sendingMethods){
            allSendingMethodsTable.getItems().add(sendingMethod);
        }
    }

    private void setAllStatusTableData() {
        for(int i=0;i<allStatusesTable.getItems().size();i++){
            allStatusesTable.getItems().clear();
        }
        ArrayList<Status> statuses = new ArrayList<>();
        try {
            statuses = DataBase.getAllStatuses();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(Status status:statuses){
            allStatusesTable.getItems().add(status);
        }
    }

    public void setAllLastOrdersTable() {
        for(int i=0;i<allLastOrdersTable.getItems().size();i++){
            allLastOrdersTable.getItems().clear();

        }
        ArrayList<Order> orders = null;
        ArrayList<Status> statuses = null;
        try {
            statuses = DataBase.getAllStatuses();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        try {
            //-----is admin--------------
            if(Preferences.userNodeForPackage(MainPageController.class).getInt("isAdmin",0)==0){
                orders = DataBase.getAllOrders(userId);
            }
            else{
                orders = DataBase.getAllOrders();
            }





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

    private void setAllTicketsTableData() {
        ArrayList<Ticket> tickets = null;
        try {
            //-----is admin--------------
            if(Preferences.userNodeForPackage(MainPageController.class).getInt("isAdmin",0)==0){
                tickets = DataBase.getUserTickets(Integer.valueOf(pref.get("userId","0")));
            }
            else{
                tickets = DataBase.getAllTickets();
            }

            for (Ticket ticket : tickets) {
                allTicketsTable.getItems().add(ticket);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUserAddressesTable() throws SQLException {
        TableColumn<Address,String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("addressText"));
        allUserAddressesTable.getColumns().addAll(addressCol);
        ArrayList<Address> addresses = null;
        addresses = DataBase.getUserAllAddresses(userId);
        for(Address address:addresses){
            allUserAddressesTable.getItems().add(address);
        }

    }

    private Profile setProfileDetail() throws SQLException {
        Profile profile =DataBase.getProfile(userId);
        fullNameLbl.setText(profile.getName()+" "+profile.getLastName());
        phoneNumberLbl.setText(profile.getPhoneNumber());
        return profile;
    }



    public TableView<Product> getAllProductsTable() {
        return allProductsTable;
    }

    public void setAllProductsTable(TableView<Product> allProductsTable) {
        this.allProductsTable = allProductsTable;
    }
    public void show(){
        thisStage.show();
    }

    public Label getFullNameLbl() {
        return fullNameLbl;
    }

    public Label getPhoneNumberLbl() {
        return phoneNumberLbl;
    }

    public TableView<Cart> getAllCartsTable() {
        return allCartsTable;
    }

    public void setAllCartsTable(TableView<Cart> allCartsTable) {
        this.allCartsTable = allCartsTable;
    }

    public TableView<Order> getAllLastOrdersTable() {
        return allLastOrdersTable;
    }

    public void setAllLastOrdersTable(TableView<Order> allLastOrdersTable) {
        this.allLastOrdersTable = allLastOrdersTable;
    }
}
