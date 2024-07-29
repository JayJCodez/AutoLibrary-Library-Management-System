package userHandlers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import Connection.DBConnection;
import Model.UserContextModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class User_HomeDashboard implements Initializable{

    //Referencing GUI Objects.

    @FXML
    private Pane BooksAvailable;

    @FXML
    public Label BooksAvailableNo;

    @FXML
    private Pane BooksReturned;

    @FXML
    private Label BooksReturnedNo;

    @FXML
    private Pane UsersRegistered;

    @FXML
    private Label UsersRegisteredNo;

    UserContextModel user = new UserContextModel();

    String username;

    Connection conn;

    public User_HomeDashboard() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }

    //This is my initializer class which as FXML Page loads.
    public void initialize(URL arg0, ResourceBundle arg1){


        //Here, I recieve the user name from the user context model
        username = UserContextModel.getInstance().getUsername();

        //This function dispalys live info displayed on the Dashboard e.g current users online; No of books to be returned etc 

        liveData(username);

        //This function aims to refresh the data in case any chnages or modifications have been made while logged in to user DB information.
        refreshData(username);



    }

    //This function dispalys live info displayed on the Dashboard e.g current users online; No of books to be returned etc 
    //This function is called in the user-home initially before being called again here for updated values, if any.


    public void liveData(String username){






        String BooksAvailable = "SELECT COUNT(BookName) FROM LibraryBooks;";
        String BooksToBeReturned = "SELECT COUNT(TransactionID) FROM USERTRANSACTIONS WHERE Username = '"+UserContextModel.getInstance().getUsername()+"' AND Returned = 'False'";

        String onlineUsers = "SELECT COUNT(First_Name) FROM USERS;";



        try {


            PreparedStatement pstmt = conn.prepareStatement(BooksAvailable);
            PreparedStatement pstmtCheck = conn.prepareStatement(BooksToBeReturned);
            PreparedStatement online = conn.prepareStatement(onlineUsers);
            pstmt.executeQuery();
            pstmtCheck.executeQuery();

            ResultSet R = pstmt.executeQuery();
            ResultSet F = pstmtCheck.executeQuery();
            ResultSet O = online.executeQuery();


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

            if (O.next()) {
                Integer OnlineUsers = O.getInt(1);

                String onUsers = String.valueOf(OnlineUsers);
                UsersRegisteredNo.setText(onUsers);
            }






        } catch (Exception e) {
            System.err.println(e);
        }


    }

    //Here I instantiated the liveData funtion and called it in here so when it is called again it will run the whole live data function and return updated values 
    public void refreshData(String var){


        liveData(var);

    }

    public void setUsername(String username){

        UserContextModel.getInstance().setUsername(username);

    }
}
