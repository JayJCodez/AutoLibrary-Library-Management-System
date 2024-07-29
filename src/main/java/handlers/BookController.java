package handlers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import Model.Book;

public class BookController {

    @FXML
    private Label AuthorName;

    @FXML
    private ImageView BookImage;

    @FXML
    private Label BookName;

    @FXML
    private Label viewBTN;

    @FXML
    private Label saveItem;

    public void setData (Book book) {

        Image image = book.getImageSrc();
        BookImage.setImage(image);
        BookName.setText(book.getName());
        AuthorName.setText(book.getAuthor());
        viewBTN.setOnMouseClicked(book.setViewBtn(viewBTN));
        saveItem.setOnMouseClicked(book.setSaveItem(saveItem));

    }
}