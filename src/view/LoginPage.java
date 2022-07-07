package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginPage extends VBox {
    private TextField usernameField;
    private PasswordField passwordField;
    private Label errorLbl;
    private Button loginBtn;

    public LoginPage() {
        Label usernameLbl =new Label("username :");
        usernameField = new TextField();
        HBox usernameHbox = new HBox(usernameLbl,usernameField);
        usernameHbox.setAlignment(Pos.CENTER);
        usernameHbox.setSpacing(5);

        Label passwordLbl =new Label("password :");
        passwordField = new PasswordField();
        HBox passwordHbox = new HBox(passwordLbl,passwordField);
        passwordHbox.setAlignment(Pos.CENTER);
        passwordHbox.setSpacing(5);


        errorLbl =new Label("");

        loginBtn = new Button("LogIn");

        this.getChildren().addAll(usernameHbox,passwordHbox,errorLbl,loginBtn);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(15);

    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(TextField usernameField) {
        this.usernameField = usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public Label getErrorLbl() {
        return errorLbl;
    }

    public void setErrorLbl(Label errorLbl) {
        this.errorLbl = errorLbl;
    }

    public Button getLoginBtn() {
        return loginBtn;
    }

    public void setLoginBtn(Button loginBtn) {
        this.loginBtn = loginBtn;
    }
}
