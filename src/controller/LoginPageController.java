package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Admin;
import view.LoginPage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;


public class LoginPageController implements Initializable {
    public static Admin admin =new Admin("yasin","12345");
    @FXML private TextField username_field;
    @FXML private PasswordField password_field;
    @FXML private Button login_button;
    @FXML private Button goto_signup_button;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        login_button.setOnAction(e->{
            try {
                int userId=DataBase.userAuth(username_field.getText(),password_field.getText());
                if(userId!=-1){
                    int userShoppingCartId = DataBase.getShoppingCartId(userId);
                    Preferences pref;
                    pref = Preferences.userNodeForPackage(LoginPageController.class);
                    pref.put("userId", String.valueOf(userId));
                    pref.put("userShoppingCartId",String.valueOf(userShoppingCartId));
                    MainPageController mainPageController =new MainPageController();
                    mainPageController.show();
                    login_button.getScene().getWindow().hide();


                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        goto_signup_button.setOnAction(e->{
            //signup page
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/signup_page.fxml"));
            try {
                loader.load();
                Stage stage =new Stage();
                stage.setScene(new Scene((Parent) loader.getRoot()));
                stage.show();
                login_button.getScene().getWindow().hide();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


        });
    }
}
