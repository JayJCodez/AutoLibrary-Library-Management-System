package handlers;

import Connection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class SendMessageHandle implements Initializable {

    @FXML
    private Label current_user_name;

    @FXML
    private TextArea message_content;

    @FXML
    private Label send_message_btn;

    @FXML
    private TextField subject_txt;

    @FXML
    private ComboBox<String> recipients_combobox;

    private Connection conn;

    private ObservableList<String> recipients = FXCollections.observableArrayList();

    private boolean admin = false;

    private int user_id;

    private int employee_id;

    public SendMessageHandle() throws SQLException {

        this.conn = DBConnection.getInstance().connectingDB();

    }

    @FXML
    void send_mail(MouseEvent event) throws SQLException {
        String recipient = recipients_combobox.getSelectionModel().getSelectedItem();
        String[] str = recipient.split(" ");
        int recipient_id = Integer.parseInt(str[0]);
        String recipient_is_admin = "";
        if (str.length > 3) {
            recipient_is_admin = str[3];
        }
        String subject = subject_txt.getText();
        String message = message_content.getText();

        if (!recipient.isEmpty() && subject != null && !subject.isEmpty() && message != null && !message.isEmpty()) {
            if (user_id != 0) { // Assuming user_id != 0 represents admin user
                if (!recipient_is_admin.isEmpty()) {
                    sendMessage(recipient_id, subject, message, true, false, employee_id, true);
                } else {
                    sendMessage(recipient_id, subject, message, false, false, employee_id, true);
                }
            } else if (employee_id != 0) { // Assuming employee_id != 0 represents regular user
                if (!recipient_is_admin.isEmpty()) {
                    sendMessage(recipient_id, subject, message, true, false, employee_id, true);
                } else {
                    sendMessage(recipient_id, subject, message, false, false, employee_id, true);
                }
            }
        } else {
            // Handle empty fields or other validation logic
            System.out.println("Please fill in all fields");
        }



    }


    public void sendMessage(int recipient_id, String subject, String message, boolean recipientIsAdmin, boolean isRead, int senderID, boolean senderIsAdmin) throws SQLException {

        String query = "INSERT INTO LibraryMail (Sender_id ,Sender_admin ,Recipient_id, Recipient_admin ,Subject, Body, SentDate, ReceivedDate, IsRead) VALUES(?, ?, ?, ?, ?, ?, GETDATE(), NULL, 0)";

        PreparedStatement pstmt = conn.prepareStatement(query);

        // Set parameters for the prepared statement
        pstmt.setInt(1, senderID);
        pstmt.setBoolean(2, senderIsAdmin);
        pstmt.setInt(3, recipient_id);
        pstmt.setBoolean(4, recipientIsAdmin);
        pstmt.setString(5, subject);
        pstmt.setString(6, message);



        // Execute the insert statement
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Message sent");
            alert.showAndWait();
        } else {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setContentText("Something went wrong");
           alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            recipients_combobox.setItems(setRecipients_combobox());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private ObservableList<String> setRecipients_combobox() throws SQLException {
        String getAdminRecipients = "SELECT Employee_ID, First_Name, Last_Name FROM ADMINUSERS";
        String getRecipients = "SELECT User_ID, First_Name, Last_Name FROM USERS";

        PreparedStatement ps = conn.prepareStatement(getAdminRecipients);
        PreparedStatement ps1 = conn.prepareStatement(getRecipients);

        ResultSet rs = ps.executeQuery();
        ResultSet rs1 = ps1.executeQuery();

        boolean isAdmin = false;

        while (rs.next()) {
            if (!isAdmin) {
                recipients.add("ADMIN USERS");
                isAdmin = true;
            }

            int emp_id = rs.getInt("Employee_ID");
            String admin_first_name = rs.getString("First_Name");
            String admin_last_name = rs.getString("First_Name");

            recipients.add(emp_id + " " + admin_first_name + " " + admin_last_name + " " + "ADMIN");
        }

        while (rs1.next()) {
            if (isAdmin) {
                recipients.add("NON-ADMIN USERS");
                isAdmin = false;
            }

            int user_id = rs1.getInt("User_ID");
            String first_name = rs1.getString("First_Name");
            String last_name = rs1.getString("Last_Name");

            recipients.add(user_id + " " + first_name + " " + last_name);
        }

        return recipients;
    }


    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
}
