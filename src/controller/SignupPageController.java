package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignupPageController implements Initializable {
    @FXML
    private TextField username_field;
    @FXML private PasswordField password_field;
    @FXML private Button signup_button;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        signup_button.setOnAction(e->{
            int user_id =-1;
            if(checkTextFields()){
                try {
                    user_id =DataBase.createUser(username_field.getText(),password_field.getText(),0);
                    DataBase.createShoppingCart(user_id);
                } catch (SQLException ex) {
                    System.out.println("Error in user creation");
                    throw new RuntimeException(ex);
                }
                if(user_id!=-1){
                    try {
                        DataBase.createProfile(user_id);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/login_page.fxml"));
                    try {
                        loader.load();
                        Stage stage =new Stage();
                        stage.setScene(new Scene((Parent) loader.getRoot()));
                        stage.show();
                        signup_button.getScene().getWindow().hide();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });
    }

    private boolean checkTextFields() {
        if(username_field.getText().length()>0 && password_field.getText().length()>0){
            return true;
        }
        return false;
    }
}
