package handlers;

import Model.ResourceCardModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ResourceCardHandler {

    @FXML
    private ImageView img;

    @FXML
    private Label resource_name;

    @FXML
    private Label resource_type;

    @FXML
    private Label saveBTN;

    @FXML
    private Label viewBTN;


    public void setData(ResourceCardModel model){

        img.setImage(model.getImg());
        resource_name.setText(model.getResource_name());
        resource_type.setText(model.getResource_type());
        saveBTN.setOnMouseClicked(model.setSavebtn(saveBTN));
        viewBTN.setOnMouseClicked(model.setViewbtn(viewBTN));

    }
}
