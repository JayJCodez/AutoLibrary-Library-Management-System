package userHandlers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import Model.SavedBook;

public class SavedBookController {

    @FXML
    private Label AuthorName;

    @FXML
    private ImageView BookImage;

    @FXML
    private Label BookName;

    @FXML
    private Label view_btn;

    @FXML
    private Label saveItem;

    @FXML
    private ImageView delete_btn;

    public void setData (SavedBook book) {

        Image image = book.getImageSrc();
        BookImage.setImage(image);
        BookName.setText(book.getName());
        AuthorName.setText(book.getAuthor());
        delete_btn.setOnMouseClicked(book.setDeletebtn(delete_btn));
        view_btn.setOnMouseClicked(book.setView(view_btn));



    }


}