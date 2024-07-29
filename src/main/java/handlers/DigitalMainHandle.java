package handlers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class DigitalMainHandle implements Initializable {
    
    @FXML
    private Label refreshTablebtn;

    @FXML
    private Label BackBTN;

      @FXML
    private Label ExploreBTN;

    @FXML
    private TextField SearchBar;

    @FXML 
    private BorderPane view;


    @FXML
    private Label uploadResourceBTN;

    

    @FXML
    void NewResourceClick(MouseEvent event) {


        try {
          
            AnchorPane view1 = FXMLLoader.load(getClass().getResource("/AdminFXMLPages/UploadResource.fxml"));
            view.setCenter(view1);
            BackBTN.setVisible(true);
            refreshTablebtn.setVisible(true);


        
        } catch (Exception e) {
           System.err.println(e);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       

        try {
          
//            AnchorPane view1 = FXMLLoader.load(getClass().getResource("/AdminFXMLPages/DigitalResourcesPage.fxml"));
//            view.setCenter(view1);
            BackBTN.setVisible(false);
            refreshTablebtn.setVisible(false);


        
        } catch (Exception e) {
           System.err.println(e);
        }
       
  
    }

     @FXML
    void SearchDigitalResources(MouseEvent event) throws IOException {

        AnchorPane view1 = FXMLLoader.load(getClass().getResource("/AdminFXMLPages/DigitalResourcesSearch.fxml"));
        view.setCenter(view1);
        BackBTN.setVisible(true);
        refreshTablebtn.setVisible(false);

        // ExploreBTN.setVisible(false);
        // SearchBar.setVisible(false);

    }
   
    @FXML
    void goBack(MouseEvent event) throws IOException {

        AnchorPane view1 = FXMLLoader.load(getClass().getResource("/AdminFXMLPages/DigitalResourcesPage.fxml"));
        view.setCenter(view1);

        ExploreBTN.setVisible(true);
        BackBTN.setVisible(false);
       refreshTablebtn.setVisible(false);

    }


    @FXML
    void refreshBTN(MouseEvent event) {

        
    }


}
