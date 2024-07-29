package admin_functions;

import javafx.scene.control.Alert;
import Connection.DBConnection;
import java.sql.*;
import java.time.Instant;

public class Issue_Book_Function {

    private Connection conn;

    public Issue_Book_Function() throws SQLException {
        this.conn = DBConnection.getInstance().connectingDB();
    }

    public int issue_Book_Function(String bookid, String username) {

    try {
        String BookID = bookid;
        int BookIDInt = Integer.parseInt(BookID);
        String Username = username;



        if(BookIDInt == 0 || Username == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please complete the form");
            alert.showAndWait();
            return BookIDInt;
        }


        // Timestamp returnTimestamp = Timestamp.valueOf(ReturnDate.atStartOfDay());

        // Timestamp.valueOf(CollectionDate.atStartOfDay());


        // Timestamp collection_date = Timestamp.valueOf(CollectionDate.toLocalDa)




        String ISSUEBOOK = "insert into USERTRANSACTIONS (BookID, BookName, Username, Collection_Date, Return_Date) values  (?, ?, ?, ?, ?)";
        String CHECK_BOOK_AVAILABILITY = "SELECT * FROM USERTRANSACTIONS where BookID = '"+BookIDInt+"';";
        String BookName = "SELECT * FROM LibraryBooks WHERE BookID = "+BookID+";";
        PreparedStatement pstmt = conn.prepareStatement(ISSUEBOOK);
        PreparedStatement pstmtCheckAvailability = conn.prepareStatement(CHECK_BOOK_AVAILABILITY);
        PreparedStatement book_name = conn.prepareStatement(BookName);






        ResultSet Q = book_name.executeQuery();

        ResultSet R = pstmtCheckAvailability.executeQuery();

        if (R.next()) {
            System.out.println("Sorry....this book is currently unavailable");
        }else{

            pstmt.setInt(1, BookIDInt);
            pstmt.setString(2, book_name.toString());
            pstmt.setString(3, Username);
            pstmt.setDate(4, java.sql.Date.valueOf(Date.valueOf(String.valueOf(java.util.Date.from(Instant.now()))).toLocalDate()));
            pstmt.setDate(5, java.sql.Date.valueOf(Date.valueOf(String.valueOf(java.util.Date.from(Instant.now()))).toLocalDate()));

            pstmt.executeUpdate();

            if (Q.next()) {
                String book_Name = Q.getString("BookName");


                String info_query = "SELECT * FROM USERS WHERE Username = '"+Username+"';";
                PreparedStatement ps = conn.prepareStatement(info_query);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    String email = rs.getString("Email");
                    String first_line_addy = rs.getString("House_Number_Street_Name");
                    String city = rs.getString("City");
                    String postcode = rs.getString("Postcode");
                    String first_name = rs.getString("First_Name");

                    System.out.println(first_name);


                }
            }

            System.out.println("Successfully Issued");

        }

    } catch (SQLException e) {
        System.err.println(e);
        System.out.println("Error :" + e.getMessage());
    }

    return 0;

}



}
