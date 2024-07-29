package admin_functions;

import Connection.DBConnection;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Return_Book_Function {

    public Connection conn;

    public Return_Book_Function() throws SQLException {

        this.conn = DBConnection.getInstance().connectingDB();

    }

    public boolean return_Book(String username, String bookId) {
        try {

            String Username = null;
            String BookID = null;

            Username = username;
            BookID = bookId;

            int BookIDInt = Integer.parseInt(BookID);

            if(Username == null || BookIDInt == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid username");
                alert.showAndWait();
                return false;
            }
            // String GetDetails = "SELECT * FROM USERTRANSACTIONS WHERE Username = '"+Username+"' AND BookID = '"+BookIDInt+"' ";

            String RETURN_BOOK = "UPDATE USERTRANSACTIONS SET Returned = 'True' WHERE Username = '"+Username+"' AND BookID = "+BookIDInt+"";

            PreparedStatement returnBook = conn.prepareStatement(RETURN_BOOK);
            int result = returnBook.executeUpdate();
            // PreparedStatement BookDetails = conn.prepareStatement(GetDetails);
            // ResultSet R = BookDetails.executeQuery();

            if(result == 0){

                System.out.println("Unable to return book");
            }else if(result == 1){


                System.out.println("Successfully Returned Book");



            }


        } catch (SQLException e) {
            System.err.println(e);
        }


        return true;
    }

}

