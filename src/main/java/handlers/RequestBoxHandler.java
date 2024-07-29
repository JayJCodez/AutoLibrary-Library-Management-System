package handlers;

import Model.RequestBoxModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class RequestBoxHandler {

    @FXML
    private Label approvebtn;

    @FXML
    private Label denybtn;

    @FXML
    private ImageView img;

    @FXML
    private Label requesttime;

    @FXML
    private Label resourcename;

    @FXML
    private Label username;

    @FXML 
    private BorderPane border;

    public void setData(RequestBoxModel model){


        
        img.setImage(model.getImg());
        border.setCenter(img);
        username.setText(model.getUsername());
        resourcename.setText(model.getResourcename());
        requesttime.setText(model.getRequestTime());
        approvebtn.setOnMouseClicked(model.setApprovebtn(approvebtn));
        denybtn.setOnMouseClicked(model.setDenybtn(denybtn));

    }

}
