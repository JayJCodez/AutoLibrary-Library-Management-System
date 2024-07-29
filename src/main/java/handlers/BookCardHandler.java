package handlers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import Model.BookAdverts;

public class BookCardHandler {

    @FXML
    private Label AuthorName;

    @FXML
    private ImageView BookImage;

    @FXML
    private Label BookName;

    @FXML
    private AnchorPane cardLayout;

    

    public void setData(BookAdverts book){

        Image image = new Image(getClass().getResourceAsStream(book.getImageSrc()));
        BookImage.setImage(image);
        BookName.setText(book.getName());
        AuthorName.setText(book.getAuthor());   


    }

}
