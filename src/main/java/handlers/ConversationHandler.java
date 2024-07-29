package handlers;

import Connection.DBConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConversationHandler implements Initializable {

    @FXML
    private ImageView status_img;

    @FXML
    private Label status_label;

    @FXML
    private ImageView attachments;

    @FXML
    private Label current_user_reply;

    @FXML
    private VBox from_conversation_conainer;

    @FXML
    private VBox from_conversation_conainer1;

    @FXML
    private Label from_txt;

    @FXML
    private Label from_user_reply;

    @FXML
    private VBox conversation_container;

    @FXML
    private TextField message_textfield;

    @FXML
    private Label send_message;

    @FXML
    private ScrollPane scrollpane;

    private Timestamp lastLoadedMessageTimestamp = Timestamp.valueOf(LocalDateTime.now());


    private int adminid;

    private final Connection conn;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    String current_user_name;

    private final Timer timer = new Timer();

    String recipient_name;

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    private int sender_id;

    public void setAdminid(int adminid) {
        this.adminid = adminid;
    }

    public String getCurrent_user_name() {
        return current_user_name;
    }

    public void setCurrent_user_name(String current_user_name) {
        this.current_user_name = current_user_name;
    }

    public String getRecipient_name() {
        return recipient_name;
    }

    public void setRecipient_name(String recipient_name) {
        this.recipient_name = recipient_name;
    }

    public ConversationHandler() throws SQLException {

        this.conn = DBConnection.getInstance().connectingDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        conversation_container.setSpacing(20);

        startDatabaseCheckThread();


    }


    public void stopDatabaseCheckThread() {
        timer.cancel();
    }

    private void startDatabaseCheckThread() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        // Code to check the database for new messages
                        checkStatus();
                        updateMessage();
                    } catch (SQLException e) {
                        e.printStackTrace(); // Handle or log the exception
                    }
                });
            }
        }, 0, 1000); // Check every second
    }



    public void FromMessageLayout(String msg_content, String user_name, String date) {
        // Load the image
        Image img = new Image("Icons/icons8-avatar-96.png");

        // Create the ImageView and set its image
        ImageView imageView = new ImageView(img);

        // Create a Label for the name
        Label nameLabel = new Label(user_name); // Example name
        nameLabel.setStyle("-fx-font-weight: bold;");

        // Create a VBox for the name and message content
        VBox nameAndMessageVBox = new VBox(5); // Spacing between elements
        nameAndMessageVBox.setAlignment(Pos.CENTER); // Center align its content

        // Create a Label for the message content
        Label messageLabel = new Label(msg_content); // Example message content
        messageLabel.setWrapText(true); // Allow text wrapping

        Label datelabel = new Label(date); // Example name
        datelabel.setStyle("-fx-font-weight: bold;");

        // Add the name and message content labels to the VBox
        nameAndMessageVBox.getChildren().addAll(nameLabel, messageLabel, datelabel);

        // Create an HBox to hold the ImageView and the VBox
        HBox conversationLayout = new HBox(10); // Spacing between elements
        conversationLayout.setAlignment(Pos.CENTER_LEFT); // Center align its content

        // Add the ImageView and VBox to the HBox
        conversationLayout.getChildren().addAll(imageView, nameAndMessageVBox);

        // Add the HBox to the conversation container
        conversation_container.getChildren().add(conversationLayout);

        // Set padding for the container
        conversation_container.setPadding(new Insets(10));
    }

    public void ToMessageLayout(String msg_content, String user_name, String date) {
        // Load the image
        Image img = new Image("Icons/icons8-avatar-96.png");

        // Create the ImageView and set its image
        ImageView imageView = new ImageView(img);

        // Create a Label for the name
        Label nameLabel = new Label(user_name); // Example name
        nameLabel.setStyle("-fx-font-weight: bold;");

        // Create a VBox for the name and message content
        VBox nameAndMessageVBox = new VBox(5); // Spacing between elements
        nameAndMessageVBox.setAlignment(Pos.CENTER_RIGHT); // Align content to the right

        // Create a Label for the message content
        Label messageLabel = new Label(msg_content); // Example message content
        messageLabel.setWrapText(true); // Allow text wrapping

        Label datelabel = new Label(date); // Example name
        datelabel.setStyle("-fx-font-weight: bold;");

        // Add the name and message content labels to the VBox
        nameAndMessageVBox.getChildren().addAll(nameLabel, messageLabel, datelabel);

        // Create an HBox to hold the ImageView and the VBox
        HBox conversationLayout = new HBox(10); // Spacing between elements
        conversationLayout.setAlignment(Pos.BASELINE_RIGHT); // Align content to the bottom right

        // Add the ImageView and VBox to the HBox
        conversationLayout.getChildren().addAll(nameAndMessageVBox, imageView);

        // Set HBox to expand horizontally
        HBox.setHgrow(conversationLayout, Priority.ALWAYS);

        // Add the HBox to the conversation container
        conversation_container.getChildren().add(conversationLayout);
    }

    public void updateMessage() throws SQLException {

        String query = "SELECT * FROM LibraryMail WHERE ((Recipient_id = ? AND Recipient_admin = 1 AND Sender_id = ?) OR (Sender_id = ? AND  Recipient_id = ?)) AND SentDate > ? ORDER BY SentDate ASC";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, adminid);
        preparedStatement.setInt(2, sender_id);
        preparedStatement.setInt(3, adminid);
        preparedStatement.setInt(4, sender_id);
        preparedStatement.setTimestamp(5, lastLoadedMessageTimestamp);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            if (resultSet.getInt("Sender_id") != adminid) {
                FromMessageLayout(resultSet.getString("Body"), recipient_name, resultSet.getString("SentDate"));
            } else {
                ToMessageLayout(resultSet.getString("Body"), "YOU", resultSet.getString("SentDate"));
            }

            // Update last loaded message timestamp
            lastLoadedMessageTimestamp = resultSet.getTimestamp("SentDate");
        }

        // Close resources
        resultSet.close();
        preparedStatement.close();
    }


    public void retrieveAdminConversation() throws SQLException {


        String query = "SELECT * FROM LibraryMail WHERE (Recipient_id = "+adminid+" AND Recipient_admin = 1 AND Sender_id = "+sender_id+") OR (Sender_id = "+adminid+" AND  Recipient_id = "+sender_id+") ORDER BY SentDate ASC";

        PreparedStatement preparedStatement = conn.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            if(resultSet.getInt("Sender_id") != adminid){

                FromMessageLayout(resultSet.getString("Body"), recipient_name, resultSet.getString("SentDate"));

            }else if(resultSet.getInt("Sender_id") == adminid){

                ToMessageLayout(resultSet.getString("Body"), "YOU", resultSet.getString("SentDate"));

            }

        }

        // Scroll to the bottom
        scrollToBottom(scrollpane);



    }


    @FXML
    void send_reply(MouseEvent event) throws SQLException {

        String query = "INSERT INTO LibraryMail (Sender_id ,Sender_admin ,Recipient_id, Recipient_admin ,Subject, Body, SentDate, ReceivedDate, IsRead) VALUES(?, ?, ?, ?, ?, ?, GETDATE(), NULL, 0)";

        PreparedStatement ps = conn.prepareStatement(query);

        ps.setInt(1, adminid);
        ps.setBoolean(2, true);
        ps.setInt(3, sender_id);
        ps.setBoolean(4, true);
        ps.setString(5, null);
        ps.setString(6, message_textfield.getText());

        int db_response = ps.executeUpdate();

        if(db_response > 0){
            ToMessageLayout(message_textfield.getText(), "YOU", String.valueOf(Timestamp.from(Instant.now())));
            message_textfield.clear();
            scrollToBottom(scrollpane);
        }


    }

    public void scrollToBottom(ScrollPane scrollPane) {
        scrollPane.applyCss();
        scrollPane.layout();
        scrollPane.setVvalue(1.0);
    }

    public void checkStatus() {
        try {
            String status_query = "SELECT status FROM ADMINUSERS WHERE Employee_ID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(status_query);
            preparedStatement.setInt(1, sender_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int status = resultSet.getInt("status");
                Platform.runLater(() -> {
                    if(status == 1) {
                        status_label.setText("Online");
                        status_img.setImage(new Image("Icons/icons8-green-dot-48.png"));
                    } else {
                        status_label.setText("Offline");
                        status_img.setImage(new Image("Icons/icons8-red-dot-48.png"));
                    }
                });
            }
            resultSet.close(); // Close the result set
            preparedStatement.close(); // Close the prepared statement
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception
        }
    }




}
