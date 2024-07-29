package handlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Connection.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ReturnBookHandler {

    @FXML
    private Label ErrorLabel;

    @FXML
    private TextField BookIDTxt;

    @FXML
    private Label BookNameDisplay;

    @FXML
    private Label ReturnBookbtn;

    @FXML
    private Label ReturnDateBox;

    @FXML
    private Label CollectionDateBox;

    @FXML
    private Label SearchBtn;

    @FXML
    private TextField UsernameTxt;

    private Connection conn;

    public ReturnBookHandler() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();
    }

    @FXML
    void FindBook(MouseEvent event) throws SQLException {

        String Username = UsernameTxt.getText();
        String BookID = BookIDTxt.getText();
        Integer BookIDInt = Integer.parseInt(BookID);

        if(Username == null || BookIDInt == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please complete the form");
            alert.showAndWait();
            return;
        }

        String FIND_BOOK = "SELECT * FROM LibraryBooks where BookID = '"+BookIDInt+"'";


        PreparedStatement pstmtFindBook = conn.prepareStatement(FIND_BOOK);
        ResultSet F = pstmtFindBook.executeQuery();

        if (F.next()) {

            String BookName = F.getString(2);
            BookNameDisplay.setText(BookName);

        }

        String GetDetails = "SELECT * FROM USERTRANSACTIONS WHERE Username = '"+Username+"' AND BookID = '"+BookIDInt+"' ";


        PreparedStatement BookDetails = conn.prepareStatement(GetDetails);
        ResultSet R = BookDetails.executeQuery();

        if (R.next()) {
            // SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
            java.sql.Date date = (R.getDate(5));
            java.sql.Date date1 = (R.getDate(6));
            // Date BorrowedDate = R.getDate(5);
            CollectionDateBox.setText("Date: " + date );
            ReturnDateBox.setText("Expected Date: " + date1);
            // long test = sdf.parse(date).getTimeInstance();
        }

    }

    @FXML
    void ReturnBook(MouseEvent event) throws SQLException {

        try {

            String Username = UsernameTxt.getText();
            String BookID = BookIDTxt.getText();
            int BookIDInt = Integer.parseInt(BookID);

            if(Username == null || BookIDInt == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid username");
                alert.showAndWait();
                return;
            }
            // String GetDetails = "SELECT * FROM USERTRANSACTIONS WHERE Username = '"+Username+"' AND BookID = '"+BookIDInt+"' ";

            String RETURN_BOOK = "UPDATE USERTRANSACTIONS SET Returned = 'True' WHERE Username = '"+Username+"' AND BookID = '"+BookIDInt+"'";

            PreparedStatement returnBook = conn.prepareStatement(RETURN_BOOK);
            returnBook.execute();
            // PreparedStatement BookDetails = conn.prepareStatement(GetDetails);
            // ResultSet R = BookDetails.executeQuery();


            ErrorLabel.setText(""+Username+" successfully returned book ");

        } catch (SQLException e) {
            System.err.println(e);
        }


    }

}
