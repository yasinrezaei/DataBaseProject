package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDataBase {
    private static Connection connection;
    private static Statement statement;

    private TestDataBase(){}

    public static void makeConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/gigikala","yasin1","1190272131");
        statement = connection.createStatement();
    }
    public static void closeConnection() throws SQLException {
        if(connection!=null){
            statement.close();
            connection.close();
        }
    }
}
