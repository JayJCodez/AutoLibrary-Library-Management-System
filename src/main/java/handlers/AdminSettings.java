package handlers;

import java.io.IOException;

import Model.AdminUserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class AdminSettings {

    @FXML
    private Label personalDetailsButton;

    @FXML
    private BorderPane view;

    String admin_ID;

    @FXML
    void personaldetailsClicked(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/BackupAdmin/PersonalDetailsAdmin.fxml"));
        AnchorPane settingsDisplay = loader.load();
        view.setCenter(settingsDisplay);

        PersonalDetails details = loader.getController();
        details.setAdminID(admin_ID);
    }

       public void setAdminID(String adminID){

        AdminUserModel.getInstance().setAdminID(adminID);
        admin_ID = AdminUserModel.getInstance().getAdminID();
       
    }
}
