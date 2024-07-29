package userHandlers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import Model.BookViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class User_ViewBook implements Initializable{

    @FXML
    private TableView<BookViewModel> BookTable;

    @FXML
    private TableColumn<BookViewModel, String> Book_Cover;

    @FXML
    private TableColumn<BookViewModel, String> Book_Name;

    @FXML
    private TableColumn<BookViewModel, String> Publisher_Name;

    BookViewModel book;

    @FXML
    private BorderPane imageView;

    ObservableList<BookViewModel> bookList = FXCollections.observableArrayList();

    private void loadTableData() throws SQLException {

        //Setting table properties in preparation for setting values into the same table.
        try {
            refreshTable();

            Book_Name.setCellValueFactory(new PropertyValueFactory<>("Book_Name"));
            Publisher_Name.setCellValueFactory(new PropertyValueFactory<>("Publisher"));
            Book_Cover.setCellFactory(cellFactory);

            BookTable.setItems(bookList);


        } catch (Exception e) {
            System.err.println(e);
        }

    }

    @FXML
    private void refreshTable() throws SQLException, IOException {

        //Retrieving data I would like to set to my Table from my SQL Database

        bookList.clear();

        String query = "select * from LibraryBooks;";

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Test", "root" , "138Jesse.");
        PreparedStatement seeResources = conn.prepareStatement(query);

        ResultSet results = seeResources.executeQuery();


        //Setting values of SQL Database to List before finally setting items to display in the table.

        while (results.next()) {



            bookList.add(new BookViewModel(
                    results.getString("BookName"),
                    results.getString("BookPublisher"),
                    results.getString("Book_Extension")

            ));

            BookTable.setItems(bookList);


        }





    }

    // Creating labels in empty column for Table.

    Callback<TableColumn<BookViewModel, String>, TableCell<BookViewModel, String>> cellFactory = (TableColumn<BookViewModel, String> param) -> {

        final TableCell<BookViewModel, String> cell = new TableCell<BookViewModel, String>() {

            //Updating properties of table where column is empty

            @Override
            public void updateItem(String Item, boolean Empty ) {

                super.updateItem(Item, Empty);

                if (Empty) {
                    setGraphic(null);
                    setText(null);
                }else{


                    //ImageView below ws to be used to display images inside table. However I ended up displaying these images outside the table currently.

                    // ImageView bookCover = new ImageView();

                    //Adding button to the table

                    Button previewBTN = new Button();

                    previewBTN.setText("Preview Cover");






                    //Setting action of the buttons placed in the Table based on the selection model of my Model.

                    previewBTN.setOnMouseClicked((MouseEvent event) -> {

                        try {
                            book = BookTable.getSelectionModel().getSelectedItem();
                            String findpreview = "select * from Resources WHERE File_Name = '"+book.getBook_Name()+"'";
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Test", "root", "138Jesse.");
                            PreparedStatement preparedStatement = connection.prepareStatement(findpreview);
                            ResultSet selection = preparedStatement.executeQuery();

                            if (selection.next()) {

                                InputStream BlobIn = selection.getBinaryStream("ResourceCoverImg");
                                OutputStream ImageOut = new FileOutputStream(new File("Catalogue.png"));
                                byte[] content = new byte[1024];

                                int size = 0;
                                while ((size = BlobIn.read(content)) != -1) {
                                    ImageOut.write(content, 0, size);
                                }


                                BlobIn.close();
                                ImageOut.close();


                                Image image = new Image("file:Catalogue.png",350, 350, true, true);

                                ImageView imgview = new ImageView(image);
                                imgview.setFitWidth(350);
                                imgview.setFitHeight(350);
                                imgview.setPreserveRatio(true);
                                imageView.setCenter(imgview);
                                imageView.setVisible(true);
                                previewBTN.setVisible(true);







                            }





                        } catch (Exception ex) {
                            System.err.println(ex);
                        }


                    });


                    //  Creating HBOX and inserting button into HBOX so as to provide easy styling and positioning in the table of the buttons.


                    HBox view = new HBox(previewBTN);
                    view.setStyle("-fx-alignment:center");
                    HBox.setMargin(previewBTN, new Insets(2, 3, 0, 2));

                    setGraphic(view);

                    setText(null);

                }

            }
        };

        //Here I am returning the empty cell values which I have just been defined above.

        return cell;

    };

    //This is my initializer class. This is ran on the start of loading of the fxml page. Hence, allowing me to populate my table on start of the function.

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            loadTableData();
            refreshTable();

            // Handling all exceptions of the individial functions to prevent heaps of stack errors.

        } catch (SQLException | IOException e) {

            e.printStackTrace();
        }

    }
}
