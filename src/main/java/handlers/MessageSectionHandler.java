package handlers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.SQLException;

public class MessageSectionHandler {

    @FXML
    private BorderPane MessagingSectionPane;

    @FXML
    private Label archive_btn;

    @FXML
    private Label inbox_btn;

    @FXML
    private Label new_messagesbtn;
    private int admin_id;

    @FXML
    void archive_section(MouseEvent event) throws IOException {

        BorderPane view1 = FXMLLoader.load(getClass().getResource("/AdminFXMLPages/Archive.fxml"));
        MessagingSectionPane.setCenter(view1);

    }

    @FXML
    void inbox_section(MouseEvent event) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminFXMLPages/Inbox.fxml"));
        BorderPane view1 = loader.load();
        InboxHandler inboxHandler = loader.getController();
        inboxHandler.setAdmin_id(admin_id);
        inboxHandler.setMessages();
        MessagingSectionPane.setCenter(view1);

    }

    @FXML
    void nav_new_message(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminFXMLPages/SendMessage.fxml"));
        BorderPane view1 = loader.load();
        SendMessageHandle msg = loader.getController();
        msg.setEmployee_id(admin_id);
        MessagingSectionPane.setCenter(view1);

    }

    public void set_admin_id(int adminid) {

        this.admin_id = adminid;

    }
}
