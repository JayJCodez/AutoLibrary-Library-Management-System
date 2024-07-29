package handlers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Alerts.NotificationHandler;
import Connection.DBConnection;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import Model.Book;
import Model.UserContextModel;
import Model.BookAdverts;

public class ViewBooksHandler implements Initializable {


    ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    private HBox CardLayout;

    // private List<Book> books;
    private List<BookAdverts> recentlyAdded;

    @FXML
    private GridPane BookContainer;

    private Connection conn;

    String username;

    int user_id;

    public ViewBooksHandler() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }


    public void setUsername(String uname){

        UserContextModel.getInstance().setUsername(uname);
        username = UserContextModel.getInstance().getUsername();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            recentlyAdded = new ArrayList<>(recentlyAdded());
            bookList = list();
        } catch (Exception e) {
            // TODO: handle exception
        }
        // recentlyAdded = new ArrayList<>(recentlyAdded());


        int column = 0;
        int row = 1;


        try {

            for (BookAdverts value : recentlyAdded) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getClassLoader().getResource("AdminFXMLPages/BookCard.fxml"));

                AnchorPane cardLayout = fxmlLoader.load();
                BookCardHandler cardHandler = fxmlLoader.getController();
                cardHandler.setData(value);
                CardLayout.getChildren().add(cardLayout);


            }

            for(Book book : bookList){

                FXMLLoader fxmlLoader1 = new FXMLLoader();
                fxmlLoader1.setLocation(getClass().getClassLoader().getResource("AdminFXMLPages/Book.fxml"));

                AnchorPane BookInfoContainer = fxmlLoader1.load();
                BookController bookController = fxmlLoader1.getController();
                bookController.setData(book);

                while (column == 0) {
                    column++;
                }

                if (column == 4) {

                    column = 0;
                    ++row;
                    ++column;

                }

                // Retrieve the label from the book and set event handler
                Label label = book.getLabel();
                label.setOnMouseClicked((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        // Handle label click event here
                        System.out.println("Label clicked for book: " + book.getName());
                        try {
                            openNewPage(book.getName(), book.getAuthor());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                Label saveLabel = book.getSaveItem();
                saveLabel.setOnMouseClicked((EventHandler<? super MouseEvent>)new EventHandler<Event>() {

                    @Override
                    public void handle(Event arg0) {


                        int book_id = 0;

                        String check_if_saved = "SELECT * FROM SavedItems WHERE book_id = ? AND user_id = ?";

                        String query = "INSERT INTO SavedItems (book_ID, user_id) values (?,?);";

                        String findbook = "SELECT BookID FROM LibraryBooks WHERE BookName = '"+book.getName()+"'";

                        String finduserID = "SELECT User_ID FROM USERS WHERE Username = '"+username+"';";

                        try {
                            PreparedStatement ps = conn.prepareStatement(findbook);

                            PreparedStatement ps3 = conn.prepareStatement(finduserID);

                            PreparedStatement ps2 = conn.prepareStatement(query);

                            PreparedStatement ps4 = conn.prepareStatement(check_if_saved);

                            ResultSet rs = ps.executeQuery();

                            ResultSet rs3 = ps3.executeQuery();




                            if (rs3.next()) {

                                user_id = rs3.getInt("User_ID");
                                ps4.setInt(1, user_id);


                            }

                            if (rs.next()) {
                                book_id = rs.getInt("BookID");
                                ps4.setInt(1, book_id);
                                ps4.setInt(2, user_id);
                                ResultSet rs4 = ps4.executeQuery();

                                if(rs4.next()){

                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Information");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Book already saved");
                                    alert.showAndWait();
                                    return;

                                }
                            }



                            ps2.setInt(1, book_id);
                            ps2.setInt(2, user_id);

                            ps2.execute();

                            System.out.println(UserContextModel.getInstance().getUsername());

                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                    }

                });
                // if (column > 3) {

                //     column = 0;
                //     ++row;

                // }


                // Button btn = new Button();
                BookContainer.add(BookInfoContainer, column++, row);
                // BookContainer.add(btn, column, row);
                // BookContainer.applyCss(ger);
                // GridPane.setMargin(BookInfoContainer, new Insets(10));
                // BookContainer.setStyle("-fx-spacing: 20px;");
                BookContainer.setVgap(25);

                BookContainer.setHgap(50);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // Creates catalogue of books

    private List<BookAdverts> recentlyAdded(){


        List<BookAdverts> ls = new ArrayList<>();
        BookAdverts book = new BookAdverts();
        book.setName("Soul");
        book.setImageSrc("/Images/BookCoverTest.jpeg");
        book.setAuthor("Olivia Wilson");
        ls.add(book);


        book = new BookAdverts();
        book.setName("Don't Look Back");
        book.setImageSrc("/Images/BookCoverPage.png");
        book.setAuthor("Issac Nelson");
        ls.add(book);


        book = new BookAdverts();
        book.setName("The Hypocrite World");
        book.setImageSrc("/Images/Cover1.jpeg");
        book.setAuthor("Sophia Hill");
        ls.add(book);


        book = new BookAdverts();
        book.setName("Lear");
        book.setImageSrc("/Images/Cover2.jpeg");
        book.setAuthor("Mike Johnson");
        ls.add(book);


        book = new BookAdverts();
        book.setName("The Longest Night of Charlie Noon");
        book.setImageSrc("/Images/Cover3.jpeg");
        book.setAuthor("Jesse");
        ls.add(book);

        book = new BookAdverts();
        book.setName("Coming Soon...");
        book.setImageSrc("/Images/Cover3.jpeg");
        book.setAuthor("Jesse");
        ls.add(book);

        return ls;

    }

    //    private List<Book> books(){

    //     List<Book> lp = new ArrayList<>();
    //     Book book = new Book();
    //     book.setName("Don't Look Back");
    //     book.setImageSrc("/Images/BookCoverPage.png");
    //     book.setAuthor("Issac Nelson");
    //     lp.add(book);


    //     book = new Book();
    //     book.setName("The Hypocrite World");
    //     book.setImageSrc("/Images/Cover1.jpeg");
    //     book.setAuthor("Sophia Hill");
    //     lp.add(book);


    //     book = new Book();
    //     book.setName("Lear");
    //     book.setImageSrc("/Images/Cover2.jpeg");
    //     book.setAuthor("Mike Johnson");
    //     lp.add(book);

    //     book = new Book();
    //     book.setName("Lear");
    //     book.setImageSrc("/Images/Cover2.jpeg");
    //     book.setAuthor("Mike Johnson");
    //     lp.add(book);

    //     book = new Book();
    //     book.setName("Lear");
    //     book.setImageSrc("/Images/Cover2.jpeg");
    //     book.setAuthor("Mike Johnson");
    //     lp.add(book);

    //     book = new Book();
    //     book.setName("Lear");
    //     book.setImageSrc("/Images/Cover2.jpeg");
    //     book.setAuthor("Mike Johnson");
    //     lp.add(book);

    //     book = new Book();
    //     book.setName("Lear");
    //     book.setImageSrc("/Images/Cover2.jpeg");
    //     book.setAuthor("Mike Johnson");
    //     lp.add(book);

    //     book = new Book();
    //     book.setName("The Hypocrite World");
    //     book.setImageSrc("/Images/Cover1.jpeg");
    //     book.setAuthor("Sophia Hill");
    //     lp.add(book);


    //     book = new Book();
    //     book.setName("Lear");
    //     book.setImageSrc("/Images/Cover2.jpeg");
    //     book.setAuthor("Mike Johnson");
    //     lp.add(book);

    //     book = new Book();
    //     book.setName("Lear");
    //     book.setImageSrc("/Images/Cover2.jpeg");
    //     book.setAuthor("Mike Johnson");
    //     lp.add(book);

    //     book = new Book();
    //     book.setName("Lear");
    //     book.setImageSrc("/Images/Cover2.jpeg");
    //     book.setAuthor("Mike Johnson");
    //     lp.add(book);

    //     book = new Book();
    //     book.setName("Lear");
    //     book.setImageSrc("/Images/Cover2.jpeg");
    //     book.setAuthor("Mike Johnson");
    //     lp.add(book);

    //     book = new Book();
    //     book.setName("Lear");
    //     book.setImageSrc("/Images/Cover2.jpeg");
    //     book.setAuthor("Mike Johnson");
    //     lp.add(book);

    //     return lp;

    //    }

    public ObservableList<Book> list() throws IOException, SQLException{


        String query = "SELECT BookCover, BookName, BookPublisher FROM LibraryBooks";

        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            InputStream stream = rs.getBinaryStream("BookCover");
            Image image =   new Image(stream);

            Label viewbutton = new Label();



            bookList.add(new Book(
                    rs.getString("BookName"),
                    image,
                    rs.getString("BookPublisher"),
                    viewbutton
            ));

            stream.close();


        }

        return bookList;


    }

// public void setLabelAction(ListView<Book> listView) {
//         listView.setCellFactory(new Callback<ListView<Book>, ListCell<Book>>() {
//             @Override
//             public ListCell<Book> call(ListView<Book> param) {
//                 return new ListCell<Book>() {
//                     @Override
//                     protected void updateItem(Book item, boolean empty) {
//                         super.updateItem(item, empty);
//                         if (empty || item == null) {
//                             setText(null);
//                             setGraphic(null);
//                         } else {
//                             ImageView imageView = new ImageView(item.getImageSrc());
//                             imageView.setFitWidth(100);
//                             imageView.setFitHeight(100);

//                             Label label = new Label(item.getName());
//                             label.setGraphic(imageView);

//                             // Set action for label
//                             label.setOnMouseClicked(event -> {
//                                 System.out.println("Label clicked: " + item.getName());
//                                 // Add your action here
//                             });

//                             setGraphic(label);
//                         }
//                     }
//                 };
//             }
//         });
//     }

    private void openNewPage(String bookname, String author) throws Exception {
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/AdminFXMLPages/BookDetails.fxml"));
        Parent root = loader.load(); // Replace "NewPage.fxml" with your FXML file


        BookDetailHandler bookdeets = loader.getController();

        bookdeets.setBookData(bookname, author);


        newStage.setTitle("Book Details");
        newStage.setScene(new Scene(root, 600, 400));
        newStage.show();

    }


}