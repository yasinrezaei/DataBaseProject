package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Admin;
import view.LoginPage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginPageController implements Initializable {
    public static Admin admin =new Admin("yasin","12345");
    @FXML private TextField username_field;
    @FXML private PasswordField password_field;
    @FXML private Button login_button;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        login_button.setOnAction(e->{
            if(admin.getUsername().equals(username_field.getText()) && admin.getPassword().equals(password_field.getText())){
                MainPageController mainPageController = null;
                try {
                    mainPageController = new MainPageController();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                Stage mainPageStage =new Stage();
                mainPageStage.setScene(new Scene(mainPageController.getMainPage()));
                mainPageStage.setHeight(600);
                mainPageStage.setWidth(400);
                mainPageStage.show();
                login_button.getScene().getWindow().hide();
            }
        });
    }
}
