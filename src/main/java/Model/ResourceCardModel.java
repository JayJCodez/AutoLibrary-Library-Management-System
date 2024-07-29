package Model;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * ResourceCardModel
 */
public class ResourceCardModel {


    Image img;

    String resource_name;

    String resource_type;

    Label savebtn;

    Label viewbtn;

    String resource_ext;
  
    int res_id;



    public ResourceCardModel(Image img, String resource_name, String resource_type, String resource_ext, int res_id, Label savebtn, Label viewbtn) {
        this.img = img;
        this.resource_name = resource_name;
        this.resource_type = resource_type;
        this.resource_ext = resource_ext;
        this.savebtn = savebtn;
        this.res_id = res_id;
        this.viewbtn = viewbtn;
    }


    public int getRes_id() {
        return res_id;
    }

    public void setRes_id(int res_id) {
        this.res_id = res_id;
    }

    public String getResource_ext() {
        return resource_ext;
    }

    public void setResource_ext(String resource_ext) {
        this.resource_ext = resource_ext;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public Label getSavebtn() {
        return savebtn;
    }

    public EventHandler<? super MouseEvent> setSavebtn(Label savebtn) {
        this.savebtn = savebtn;
        return null;
    }

    public Label getViewbtn() {
        return viewbtn;
    }

    public EventHandler<? super MouseEvent> setViewbtn(Label viewbtn) {
        this.viewbtn = viewbtn;
        return null;
    }

}