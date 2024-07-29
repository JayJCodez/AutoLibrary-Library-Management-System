package userHandlers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import Connection.DBConnection;
import Model.Book;
import Model.SavedBook;
import Model.UserContextModel;
import handlers.BookController;
import handlers.BookDetailHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class User_SavedItems implements Initializable {

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scrollpaneanchor;

    private Connection conn;

    ObservableList<SavedBook> bookList = FXCollections.observableArrayList();

    String uname;

    int user_id;

    public User_SavedItems() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();
    }



    public ObservableList<SavedBook> list() throws IOException, SQLException{

        String userid = "SELECT * FROM USERS WHERE Username = '"+UserContextModel.getInstance().getUsername()+"';";

        PreparedStatement ps1 = conn.prepareStatement(userid);

        ResultSet rs1 = ps1.executeQuery();


        if (rs1.next()) {
            user_id = rs1.getInt("User_ID");
            System.out.println(user_id);
        }


        String query = "SELECT LibraryBooks.BookID, LibraryBooks.BookName, LibraryBooks.BookPublisher, LibraryBooks.BookCover, SavedItems.user_id \n" + //
                "FROM LibraryBooks \n" + //
                "INNER JOIN SavedItems ON SavedItems.book_id = LibraryBooks.BookID\n" + //
                "WHERE user_id = "+user_id+";";



        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();





        while (rs.next()) {

            InputStream stream = rs.getBinaryStream("BookCover");
            Image image =   new Image(stream);

            Label viewbutton = new Label();
            ImageView imageview = new ImageView(new Image("Icons/icons8-close-window-48.png"));



            bookList.add(new SavedBook(
                    rs.getString("BookID"),
                    rs.getString("BookName"),
                    image,
                    rs.getString("BookPublisher"),
                    imageview
            ));



        }

        return bookList;


    }



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {


        loadSavedItems();


    }

    public void setUsername(String username){

        UserContextModel.getInstance().setUsername(username);
        uname = UserContextModel.getInstance().getUsername();

    }


    public void loadSavedItems(){
        bookList.clear();
        try {
            // recentlyAdded = new ArrayList<>(recentlyAdded());
            bookList = list();
        } catch (Exception e) {
            // TODO: handle exception
        }
        // recentlyAdded = new ArrayList<>(recentlyAdded());


        int column = 0;
        int row = 1;


        try {
            //      for (BookAdverts value : recentlyAdded) {
            //     FXMLLoader fxmlLoader = new FXMLLoader();
            //     fxmlLoader.setLocation(getClass().getResource("/resources/BookCard.fxml"));

            //     AnchorPane cardLayout = fxmlLoader.load();
            //     BookCardHandler cardHandler = fxmlLoader.getController();
            //     cardHandler.setData(value);
            //     CardLayout.getChildren().add(cardLayout);


            // }

            for(SavedBook book : bookList){

                FXMLLoader fxmlLoader1 = new FXMLLoader();
                fxmlLoader1.setLocation(getClass().getResource("/userFXMLpages/SavedBook.fxml"));

                AnchorPane BookInfoContainer = fxmlLoader1.load();
                SavedBookController bookController = fxmlLoader1.getController();
                bookController.setData(book);

                while (column == 0) {
                    column++;
                }

                if (column == 4) {

                    column = 0;
                    ++row;
                    ++column;

                }

//                 Retrieve the label from the book and set event handler
                ImageView label = book.getDeletebtn();
                label.setOnMouseClicked((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        // Handle label click event here
                        System.out.println("Label clicked for book: " + book.getName());
                        String query = "DELETE FROM SavedItems WHERE user_id = "+user_id+" AND book_id = "+book.getId()+";";

                        try {
                            PreparedStatement ps = conn.prepareStatement(query);

                            ps.execute();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        grid.getChildren().clear();

                        loadSavedItems();
                    }
                });

//                 Retrieve the label from the book and set event handler
                  Label view_btn = book.getView();
                  view_btn.setOnMouseClicked((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
                      @Override
                      public void handle(MouseEvent event) {
                          // Handle label click event here

                          try {
                              openNewPage(book.getName(), book.getAuthor());
                          } catch (Exception e) {
                              throw new RuntimeException(e);
                          }
                      }
                  });

                // if (column > 3) {

                //     column = 0;
                //     ++row;

                // }


                // Button btn = new Button();
                grid.add(BookInfoContainer, column++, row);
                // BookContainer.add(btn, column, row);
                // BookContainer.applyCss(ger);
                // GridPane.setMargin(BookInfoContainer, new Insets(10));
                // BookContainer.setStyle("-fx-spacing: 20px;");
                grid.setVgap(25);

                grid.setHgap(50);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


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
