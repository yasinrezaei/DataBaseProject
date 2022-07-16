package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Chat;
import model.Ticket;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class TicketChatsPageController implements Initializable {
    @FXML
    private TextArea chatTextArea;
    @FXML
    private ListView chatsListView;
    @FXML
    private Button sendChatBtn;
    private final Stage thisStage;
    private Ticket ticket;

    public TicketChatsPageController(Ticket ticket) {
        this.ticket = ticket;
        thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/ticket_chats_page.fxml"));
        try {
            loader.setController(this);
            loader.load();
            thisStage.setScene(new Scene((Parent) loader.getRoot()));
            thisStage.setTitle("Chats");

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }




    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setChats();

        //
        sendChatBtn.setOnAction(e->{
            try {
                DataBase.createChat(chatTextArea.getText(),ticket.getTicketId(),Integer.valueOf(Preferences.userNodeForPackage(TicketChatsPageController.class).get("userId","0")));
                setChats();
                chatTextArea.setText("");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void setChats() {
        for(int i=0;i<chatsListView.getItems().size();i++){
            chatsListView.getItems().clear();
        }



        ArrayList<Chat> chats =new ArrayList<>();
        try {
            chats = DataBase.getTicketChats(ticket.getTicketId());
            for(Chat chat:chats){
                chatsListView.getItems().add(chat.getChatUserName()+" : "+chat.getChatText());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void show(){
        thisStage.show();
    }
}
