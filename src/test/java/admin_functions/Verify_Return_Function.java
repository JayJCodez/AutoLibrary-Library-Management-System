package admin_functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Connection.DBConnection;

public class Verify_Return_Function {

    public Connection conn;

    public Verify_Return_Function() throws SQLException {

        this.conn = DBConnection.getInstance().connectingDB();

    }


    public boolean verify_return(int bookid, String username){

        String BookID = null;
        int BookIDInt = 0;
        String Username = null;

        Username = username;
        BookIDInt = bookid;

        if(Username == null || BookIDInt == 0){
               System.out.println("Username or BookID is null");
        }

        String VerifyUser = "SELECT * FROM USERTRANSACTIONS WHERE Username = '"+Username+"' AND Returned = 'True' AND BookID = "+BookIDInt+";";
        String NotReturned = "SELECT * FROM USERTRANSACTIONS WHERE Username = '"+Username+"' AND Returned = 'False' AND BookID = "+BookIDInt+";";

        try {

            PreparedStatement Verify = conn.prepareStatement(VerifyUser);
            PreparedStatement getDetail = conn.prepareStatement(NotReturned);
            ResultSet F = Verify.executeQuery();
            ResultSet P = getDetail.executeQuery();


            if (F.next()) {

                java.sql.Date DateReturned = F.getDate(6);
                System.out.println("Book was successfully returned \nExpected Return Date: "+DateReturned+"");
                return false;

            } else if(P.next()){

                java.sql.Date DateToBeReturned = P.getDate(6);
                System.out.println("Book has not been returned \nExpected Return Date: "+DateToBeReturned+"");


            }else{


                System.out.println("This transaction is not on the system!");

            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        return true;

    }

}
