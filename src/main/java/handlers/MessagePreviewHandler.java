package handlers;

import Model.MailModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class MessagePreviewHandler {

    @FXML
    private Label from_txt;

    @FXML
    private BorderPane message_preview_container;

    @FXML
    private Label message_txt;

    @FXML
    private Label reply_btn;

    @FXML
    private Label subject_txt;

    @FXML
    private Label view_btn;

    @FXML
    private Label date_txt;



    public void setData(MailModel mailModel){

        from_txt.setText("From: " + mailModel.getFrom());
        message_txt.setText(mailModel.getBody());
        subject_txt.setText("Subject: " + mailModel.getSubject());
        view_btn.setOnMouseClicked(mailModel.setView(view_btn));
        reply_btn.setOnMouseClicked(mailModel.setReply(reply_btn));
        date_txt.setText(mailModel.getDate());

    }

}
