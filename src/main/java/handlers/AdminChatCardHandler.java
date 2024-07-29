package handlers;

import Model.AdminUserChatCardModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminChatCardHandler {

    @FXML 
    private Label messageButton;

    @FXML
    private Label name;

    @FXML
    private Label online_status;

    @FXML
    private Label userid;


    public void setData(AdminUserChatCardModel model){


        name.setText(model.getFirst_Name() + " " + model.getLast_Name());
        userid.setText(model.getId());
        messageButton.setOnMouseClicked(model.setMessage(messageButton));


    }

}
