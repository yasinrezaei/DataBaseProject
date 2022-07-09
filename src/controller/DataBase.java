package controller;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    private static Connection connection;
    private static Statement statement;

    private DataBase(){}

    public static void makeConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gigikala","yasin1","1190272131");
        statement = connection.createStatement();
    }
    public static void closeConnection() throws SQLException {
        if(connection!=null){
            statement.close();
            connection.close();
        }
    }

    public static int createProduct(DbProduct product) throws SQLException {
        makeConnection();
        statement.execute(String.format("insert into products (product_name) values ('%s')",product.getProductName()),Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();//go to first row
        int id = rs.getInt(1);
        closeConnection();
        return id;
    }

    public static void deleteProduct(DbProduct product) throws SQLException {
        makeConnection();
        statement.execute(String.format("delete from products where id=%d",product.getId()));
        closeConnection();
    }
    public static ArrayList<Product> getAllProducts() throws SQLException {
        makeConnection();
        ResultSet rs = statement.executeQuery("select * from products");
        ArrayList<Product> products = new ArrayList<>();
        while (rs.next()){
            products.add(new Product(rs.getInt("product_id"),rs.getString("product_name"),rs.getInt("product_price"),rs.getInt("product_category")));
        }
        closeConnection();
        return products;
    }

    public static int createUser(String username,String password,int is_admin) throws SQLException {
        makeConnection();
        statement.execute(String.format("insert into user (username,password,is_admin) values ('%s','%s','%d')",username,password,is_admin),Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();//go to first row
        int user_id = rs.getInt(1);
        closeConnection();
        return user_id;

    }

    //shopping_cart
    public static int getShoppingCartId(int userId) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("select * from shoppingcarts where shoppingcart_user = ?");
        statement.setString(1, String.valueOf(userId));
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();//go to first row
        int shoppingCartId = resultSet.getInt("shoppingcart_id");
        closeConnection();
        return shoppingCartId;
    }

    public static ArrayList<Cart> getCarts(int shoppingCartId) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("select * from cartitems where shoppingcart_id = ?");
        statement.setString(1, String.valueOf(shoppingCartId));
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Cart> carts = new ArrayList<>();
        while (resultSet.next()){
            carts.add(new Cart(resultSet.getInt("cart_id"),resultSet.getInt("shoppingcart_id"),resultSet.getInt("product_id"),resultSet.getInt("product_quantity")));
        }
        closeConnection();
        return carts;
    }


    //last orders
    public static ArrayList<Order> getAllOrders(int userId) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("select * from orders where order_user = ?");
        statement.setString(1, String.valueOf(userId));
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Order> orders = new ArrayList<>();
        while (resultSet.next()){
            orders.add(new Order(resultSet.getInt("order_id"),resultSet.getString("order_date"),resultSet.getInt("order_status")));
        }
        closeConnection();
        return orders;
    }


    //status
    public static ArrayList<Status> getAllStatuses() throws SQLException {
        makeConnection();
        ResultSet rs = statement.executeQuery("select * from orderstatuses");
        ArrayList<Status> statuses = new ArrayList<>();
        while (rs.next()){
            statuses.add(new Status(rs.getInt("orderstatus_id"),rs.getString("orderstatus_name")));
        }
        closeConnection();
        return statuses;
    }

    public static String getStatusDetail(int id) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("select * from orderstatuses where orderstatus_id = ?");
        statement.setString(1, String.valueOf(id));
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();//go to first row
        String statusName = resultSet.getString("orderstatus_name");
        closeConnection();
        return statusName;
    }








    //caretgory
    public static ArrayList<Category> getAllCategories() throws SQLException {
        makeConnection();
        ResultSet rs = statement.executeQuery("select * from categories");
        ArrayList<Category> categories = new ArrayList<>();
        while (rs.next()){
            categories.add(new Category(rs.getInt("category_id"),rs.getString("category_name")));
        }
        closeConnection();
        return categories;
    }

    public static String getCategoryDetail(int id) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("select * from categories where category_id = ?");
        statement.setString(1, String.valueOf(id));
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();//go to first row
        String categoryName = resultSet.getString("category_name");
        closeConnection();
        return categoryName;
    }


}
