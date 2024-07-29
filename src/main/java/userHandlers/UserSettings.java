package userHandlers;

import java.io.IOException;
import Model.UserContextModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class UserSettings {

    @FXML
    private Label personalDetailsButton;

    @FXML
    private BorderPane view;

    @FXML
    void personaldetailsClicked(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/userFXMLpages/UserPersonalDetails.fxml"));
        AnchorPane settingsDisplay = loader.load();
        view.setCenter(settingsDisplay);

        UserPersonalDetails user = loader.getController();
        user.setUsername(UserContextModel.getInstance().getUsername());



        // PersonalDetails details = loader.getController();
        // details.setAdminID(admin_ID);
    }

    public void setUsername(String username){

        UserContextModel.getInstance().setUsername(username);

    }

}
