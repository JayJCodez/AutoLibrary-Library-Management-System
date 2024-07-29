package handlers;

import Model.ResourceCardModel;
import Model.ResourceSavedCardModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ResourceSavedCardHandler {


        @FXML
        private ImageView img;

        @FXML
        private Label resource_name;

        @FXML
        private Label resource_type;

        @FXML
        private Label viewBTN;

        @FXML
        private ImageView delete_book_icon;

        public void setData(ResourceSavedCardModel model){

            img.setImage(model.getImg());
            resource_name.setText(model.getResource_name());
            resource_type.setText(model.getResource_type());
            viewBTN.setOnMouseClicked(model.setViewbtn(viewBTN));
            delete_book_icon.setOnMouseClicked(model.setDeletebtn(delete_book_icon));

        }
    }


