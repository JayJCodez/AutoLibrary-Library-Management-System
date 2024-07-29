package handlers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Connection.DBConnection;
import javafx.util.Callback;

public class NewBookHandler implements Initializable {

    @FXML
    private Label AddNewBookbtn;

    @FXML
    private TextField BookNameTxt;

    @FXML
    private Label ErrorMessage;

    @FXML
    private AnchorPane NewBookView;

    @FXML
    private TextField PriceTxt;

    @FXML
    private TextField PublisherTxt;

    @FXML
    private TextField bookCoverSrc;

    @FXML
    private ComboBox<String> genre_combo;

    @FXML
    private Label chooseFileBtn;

    @FXML
    private Label uploadFileBtn;

    private Connection conn;

    private File imgFile;

    String extension;


    public NewBookHandler() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }

    @FXML
    void AddNewBookTDB(MouseEvent event) throws IOException {


        String BookName = BookNameTxt.getText();
        String Publisher = PublisherTxt.getText();
        String BookPrice = PriceTxt.getText();
        int BookPriceInt = Integer.parseInt(BookPrice);

        if(BookName.isEmpty() || Publisher.isEmpty() || BookPrice.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
            return;
        }

        try {

            if(imgFile == null){

                imgFile = new File("/Images/fiction-novel-book-cover-template.jpg");

            }

            FileInputStream fis = new FileInputStream(imgFile);



            String ADD_NEW_BOOK = "insert into LibraryBooks (BookName, BookPublisher, BookPrice, BookCover, Book_Extension, genre) values  (?, ?, ?, ?, ?, ?)";
            String CHECK_DUPLICATE_BOOK = "SELECT * FROM LibraryBooks where BookName = '"+BookName+"';";
            PreparedStatement pstmt = conn.prepareStatement(ADD_NEW_BOOK);
            PreparedStatement pstmtCheck = conn.prepareStatement(CHECK_DUPLICATE_BOOK);



            ResultSet R = pstmtCheck.executeQuery();

            if (R.next()) {
                ErrorMessage.setText("This book is already stored in the database");
            }
            else{


                pstmt.setString(1,BookName);
                pstmt.setString(2, Publisher);
                pstmt.setInt(3, BookPriceInt);
                pstmt.setBinaryStream(4, fis);
                pstmt.setString(5, extension);
                pstmt.setString(6, genre_combo.getValue());

                pstmt.executeUpdate();

                ErrorMessage.setText("Book Added Successfully to your Database");

            }
        } catch (SQLException e ) {
            System.err.println(e);
            e.printStackTrace();
        }



    }

    @FXML
    void chooseFile(MouseEvent event) {

        FileChooser chooser = new FileChooser();
        imgFile = chooser.showOpenDialog(null);

        if (imgFile != null) {

            String name = imgFile.getName();
            bookCoverSrc.setText(imgFile.getName());

            int lastIndex = name.lastIndexOf('.'); // Find the last occurrence of '.'

            extension = null;

            if (lastIndex > 0) { // Check if '.' exists and it's not the first character

                extension = name.substring(lastIndex + 1); // Extract the extension

                // System.out.println(extension);
            }



        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add book genres to the ComboBox
        genre_combo.setStyle("-fx-text-fill: white");
        genre_combo.getItems().addAll(
                "Fiction",
                "Mystery",
                "Thriller",
                "Science Fiction",
                "Fantasy",
                "Romance",
                "Historical Fiction",
                "Horror",
                "Adventure",
                "Biography",
                "Autobiography",
                "Memoir",
                "Poetry",
                "Drama",
                "Comedy",
                "Self-help",
                "Business",
                "Psychology",
                "Philosophy",
                "Religion",
                "Cooking",
                "Travel",
                "Art",
                "Science",
                "History"
        );
        // Apply CSS styling to change text color
        genre_combo.getEditor().setStyle("/* combo-box selected text */\n" +
                ".combo-box .list-view .list-cell:focused {\n" +
                "  -fx-text-fill: white;\n" +
                "}\n");
        genre_combo.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            setStyle("-fx-text-fill: #41a5e4;"); // Change text color here
                        }
                    }
                };
            }
        });
        genre_combo.setValue("Travel");

    }

    // @FXML
    // void uploadFile(MouseEvent event) throws FileNotFoundException, SQLException {


    //     try {


    //         FileInputStream fis = new FileInputStream(imgFile);


    //         String insertImg = "INSERT INTO LibraryBooks(BookCover, Book_Extension) values (?,'"+extension+"') WHERE BookName = '"+BookNameTxt.getText()+"';";


    //         PreparedStatement ps = conn.prepareStatement(insertImg);


    //         // ResultSet rs = ps.executeQuery();




    //             ps.setBinaryStream(1, fis);
    //             ps.execute();




    //         System.out.println("SUCCESS");


    //     } catch (SQLException e) {


    //         System.err.println(e);


    //     }




    // }
}