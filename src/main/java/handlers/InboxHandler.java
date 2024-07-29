package handlers;

import Connection.DBConnection;
import Model.MailModel;
import Model.ResourceCardModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class InboxHandler implements Initializable {

    @FXML
    private Label all_mail;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private Label starred_mail;

    @FXML
    private Label unread_mail;

    private int admin_id;

    private String sender_full_name;

    private ObservableList<MailModel> mail = FXCollections.observableArrayList();

    private Connection conn;

    public InboxHandler() throws SQLException {

        this.conn = DBConnection.getInstance().connectingDB();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        try {
////            setMessages();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void setAdmin_id(int admin_id) {

        this.admin_id = admin_id;

    }

    public void setMessages() throws SQLException {


        listAdminMessages().clear();
        // Clear the grid before adding new items
//        grid.getChildren().clear();

        mail = listAdminMessages();


        int column = 0;
        int row = 1;


        try {


            for(MailModel res : mail){

                FXMLLoader fxmlLoader1 = new FXMLLoader();
                fxmlLoader1.setLocation(getClass().getResource("/AdminFXMLPages/MessagePreview.fxml"));

                BorderPane ResourceContainer = fxmlLoader1.load();
                MessagePreviewHandler handler = fxmlLoader1.getController();
                handler.setData(res);

                while (column == 0) {
                    column++;
                }

                if (column == 2) {

                    column = 0;
                    ++row;
                    ++column;

                }

                // Retrieve the label from the book and set event handler
                Label label = res.getView();
                label.setOnMouseClicked((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        // Handle label click event here

                        Stage newStage = new Stage();
                        FXMLLoader fxmlLoader2 = new FXMLLoader();
                        fxmlLoader2.setLocation(getClass().getResource("/AdminFXMLPages/Conversations.fxml"));
                        try {
                            Parent root = fxmlLoader2.load();

                            ConversationHandler convo = fxmlLoader2.getController();
                            convo.setAdminid(admin_id);
                            convo.setSender_id(res.getSender_id());
                            convo.setRecipient_name(res.getFrom());
                            convo.retrieveAdminConversation();

                            newStage.setTitle("Conversation Details");
                            newStage.setScene(new Scene(root, 751, 622));
                            newStage.show();
                        } catch (IOException | SQLException e) {
                            throw new RuntimeException(e);
                        }


                    }
                });



                Label reply = res.getReply();
                reply.setOnMouseClicked((EventHandler<? super MouseEvent>)new EventHandler<Event>() {

                    @Override
                    public void handle(Event arg0) {



                    }

                });
                // if (column > 3) {

                //     column = 0;
                //     ++row;

                // }


                // Button btn = new Button();
                grid.add(ResourceContainer, column++, row);
                // BookContainer.add(btn, column, row);
                // BookContainer.applyCss(ger);
                // GridPane.setMargin(BookInfoContainer, new Insets(10));
                // BookContainer.setStyle("-fx-spacing: 20px;");
                grid.setVgap(25);

//                grid.setHgap(50);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public ObservableList<MailModel> listAdminMessages() throws SQLException {
        String query = "SELECT A.First_Name, A.Last_Name,L.Sender_id, L.Recipient_id, L.Subject, L.Body, L.SentDate  FROM LibraryMail L JOIN ADMINUSERS A ON L.Recipient_id = A.Employee_ID WHERE L.Recipient_admin = 1 AND L.Recipient_id = "+admin_id+" ORDER BY L.SentDate DESC";

        String getReccipientDetails = "SELECT * FROM ADMINUSERS WHERE Employee_ID =?";


        PreparedStatement ps = conn.prepareStatement(query);
        PreparedStatement ps2 = conn.prepareStatement(getReccipientDetails);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Label view = new Label();
            Label reply = new Label();

            // Convert Recipient_id to String using String.valueOf()
            String recipientId = String.valueOf(rs.getInt("Sender_id"));


            ps2.setString(1, recipientId);

            ResultSet rs2 = ps2.executeQuery();

            String first_name = "";
            String last_name = "";


            if (rs2.next()) {

                 first_name = rs2.getString("First_Name");
                 last_name = rs2.getString("Last_Name");

                 sender_full_name = first_name + " " + last_name + " " + "ADMIN";

            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = dateFormat.format(rs.getTimestamp("SentDate"));

            mail.add(new MailModel(
                    rs.getInt("Sender_id"),
                sender_full_name,
                    null,
                    rs.getString("Subject"),
                    rs.getString("Body"),
                    view,
                    reply,
                    date
            ));
        }

        return mail;
    }

}
