package handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import java.util.ResourceBundle;
import Connection.DBConnection;
import Model.ResourceTableModel;

public class ResourceUploadHandler implements Initializable {


    @FXML
    private Label chooseCover;

    @FXML
    private Label uploadCoverBTN;

    @FXML
    private TableColumn<ResourceTableModel, String> OptionsCol;

    @FXML
    private TableColumn<ResourceTableModel, String> FileExtensionColumn;

    @FXML
    private TableColumn<ResourceTableModel, String> FileNameColumn;


    @FXML
    private TableColumn<ResourceTableModel, String> FileTypeColumn;

    @FXML
    private TableColumn<ResourceTableModel , String> idColumn;

    @FXML
    private TableView<ResourceTableModel> ResourceTable;

    @FXML
    private TextField FileNameTxt;

    @FXML
    private Label chooseFileBTN;

    @FXML
    private TextField pathTxt;

    @FXML
    private Label uploadBTN;

    @FXML
    private Label CoverPathLabel;

    @FXML
    private ProgressBar UploadStatus;

    @FXML
    private Label ErrLabel;

    FileChooser chooser = new FileChooser();

    FileChooser imgchooser = new FileChooser();

    File selectedFile = null;

    File fileCoverImg = null;

    String extension = null;

    String imgextension = null;

    @FXML
    private ComboBox<String> resource_type_combo;

    // Resources resource;

    ResourceTableModel resource;

    ObservableList<ResourceTableModel> ResourceList = FXCollections.observableArrayList();

    private Connection conn;

    public ResourceUploadHandler() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }

    @FXML
    private void refreshTable() throws SQLException {


        ResourceList.clear();

        String query = "select File_ID, File_Name, File_Type, File_Extension from Resources;";

        PreparedStatement seeResources = conn.prepareStatement(query);

        ResultSet results = seeResources.executeQuery();

        while (results.next()) {

            ResourceList.add(new ResourceTableModel(
                    results.getInt("File_ID"),
                    results.getString("File_Name"),
                    results.getString("File_Type"),
                    results.getString("File_Extension")
            ));

            ResourceTable.setItems(ResourceList);


        }






    }

    @FXML
    void chooseFile(MouseEvent event) {


        // FileChooser saveFile = new FileChooser();
        selectedFile = chooser.showOpenDialog(null);

        if (selectedFile != null) {
            pathTxt.setText(selectedFile.getName());
            pathTxt.setEditable(false);
            String name = selectedFile.getName();
            int lastindex = pathTxt.getText().lastIndexOf(".");
            extension = null;

            if (lastindex > 0) {

                extension = name.substring(lastindex + 1);

            }

        }

    }




    Callback<TableColumn<ResourceTableModel, String>, TableCell<ResourceTableModel, String>> cellFactory = (TableColumn<ResourceTableModel, String> param) -> {

        final TableCell<ResourceTableModel, String> cell = new TableCell<ResourceTableModel, String>() {


            @Override
            public void updateItem(String Item, boolean Empty ) {

                super.updateItem(Item, Empty);

                if (Empty) {
                    setGraphic(null);
                    setText(null);
                }else{

                    Button deleteBTN = new Button("Delete");
                    Button editBTN = new Button("Edit");
                    Button updateBTN = new Button("Update");



                    // FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                    // FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                    // deleteIcon.setStyle(
                    //             " -fx-cursor: hand ;"
                    //             // + "-glyph-size:28px;"
                    //             + "-fx-fill:#ff1744;"
                    //     );
                    //     editIcon.setStyle(
                    //             " -fx-cursor: hand ;"
                    //             // + "-glyph-size:28px;"
                    //             + "-fx-fill:#00E676;"
                    //     );


                    deleteBTN.setOnMouseClicked((MouseEvent event) -> {

                        try {
                            resource = ResourceTable.getSelectionModel().getSelectedItem();
                            String deletionQuery = "DELETE FROM Resources WHERE File_ID  = "+resource.getFile_ID()+";";
                            PreparedStatement preparedStatement = conn.prepareStatement(deletionQuery);
                            preparedStatement.execute();
                            refreshTable();
                            pathTxt.clear();
                            UploadStatus.setVisible(false);
                            ErrLabel.setText("The resource selected has been deleted");
                            ErrLabel.setVisible(true);


                        } catch (SQLException ex) {
                            System.err.println(ex);
                        }


                    });


                    editBTN.setOnMouseClicked((MouseEvent event) -> {

                        try {
                            resource = ResourceTable.getSelectionModel().getSelectedItem();
                            String selectionQuery = "SELECT * FROM Resources WHERE File_ID = ?;";
                            PreparedStatement preparedStatement = conn.prepareStatement(selectionQuery);
                            preparedStatement.setInt(1, resource.getFile_ID());
                            preparedStatement.execute();
                            refreshTable();

                            FileNameTxt.setText(resource.getFile_Name());
//                            resource_type_combo.setValue(resource.getFile_Type());
                            pathTxt.clear();
                            UploadStatus.setVisible(false);
                            ErrLabel.setVisible(false);

                        } catch (SQLException ex) {
                            System.err.println(ex);
                        }

                    });

                    updateBTN.setOnMouseClicked((MouseEvent event) -> {

                        try {
                            resource = ResourceTable.getSelectionModel().getSelectedItem();
                            String updateQuery = "UPDATE Resources SET File_Name = '"+FileNameTxt.getText()+"', File_Type = '"+resource_type_combo.getValue()+"' WHERE File_ID = "+resource.getFile_ID()+";";
                            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);
                            preparedStatement.execute();
                            refreshTable();

                            // FileNameTxt.setText(resource.getFile_Name());
                            // ResourceType.setText(resource.getFile_Type());
                            // ExtensionTxt.setText(resource.getFile_Extension());
                            pathTxt.clear();
                            pathTxt.setEditable(false);
                            UploadStatus.setVisible(true);
                            ErrLabel.setText("Your update has been synchronized.");
                            ErrLabel.setVisible(true);



                        } catch (SQLException ex) {
                            System.err.println(ex);
                        }


                    });




                    HBox managebtn = new HBox(editBTN, deleteBTN, updateBTN);
                    managebtn.setStyle("-fx-alignment:center");
                    HBox.setMargin(editBTN, new Insets(2, 2, 0, 3));
                    HBox.setMargin(deleteBTN, new Insets(2, 3, 0, 2));
                    HBox.setMargin(updateBTN, new Insets(2, 3, 0, 2));

                    setGraphic(managebtn);

                    setText(null);

                }




            }
        };

        return cell;



    };





    @Override
    public void initialize(URL location, ResourceBundle resources) {




        try {

            resource_type_combo.setItems(
                    FXCollections.observableArrayList(
                            "Images",
                            "Videos",
                            "Audio",
                            "Documents",
                            "E-books",
                            "Presentations",
                            "Software",
                            "Games",
                            "Webinars",
                            "Tutorials",
                            "Templates",
                            "Fonts",
                            "Icons",
                            "Clipart",
                            "Stock Photos",
                            "Infographics",
                            "Animations",
                            "Plugins",
                            "Scripts",
                            "Code Snippets"
                    ).sorted()
            );
            UploadStatus.setVisible(false);
            uploadCoverBTN.setVisible(false);
            CoverPathLabel.setVisible(false);
            chooseCover.setVisible(false);

            refreshTable();
            loadTableData();


        } catch (Exception e) {
            System.err.println(e);
        }


    }



    private void loadTableData() throws SQLException {

        try {
            refreshTable();

            idColumn.setCellValueFactory(new PropertyValueFactory<>("File_ID"));
            FileNameColumn.setCellValueFactory(new PropertyValueFactory<>("File_Name"));
            FileTypeColumn.setCellValueFactory(new PropertyValueFactory<>("File_Type"));
            FileExtensionColumn.setCellValueFactory(new PropertyValueFactory<>("File_Extension"));
            OptionsCol.setCellFactory(cellFactory);
            ResourceTable.setItems(ResourceList);


        } catch (Exception e) {
            System.err.println(e);
        }



    }

    @FXML
    void uploadFile(MouseEvent event) throws SQLException, IOException, InterruptedException {

        String File_Name = FileNameTxt.getText();
        String File_Type = resource_type_combo.getValue().toString();
        String Extension = extension;


        if(File_Name == null || Extension == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the required fields");
            alert.showAndWait();

        }

        FileInputStream fis = new FileInputStream(selectedFile);
        // java.time.Duration timeToComplete = Duration.ofSeconds(10);


        String Insert_File = "insert into Resources (File_Name, File_Type, File_Extension, File_Blob) values ('"+File_Name+"', '"+File_Type+"', '"+Extension+"',?);";

        // String uploadFile = "`UPDATE Files SET Content = '?', WHERE Filename = "+fileName+";";
        PreparedStatement insertfile = conn.prepareStatement(Insert_File);
        // PreparedStatement updatefile = conn.prepareStatement(uploadFile);


        // String selectImgBlob = "select Content from Files where Filename = 'TutorialPytube.mp4'";
        // PreparedStatement selectImage = conn.prepareStatement(selectImgBlob);

        if (selectedFile != null) {

            // resource = new Resources();
            // resource.setFilename(File_Name);
            // // resource.setFileID();
            // resource.setFileExtension(Extension);
            // resource.setFileType(File_Type);

            // List<Resources> resources;

            // resources = new ArrayList<>();


            insertfile.setBinaryStream(1, fis);
            insertfile.execute();
            ErrLabel.setText("Resource uploaded Succesfully...");

            //This prints the model values for testing. 


            // System.out.println(resource.getFilename());


            refreshTable();


            // for (int i = 0; i < 10; i++) {

            //     // Thread.sleep(2000);
            //     // UploadStatus.
            //     // UploadStatus.progressProperty();

            // }

        }else{

            ErrLabel.setText("No File Selected");

        }

        if (ErrLabel.getText() == "Resource uploaded Succesfully...") {
            uploadCoverBTN.setVisible(true);
            CoverPathLabel.setVisible(true);
            chooseCover.setVisible(true);

            FileNameTxt.setEditable(false);
            FileNameTxt.setEditable(false);

        }

        fis.close();

        UploadStatus.setVisible(true);

//        wait(5000);

        UploadStatus.setVisible(false);



    }

    @FXML
    void chooseCoverImg(MouseEvent event) {


        fileCoverImg = imgchooser.showOpenDialog(null);

        if (fileCoverImg != null) {

            pathTxt.setText(fileCoverImg.getName());
            pathTxt.setEditable(false);
            String name = fileCoverImg.getName();
            int lastindex = name.lastIndexOf(".");
            if (lastindex > 0) {

                imgextension = name.substring(lastindex+1);

            }

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No file selected");
            alert.showAndWait();
        }


    }


    @FXML
    void uploadCover(MouseEvent event) throws IOException, SQLException {


        FileInputStream fis = new FileInputStream(fileCoverImg);
        // java.time.Duration timeToComplete = Duration.ofSeconds(10);



        String Insert_File = "UPDATE Resources SET ResourceCoverImg = ?, ResourceCoverImgExtension = '"+imgextension+"' WHERE File_Name = '"+FileNameTxt.getText()+"';";



        PreparedStatement insertfile = conn.prepareStatement(Insert_File);

        UploadStatus.setVisible(true);

        if (fileCoverImg != null) {



            insertfile.setBinaryStream(1, fis);
            insertfile.execute();
            ErrLabel.setText("Uploaded Cover Succesfully...");






            //This prints the model values for testing. 

            // System.out.println(resource.getFilename());


            refreshTable();


            // for (int i = 0; i < 10; i++) {

            //     // Thread.sleep(2000);
            //     // UploadStatus.
            //     // UploadStatus.progressProperty();

            // }

        }else{

            ErrLabel.setText("No File Selected");

        }



        fis.close();




    }

}
    
    


