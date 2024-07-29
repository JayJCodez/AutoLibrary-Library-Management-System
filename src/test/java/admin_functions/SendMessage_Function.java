package admin_functions;

import Connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SendMessage_Function {

    public Connection conn;

    public SendMessage_Function() throws SQLException {

        this.conn = DBConnection.getInstance().connectingDB();

    }

    public int sendMessage(String message, int senderID, boolean senderIsAdmin, int recipient_id, boolean recipientIsAdmin, String subject) throws SQLException {
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

            System.out.println("Message sent successfully");
            return 3;
        } else {

            return 0;
        }

    }

}
