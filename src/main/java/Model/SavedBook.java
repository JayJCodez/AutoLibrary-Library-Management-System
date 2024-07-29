package Model;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SavedBook {
    private String Name;
    private Image ImageSrc;
    private String Author;
    // private Label viewBtn;
    // private Label saveItem;
    private ImageView deletebtn;
    private Label view;
    private String id;
    

  


    public SavedBook(String id, String name, Image imageSrc, String author, ImageView deletebtn) {
        Name = name;
        ImageSrc = imageSrc;
        Author = author;
        this.deletebtn = deletebtn;
        this.id = id;
        
     
    }


    public Label getView() {
        return view;
    }

    public EventHandler<? super MouseEvent> setView(Label view) {
        this.view = view;
        return null;
    }
 
    // public Label getViewBtn() {
    //     return viewBtn;
    // }

    // public EventHandler<? super MouseEvent> setViewBtn(Label viewBtn) {
    //     this.viewBtn = viewBtn;
    //     return null;
    // }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public EventHandler<? super MouseEvent> setDeletebtn(ImageView img) {
        this.deletebtn = img;
        return null;
    }

    public ImageView getDeletebtn() {

        return deletebtn;
    }

    // public Label getSaveItem() {
    //     return saveItem;
    // }


    // public EventHandler<? super MouseEvent> setSaveItem(Label saveItem) {
    //     this.saveItem = saveItem;
    //     return null;
    // }

    // public Label getLabel() {
    //     return viewBtn;
    // }
}
