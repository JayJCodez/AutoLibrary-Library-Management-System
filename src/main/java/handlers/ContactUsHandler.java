package handlers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class ContactUsHandler implements Initializable{

    @FXML
    private TextArea messageArea;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    
        messageArea.setWrapText(true);
    }

}
