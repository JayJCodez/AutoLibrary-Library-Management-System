package handlers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class BookkeepingHandler implements Initializable {


   
    


    @FXML
    private Label IssueBookButton;

    @FXML
    private BorderPane LibraryView;

    @FXML
    private Label NewBookButton;

    @FXML
    private Label ReturnBookButton;

    @FXML
    private Label VerifyReturnButton;

    @FXML
    private Label ViewBooksButton;

     @Override
    public void initialize(URL location, ResourceBundle resources) {

       

        try {
          
            AnchorPane view = FXMLLoader.load(getClass().getResource("/AdminFXMLPages/BookKeepingDashboard.fxml"));
            LibraryView.setCenter(view);

        
        } catch (Exception e) {
           System.err.println(e);
        }
       
  
    }

    @FXML
    void ReturnBookButton(MouseEvent event) {

        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("/AdminFXMLPages/ReturnBookPage.fxml"));
            LibraryView.setCenter(view);
        
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    void clickNewBook(MouseEvent event) throws IOException {

        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("/AdminFXMLPages/AddNewBookPage.fxml"));
            LibraryView.setCenter(view);
        
        } catch (Exception e) {
            System.err.println(e);
        }
      

    }

    @FXML
    void IssueBookbtn(MouseEvent event) {

        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("/AdminFXMLPages/IssueBookPage.fxml"));
            LibraryView.setCenter(view);
        
        } catch (Exception e) {
            System.err.println(e);
        }
      

    }
    
    @FXML
    void ViewBookbtn(MouseEvent event) {

        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("/AdminFXMLPages/ViewBooks.fxml"));
            LibraryView.setCenter(view);
        
        } catch (Exception e) {
            System.err.println(e);
        }
    }


    @FXML
    void VerifyReturnbtn(MouseEvent event) {

        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("/AdminFXMLPages/VerifyReturn.fxml"));
            LibraryView.setCenter(view);
        
        } catch (Exception e) {
            System.err.println(e);
        }
    }

  
}
