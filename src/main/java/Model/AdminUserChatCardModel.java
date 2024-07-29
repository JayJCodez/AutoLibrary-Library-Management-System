package Model;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class AdminUserChatCardModel {
    
    String id;

    String First_Name;

    String Last_Name;

    Label message;

  

    public AdminUserChatCardModel(String id, String first_Name, String last_Name, Label message) {
        this.id = id;
        First_Name = first_Name;
        Last_Name = last_Name;
        this.message = message;
    }

    public Label getMessage() { 
        return message;
    }

    public EventHandler<? super MouseEvent> setMessage(Label message) {
        this.message = message;
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }


}
