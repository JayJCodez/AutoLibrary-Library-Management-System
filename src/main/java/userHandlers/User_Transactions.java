package userHandlers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import Connection.DBConnection;
import Model.UserContextModel;
import Model.User_TransactionsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class User_Transactions implements Initializable{

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private AnchorPane anchor;



    Connection conn;

    ObservableList<User_TransactionsModel> transactions = FXCollections.observableArrayList();

    public User_Transactions() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        try {
            transactions = list();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        int column = 0;
        int row = 1;



        try {
            for(User_TransactionsModel value : transactions){

                FXMLLoader fxmlLoader1 = new FXMLLoader();
                fxmlLoader1.setLocation(getClass().getResource("/userFXMLpages/User_TransactionsBar.fxml"));

                AnchorPane transactionbar = fxmlLoader1.load();
                TransactionBar transaction_handler = fxmlLoader1.getController();
                transaction_handler.setData(value);

                while (column == 0) {
                    column++;
                }

                if (column == 2) {

                    column = 0;
                    ++row;
                    ++column;

                }

                //  // Retrieve the label from the book and set event handler
                //  Label label = book.getLabel();
                //  label.setOnMouseClicked((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
                //      @Override
                //      public void handle(MouseEvent event) {
                //          // Handle label click event here
                //          System.out.println("Label clicked for book: " + book.getName());
                //          try {
                //             openNewPage(book.getName(), book.getAuthor());
                //         } catch (Exception e) {
                //             e.printStackTrace();
                //         }
                //      }
                //  });
                //  Label saveLabel = book.getSaveItem();
                //  saveLabel.setOnMouseClicked((EventHandler<? super MouseEvent>)new EventHandler<Event>() {

                //     @Override
                //     public void handle(Event arg0) {



                //         String query = "INSERT INTO SavedItems (book_ID, user_id) values (?,?);";

                //         String findbook = "SELECT * FROM LibraryBooks WHERE BookName = '"+book.getName()+"'";

                //         String finduserID = "SELECT * FROM USERS WHERE Username = '"+username+"';";

                //         try {
                //             PreparedStatement ps = conn.prepareStatement(findbook);

                //             PreparedStatement ps3 = conn.prepareStatement(finduserID);

                //             PreparedStatement ps2 = conn.prepareStatement(query);

                //             ResultSet rs = ps.executeQuery();

                //             ResultSet rs3 = ps3.executeQuery();

                //             if (rs.next()) {
                //                 book_id = rs.getInt("BookID");
                //             }

                //             if (rs3.next()) {
                //                 user_id = rs3.getInt("User_ID");

                //             }

                //                 ps2.setInt(1, book_id);
                //                 ps2.setInt(2, user_id);

                //                 ps2.execute();

                //                 System.out.println(UserContextModel.getInstance().getUsername());

                //         } catch (SQLException e) {
                //             // TODO Auto-generated catch block
                //             e.printStackTrace();
                //         }


                //     }

                //  });
                // if (column > 3) {

                //     column = 0;
                //     ++row;

                // }


                // Button btn = new Button();
                grid.add(transactionbar, column++, row);
                // BookContainer.add(btn, column, row);
                // BookContainer.applyCss(ger);
                // GridPane.setMargin(BookInfoContainer, new Insets(10));
                // BookContainer.setStyle("-fx-spacing: 20px;");
                grid.setVgap(25);

                grid.setHgap(50);

            }
        } catch (Exception e) {
            // TODO: handle exception
        }


    }

    public ObservableList<User_TransactionsModel> list() throws SQLException{



        String query = "select * from USERTRANSACTIONS WHERE Username = '"+UserContextModel.getInstance().getUsername()+"';";

        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Date collectDate = rs.getDate("Collection_Date");

            LocalDate date = collectDate.toLocalDate();

            Date returnDate = rs.getDate("Return_Date");

            // LocalDate return_date =  returnDate.toLocalDate();



            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            transactions.add(new User_TransactionsModel(
                    rs.getString("BookName"),
                    date.toString(),
                    returnDate.toString(),
                    Integer.toString(rs.getInt("TransactionID")),
                    rs.getString("Returned")
                    ));

        }







        return transactions;



    }

}
