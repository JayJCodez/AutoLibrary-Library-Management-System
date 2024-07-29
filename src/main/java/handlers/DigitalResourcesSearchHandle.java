package handlers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import Connection.DBConnection;
import Model.ResourceCardModel;
import Model.ResourceSavedCardModel;
import Model.UserContextModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class DigitalResourcesSearchHandle implements Initializable {



    @FXML
    private GridPane grid;

    @FXML
    private Label savedResourcePage;

    @FXML
    private Label all_resources;


    ObservableList<ResourceCardModel> ResourceList = FXCollections.observableArrayList();

    ObservableList<ResourceSavedCardModel> ResourceSavedList = FXCollections.observableArrayList();

    DirectoryChooser dc = new DirectoryChooser();

    File selectedDir;

    String path;

    Connection conn;

    ResourceCardModel res;
    
    int userid;

      public DigitalResourcesSearchHandle() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

          all_resources.setVisible(false);
        try {
            allResources();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void allResources() throws SQLException, IOException {



          list().clear();
        // Clear the grid before adding new items
        grid.getChildren().clear();

        try {
            ResourceList = list();
        } catch (SQLException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int column = 0;
        int row = 1;


        try {




            for(ResourceCardModel res : ResourceList){

                FXMLLoader fxmlLoader1 = new FXMLLoader();
                fxmlLoader1.setLocation(getClass().getResource("/AdminFXMLPages/ResourceCard.fxml"));

                AnchorPane ResourceContainer = fxmlLoader1.load();
                ResourceCardHandler handler = fxmlLoader1.getController();
                handler.setData(res);

                while (column == 0) {
                    column++;
                }

                if (column == 4) {

                    column = 0;
                    ++row;
                    ++column;

                }

                // Retrieve the label from the book and set event handler
                Label label = res.getViewbtn();
                label.setOnMouseClicked((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        // Handle label click event here

                        try {
                            openNewPage(res.getResource_name(), res.getResource_type(), res.getResource_ext(),res.getRes_id(), res.getImg());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }



                    }
                });



                Label saveLabel = res.getSavebtn();
                if (userid == 0){
                    saveLabel.setVisible(false);
                    saveLabel.setDisable(true);
                }else {
                    saveLabel.setVisible(true);
                }
                saveLabel.setOnMouseClicked((EventHandler<? super MouseEvent>)new EventHandler<Event>() {

                    @Override
                    public void handle(Event arg0) {


                        String check_if_saved = "SELECT * FROM SavedResources WHERE user_id=" + userid + " AND resource_id=" + res.getRes_id() + ";";
                        String query = "INSERT INTO SavedResources (user_id, resource_id) values ("+userid+", "+res.getRes_id()+");";

                        PreparedStatement ps = null;

                        try {
                            ps = conn.prepareStatement(query);
                            PreparedStatement ps_check_if_saved = conn.prepareStatement(check_if_saved);
                            ResultSet rs = ps_check_if_saved.executeQuery();

                            if(rs.next()){

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Warning");
                                alert.setHeaderText(null);
                                alert.setContentText("Resource already saved");
                                alert.showAndWait();

                            }else if(!rs.next()){

                                ps.execute();

                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    }

                });
                // if (column > 3) {

                //     column = 0;
                //     ++row;

                // }


                // Button btn = new Button();
                grid.add(ResourceContainer, column++, row);
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

    private void openNewPage(String res_name, String res_type, String res_ext, int resid, Image img) throws Exception {
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/AdminFXMLPages/ResourceExpanded.fxml"));
        Parent root = loader.load(); // Replace "NewPage.fxml" with your FXML file
        
        
        ResourceExpanded res_expanded = loader.getController();

        res_expanded.setData(res_name, res_type, res_ext, img, userid, resid);
        res_expanded.checkCurrentReq(resid);

       

        
        newStage.setTitle("Resource Details");
        newStage.setScene(new Scene(root, 431, 293));
        newStage.show();

    }

    public void setUserID(int user_id) throws SQLException, IOException {


        userid = user_id;
        allResources();


    }
   
    // @FXML
    // private void refreshTable() throws SQLException, IOException {


    //     ResourceList.clear();

    //     String query = "select * from Resources;";

        
    //     PreparedStatement seeResources = conn.prepareStatement(query);

    //     ResultSet results = seeResources.executeQuery();

    //     while (results.next()) {

          

          
    //         // Blob ImageBlob = results.getBlob("File_Blob");
    //         // byte bt[] = ImageBlob.getBytes(1,(int)ImageBlob.length());
    //         // Image img = Tool.getDefaultTooklit().createImage(bt);

    //         ResourceList.add(new ResourceSearch(
    //             results.getString("File_Name"),
    //             results.getString("File_Type"),
    //             results.getBinaryStream("File_Blob"),
    //             results.getString("File_Extension")
            
    //         ));

    //         SearchTBL.setItems(ResourceList);

            
    //     }

    



    // }
    

  
    



// @FXML
//     void search(ActionEvent event) throws SQLException {

//         String searchTxt = SearchTxt.getText();
//         String search = "SELECT * FROM Test.Resources WHERE File_Name = '"+searchTxt+"' OR File_Type = '"+searchTxt+"' LIKE CONCAT('%', File_Name, '%');";
      
//          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Test", "root", "138Jesse.");
//         PreparedStatement searchData = conn.prepareStatement(search);
//         ResultSet query = searchData.executeQuery();

//         ResourceList.clear();

//               while (query.next()) {

//             ResourceList.add(new ResourceSearch(
//                 query.getString("File_Name"),
//                 query.getString("File_Type"),
//                 query.getBinaryStream("ResourceCoverImg"),
//                 query.getString("File_Extension")


//             ));

            
//             SearchTBL.setItems(ResourceList);

            
//         }

//         conn.close();
//     }

   

    @FXML
    void chooseDirectory(MouseEvent event) {


        Stage stage = new Stage();
        selectedDir = dc.showDialog(stage);
      
       
      

        if (dc != null) {
            
            dc.getInitialDirectory();

            path = selectedDir.getAbsolutePath();


            // pathTxt.setText(fileCoverImg.getName());
            // pathTxt.setEditable(false);
            System.out.println(path);
        
        }
     


    }

    // @FXML
    // void downloadResource(MouseEvent event) throws SQLException, IOException {


    //     resource = SearchTBL.getSelectionModel().getSelectedItem();

    //     String downloadQuery = "select * from Resources where File_Name = '"+resource.getName()+"';";
    //      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Test", "root", "138Jesse.");
        
    //     PreparedStatement preparedStatement = conn.prepareStatement(downloadQuery);
    //     ResultSet rs = preparedStatement.executeQuery();

    //     if (rs.next()) {
            
         
                
    //        InputStream dwnload =  rs.getBinaryStream("File_Blob");
    //        OutputStream downloadStream = new FileOutputStream(new File(""+path+"/"+resource.getName()+""+resource.getExtension()+""));


    //        byte[] content = new byte[1024];
            
    //        int size = 0;
    //        while ((size = dwnload.read(content)) != -1) {
    //                downloadStream.write(content, 0, size);
    //        }

         
    //        dwnload.close();
    //        downloadStream.close();


    //     //    Image image = new Image("file:Catalogue."+resource.getExtension()+"", 365, 356, true, true);


    //     }


    //     // preparedStatement.execute();

    //     conn.close();
    // }


    public void displaySavedItems(){

        // Clear the grid before adding new items
        grid.getChildren().clear();

        try {
            saved_res_list().clear();
            ResourceSavedList = saved_res_list();
        } catch (SQLException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int column = 0;
        int row = 1;


        try {




            for(ResourceSavedCardModel res : ResourceSavedList){

                FXMLLoader fxmlLoader1 = new FXMLLoader();
                fxmlLoader1.setLocation(getClass().getResource("/AdminFXMLPages/ResourceSavedCard.fxml"));

                AnchorPane ResourceContainer = fxmlLoader1.load();
                ResourceSavedCardHandler handler = fxmlLoader1.getController();
                handler.setData(res);

                while (column == 0) {
                    column++;
                }

                if (column == 4) {

                    column = 0;
                    ++row;
                    ++column;

                }

                // Retrieve the label from the book and set event handler
                Label label = res.getViewbtn();
                label.setOnMouseClicked((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        // Handle label click event here

                        try {
                            openNewPage(res.getResource_name(), res.getResource_type(), res.getResource_ext(),res.getRes_id(), res.getImg());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }



                    }
                });



                ImageView delete = res.getDeletebtn();
                delete.setOnMouseClicked((EventHandler<? super MouseEvent>)new EventHandler<Event>() {

                    @Override
                    public void handle(Event arg0) {


                        try {

                            deleteSavedResource(res);

                            grid.getChildren().clear();

                            displaySavedItems();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }


                    }

                });
                // if (column > 3) {

                //     column = 0;
                //     ++row;

                // }


                // Button btn = new Button();
                grid.add(ResourceContainer, column++, row);
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

    @FXML
    void goSavedResource(MouseEvent event) {
        this.all_resources.setVisible(true);
        this.savedResourcePage.setVisible(false);
        this.displaySavedItems();
    }

    @FXML
    void goAllResource(MouseEvent event) throws SQLException, IOException {
        this.all_resources.setVisible(false);
        this.savedResourcePage.setVisible(true);
        this.allResources();
    }

    public ObservableList<ResourceSavedCardModel> saved_res_list() throws SQLException, IOException {

          this.ResourceList.clear();
        HashSet<Integer> seenIds = new HashSet();
        String query = "SELECT R.File_ID, R.File_Name, R.File_Type,R.File_Extension, R.ResourceCoverImg, S.saveID, S.user_id FROM Resources AS R INNER JOIN SavedResources AS S ON R.File_ID = S.resource_id WHERE S.user_id = ?";
        PreparedStatement ps = this.conn.prepareStatement(query);
        ps.setInt(1, this.userid);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int fileId = rs.getInt("File_ID");
            if (!seenIds.contains(fileId)) {
                InputStream stream = rs.getBinaryStream("ResourceCoverImg");
                Image image = new Image(stream);
                Label viewbutton = new Label();
//                Label savebtn = new Label();
                ImageView img = new ImageView();
                img.setImage(new Image("Icons/icons8-close-window-48.png"));
                ResourceSavedList.add(new ResourceSavedCardModel(
                        image,
                        rs.getString("File_Name"),
                        rs.getString("File_Type"),
                        rs.getString("File_Extension"),
                        fileId,
                        viewbutton,
                        img
                        ));

                seenIds.add(fileId);
                stream.close();
            }
        }

        return this.ResourceSavedList;
    }

    public ObservableList<ResourceCardModel> list() throws SQLException, IOException{
        
        
        String query = "SELECT File_Name, File_Type, File_Extension, File_ID, ResourceCoverImg FROM Resources;";

        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

      
        while (rs.next()) {

            InputStream stream = rs.getBinaryStream("ResourceCoverImg");
            Image image =   new Image(stream);
           
            Label viewbutton = new Label();
            Label savebtn = new Label();
            
            


            ResourceList.add(new ResourceCardModel(


                image,
                rs.getString("File_Name"),
                rs.getString("File_Type"),
                rs.getString("File_Extension"),
                rs.getInt("File_ID"),
                savebtn,
                viewbutton



            ));

        stream.close();
          

       }

        
        
        return ResourceList;



    }


    private void deleteSavedResource(ResourceSavedCardModel res) throws SQLException {


          String query = "DELETE FROM SavedResources WHERE user_id = "+userid+" AND resource_id = "+res.getRes_id()+";";

          PreparedStatement ps = conn.prepareStatement(query);

          System.out.println(UserContextModel.getInstance().getUserid());

//          ps.setInt(1, UserContextModel.getInstance().getUserid());
//          ps.setInt(2, res.getRes_id());

          ps.execute();

    }

    private static final long REFRESH_INTERVAL_MS = 5000; // 5 seconds
    private Timer timer;


    public void startRefreshing() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new RefreshTask(), 0, REFRESH_INTERVAL_MS);
    }

    private static class RefreshTask extends TimerTask {
        @Override
        public void run() {
            // Code to fetch updated data from the source

        }
    }

    public void stopRefreshing() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
