package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Profile;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditProfilePageController implements Initializable {
    @FXML
    private TextField userNameTf,userLastnameTf,userPhoneNumberTf;
    @FXML
    private Button editProfileBtn;
    private Profile profile;
    private final Stage thisStage;
    private MainPageController mainPageController;
    public EditProfilePageController(Profile profile,MainPageController mainPageController) {
        this.profile = profile;
        thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/edit_profile_page.fxml"));
        try {
            loader.setController(this);
            loader.load();
            thisStage.setScene(new Scene((Parent) loader.getRoot()));
            thisStage.setTitle("Edit profile");

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        editProfileBtn.setOnAction(e->{
            Profile newProfile = new Profile(profile.getProfileId(),profile.getUserId(),userNameTf.getText(),userLastnameTf.getText(),userPhoneNumberTf.getText());
            try {
                DataBase.updateProfile(newProfile);
                mainPageController.getFullNameLbl().setText(newProfile.getName()+" "+newProfile.getLastName());
                mainPageController.getPhoneNumberLbl().setText(newProfile.getPhoneNumber());
                editProfileBtn.getScene().getWindow().hide();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userNameTf.setText(profile.getName());
        userLastnameTf.setText(profile.getLastName());
        userPhoneNumberTf.setText(profile.getPhoneNumber());

        //



    }

    public void show(){
        thisStage.show();
    }
}
