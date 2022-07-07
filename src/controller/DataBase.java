package controller;

import model.DbProduct;
import model.Product;

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
            products.add(new Product(rs.getInt("id"),rs.getString("product_name"),1200));
        }
        closeConnection();
        return products;
    }
}
