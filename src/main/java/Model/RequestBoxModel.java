package Model;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * RequestBoxModel
 */
public class RequestBoxModel {

    String username;

    String resourcename;

    String requestTime;

    Label approvebtn;

    Label denybtn;

    Image img;

    int resourceid;


    public RequestBoxModel(Image img, String username, String resourcename, String requestTime, Label approvebtn, Label denybtn, int resourceid) {
        this.username = username;
        this.resourcename = resourcename;
        this.requestTime = requestTime;
        this.approvebtn = approvebtn;
        this.denybtn = denybtn;
        this.img = img;
        this.resourceid = resourceid;
    }


    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }


    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public Label getApprovebtn() {
        return approvebtn;
    }

    public EventHandler<? super MouseEvent> setApprovebtn(Label approvebtn) {
        this.approvebtn = approvebtn;
        return null;
    }

    public Label getDenybtn() {
        return denybtn;
    }

    public EventHandler<? super MouseEvent> setDenybtn(Label denybtn) {
        this.denybtn = denybtn;
        return null;
    }


}