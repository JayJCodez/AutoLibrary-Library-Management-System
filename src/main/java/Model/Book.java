package Model;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class Book {
   
    private String Name;
    private Image ImageSrc;
    private String Author;
    private Label viewBtn;
    private Label saveItem;
    

  


    public Book(String name, Image imageSrc, String author, Label viewBtn) {
        Name = name;
        ImageSrc = imageSrc;
        Author = author;
        this.viewBtn = viewBtn;
     
    }

 
    public Label getViewBtn() {
        return viewBtn;
    }

    public EventHandler<? super MouseEvent> setViewBtn(Label viewBtn) {
        this.viewBtn = viewBtn;
        return null;
    }


    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public Image getImageSrc() {
        return ImageSrc;
    }
    public void setImageSrc(Image imageSrc) {
        ImageSrc = imageSrc;
    }
    public String getAuthor() {
        return Author;
    }
    public void setAuthor(String author) {
        Author = author;
    }

    public Label getSaveItem() {
        return saveItem;
    }


    public EventHandler<? super MouseEvent> setSaveItem(Label saveItem) {
        this.saveItem = saveItem;
        return null;
    }

    public Label getLabel() {
        return viewBtn;
    }

}
