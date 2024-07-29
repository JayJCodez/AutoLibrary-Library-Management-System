package userHandlers;

import java.io.IOException;

import Model.UserContextModel;
import handlers.ViewBooksHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class User_Bookkeeping {

    //Referencing GUI objects.

    @FXML
    private BorderPane LibraryView;

    @FXML
    private Label ViewBooksButton;

    @FXML
    private Label transactionHistory;

    @FXML
    private Label transactionHistory1;

    String uname;

    //This event loads the Saved Items page.

    @FXML
    void SavedItems(MouseEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/userFXMLpages/User_SavedItems.fxml"));
        AnchorPane view = loader.load();
        LibraryView.setCenter(view);


        User_SavedItems savedItems = loader.getController();
        savedItems.setUsername(UserContextModel.getInstance().getUsername());


    }

    public void setUser(String username){

        UserContextModel.getInstance().setUsername(username);
        uname = username;


    }

    //This event loads the user View Book page.

    @FXML
    void ViewBookbtn(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/AdminFXMLPages/ViewBooks.fxml"));
        AnchorPane view = loader.load();
        LibraryView.setCenter(view);

        ViewBooksHandler viewbooks = loader.getController();
        viewbooks.setUsername(uname);
        System.out.println(uname);




    }

    //This event loads the Transaction History class.

    @FXML
    void showTransactionHistory(MouseEvent event) throws IOException {

        AnchorPane view = FXMLLoader.load(getClass().getResource("/userFXMLpages/User_Transactions.fxml"));
        LibraryView.setCenter(view);

    }



}
