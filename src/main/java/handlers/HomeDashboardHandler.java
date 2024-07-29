package handlers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import Connection.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
public class HomeDashboardHandler implements Initializable {



    @FXML
    private VBox BooksAvailable;

    @FXML
    private Label BooksAvailableNo;

    @FXML
    private VBox BooksReturned;

    @FXML
    private Label BooksReturnedNo;

    @FXML
    private VBox UsersRegistered;

    @FXML
    private Label UsersRegisteredNo;

    Connection conn;

    //Instance of our connection being instantiated in this class.

    public HomeDashboardHandler() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }

    //Here I am initializing functions which provide this scene with live information from the SQL Database.

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String books_available = "SELECT COUNT(BookName) FROM LibraryBooks;";
        String BooksToBeReturned = "SELECT COUNT(TransactionID) FROM USERTRANSACTIONS WHERE Returned = 'False'";
        String users_registered = "SELECT COUNT(User_ID) FROM USERS;";

        try {


            PreparedStatement pstmt = conn.prepareStatement(books_available);
            PreparedStatement pstmtCheck = conn.prepareStatement(BooksToBeReturned);
            PreparedStatement userCount = conn.prepareStatement(users_registered);
            pstmt.executeQuery();
            pstmtCheck.executeQuery();
            userCount.executeQuery();

            ResultSet R = pstmt.executeQuery();
            ResultSet F = pstmtCheck.executeQuery();
            ResultSet U = userCount.executeQuery();


            if (R.next()) {
                Integer BooksAvailableValue = R.getInt(1);

                String BookAvailable = String.valueOf(BooksAvailableValue);
                BooksAvailableNo.setText(BookAvailable);

            }

            if (F.next()) {
                Integer BooksToReturnValue = F.getInt(1);

                String BookToReturn = String.valueOf(BooksToReturnValue);
                BooksReturnedNo.setText(BookToReturn);
            }

            if (U.next()) {

                int user_count = U.getInt(1);

                String user_count_s = Integer.toString(user_count);

                UsersRegisteredNo.setText(user_count_s);
            }





        } catch (Exception e) {
            System.err.println(e);
        }



    }
}
