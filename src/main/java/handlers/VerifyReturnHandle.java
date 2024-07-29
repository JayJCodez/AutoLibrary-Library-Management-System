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

public class VerifyReturnHandle {

    @FXML
    private TextField BookIDTxt;

    @FXML
    private Label ErrorLabel;

    @FXML
    private Label Searchbtn;

    @FXML
    private TextField UsernameTxt;

    private Connection conn;

    public VerifyReturnHandle() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }

    @FXML
    void VerifyReturn(MouseEvent event) throws SQLException {

        String BookID = BookIDTxt.getText();
        int BookIDInt = Integer.parseInt(BookID);
        String Username = UsernameTxt.getText();

        if(Username == null || BookIDInt == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in details");
            alert.showAndWait();
            return;
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
                ErrorLabel.setText("Book was successfully returned \nExpected Return Date: "+DateReturned+"");
                return;

            } else if(P.next()){

                java.sql.Date DateToBeReturned = P.getDate(6);
                ErrorLabel.setText("Book has not been returned \nExpected Return Date: "+DateToBeReturned+"");


            }else{


                ErrorLabel.setText("This transaction is not on the system!");

            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

}
