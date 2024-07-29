package handlers;

import java.io.InputStream;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookDetailHandler implements Initializable{

    @FXML
    private Label availability;

    @FXML
    private Label bookName;

    @FXML
    private ImageView imgView;

    @FXML
    private Label publisher;

    @FXML
    private Label reviews;

    String book_name;

    String publisher_txt;

    String reviews_txt;

    String Availability;

    Connection conn;

    int bookID;

    public BookDetailHandler() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

  

    public String getReviews_txt() {
        return reviews_txt;
    }

    public void setReviews_txt(String reviews_txt) {
        this.reviews_txt = reviews_txt;
    }

    public String getAvailability() {
        return Availability;
    }

    public void setAvailability(String availability) {
        Availability = availability;
    }

    public void displayBookDetails(String name){

        bookName.setText(name);

    }

    public void setBookData(String Book_Name, String Author){


        book_name = Book_Name;
        setBook_name(Book_Name);
        bookName.setText(Book_Name);


        publisher_txt = Author;
        publisher.setText(Author);


        // reviews_txt = review

        Availability = null;

        String query = "SELECT * FROM LibraryBooks WHERE BookName = '"+book_name+"'"; 

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                InputStream stream = rs.getBinaryStream("BookCover");
                Image image =   new Image(stream);
                imgView.setImage(image);
                imgView.setFitHeight(282);
                imgView.setFitWidth(198);
                bookID = rs.getInt("BookID");

                checkAvailable(bookID);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
      
        // try {
        //     // checkAvailable();
        // } catch (SQLException e) {
            
        //     e.printStackTrace();
        // }
        // displayBookDetails();


    }

    public void checkAvailable(int book_ID) throws SQLException{


        String check_availability = "SELECT * FROM book_availability WHERE bookID = "+book_ID+";";

        PreparedStatement ps = conn.prepareStatement(check_availability);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            
            Availability = rs.getString("availability");

        }

        System.out.println(Availability);

        availability.setText(Availability);


    }
}
