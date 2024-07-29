package handlers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Connection.DBConnection;
import Model.AdminUserModel;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

//Controller for HomePage

public class AdminHomeHandle implements Initializable {
   


    @FXML
    private Label adminNameLabel;

    @FXML
    private Scene scene2;
    
    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @FXML
    private Label HomePageButton;

    @FXML
    private VBox LeftMenuBar;

    @FXML
    private Label requestPage;

    @FXML
    private ImageView menu_button;

    @FXML
    private ImageView menu_button_close;

    @FXML
    private Label signoutButton;

    @FXML
    private Label settingsButton;

    @FXML
    private AnchorPane TopMenuBar;

    @FXML
    private BorderPane borderView;

    @FXML
    private Label bookkeepingbtn;

    @FXML
    private Label InboxBTN;

    @FXML
    private Label digital_res_btn;

    @FXML
    private ImageView profile_pic;

    String admin_ID;

    Connection conn;

    public AdminHomeHandle() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();
        
    }

    @FXML
    void requestPage(MouseEvent event) {

        try {
          
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/AdminFXMLPages/ApproveDenyRequests.fxml"));
            AnchorPane view = loader.load();
            borderView.setCenter(view);
            ApproveDenyRequestHandler handler = loader.getController();
            handler.setAdminID(admin_ID);

        
        } catch (Exception e) {
           System.err.println(e);
        }
       

    }



//    @FXML
//    void LiveChatBtn(MouseEvent event) {
//
//        try {
//
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/BackupAdmin/LiveChat.fxml"));
//            AnchorPane view = loader.load();
//            LiveChatHandler handler = loader.getController();
//            handler.setAdminUser(admin_ID);
//            borderView.setCenter(view);
//
//
//        } catch (Exception e) {
//           System.err.println(e);
//        }
//
//    }
//


    @FXML
    void ReportsBtn(MouseEvent event) {


        try {
          
            AnchorPane view = FXMLLoader.load(getClass().getResource("/BackupAdmin/Reports.fxml"));
            borderView.setCenter(view);

        
        } catch (Exception e) {
           System.err.println(e);
        }
       
  
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    
        try {
          

            menu_button_close.setVisible(false);

            setProfilePic();

            setMenuTransition();

            createMenu();

            AnchorPane view = FXMLLoader.load(getClass().getResource("/BackupAdmin/HomeDashboard.fxml"));
            borderView.setCenter(view);
            

        
        } catch (Exception e) {
           System.err.println(e);
        }
       
  
    }

    public void setMenuTransition(){

        menu_button.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.5));
            slide.setNode(LeftMenuBar);

            slide.setToX(-300);
            slide.play();

            slide.setOnFinished(ActionEvent -> {
                menu_button_close.setVisible(true);
                menu_button.setVisible(false);
            });
        });

        menu_button_close.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.5));
            slide.setNode(LeftMenuBar);

            slide.setToX(0);
            slide.play();

            slide.setOnFinished(ActionEvent -> {
                menu_button_close.setVisible(false);
                menu_button.setVisible(true);
            });
        });

    }

    @FXML
    void InboxBtn(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/AdminFXMLPages/LibraryMail.fxml"));
        BorderPane view1 = loader.load();
        MessageSectionHandler msgHandler = loader.getController();
        msgHandler.set_admin_id(Integer.parseInt(admin_ID));
        borderView.setCenter(view1);
        
    }



    @FXML
    void signout(MouseEvent event) throws IOException {

    try {

        PreparedStatement ps = conn.prepareStatement("UPDATE ADMINUSERS SET [status] = 0 WHERE Employee_ID = ?;");
        ps.setInt(1, Integer.parseInt(admin_ID));
        ps.executeUpdate();

        Parent root = FXMLLoader.load(getClass().getResource("/AdminFXMLPages/LoginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =  new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Home");
    } catch (Exception e) {
       System.err.println(e);
    }

    
    }

    @FXML
    void btnHomeClicked(MouseEvent event) throws IOException {

        // Parent root = FXMLLoader.load(getClass().getResource("/resources/Home.fxml"));
        // stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        // scene =  new Scene(root);
        // stage.setScene(scene);
        // stage.setTitle("Home");;

        AnchorPane view = FXMLLoader.load(getClass().getResource("/BackupAdmin/HomeDashboard.fxml"));
        borderView.setCenter(view);

     }

    @FXML
    void btnBookkeeping(MouseEvent event) throws IOException {


        AnchorPane view1 = FXMLLoader.load(getClass().getResource("/AdminFXMLPages/Bookkeeping.fxml"));
        borderView.setCenter(view1);
    }


    @FXML
    void DigitalResourcesbtn(MouseEvent event) throws IOException {


        AnchorPane view1 = FXMLLoader.load(getClass().getResource("/AdminFXMLPages/DigitalTest.fxml"));
        borderView.setCenter(view1);
        
    }

    public void setAdminContext(String name, String adminID) throws SQLException{

        AdminUserModel.getInstance().setName(name);
        AdminUserModel.getInstance().setAdminID(adminID);
        adminNameLabel.setText("Welcome, " + AdminUserModel.getInstance().getName());
        admin_ID = AdminUserModel.getInstance().getAdminID();

    }


    @FXML
    void settingsClicked(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/AdminFXMLPages/AdminSettings.fxml"));
        AnchorPane view1 = loader.load();
        borderView.setCenter(view1);

        AdminSettings details = loader.getController();
        details.setAdminID(admin_ID);


    }

    
    public void createMenu(){
        Menu menu = new Menu();

        ImageView home_icon = new ImageView(new Image(getClass().getResourceAsStream("/Icons/icons8-home-48.png"))); 
        home_icon.setFitWidth(40);
        home_icon.setFitHeight(40); 
        ImageView bookkeeping_icon = new ImageView(new Image(getClass().getResourceAsStream("/Icons/icons8-refer-to-manual-48.png"))); 
        bookkeeping_icon.setFitWidth(40);
        bookkeeping_icon.setFitHeight(40);
        ImageView digital_resources_icon = new ImageView(new Image(getClass().getResourceAsStream("/Icons/icons8-content-48.png"))); 
        digital_resources_icon.setFitWidth(40);
        digital_resources_icon.setFitHeight(40);
        ImageView request_icon = new ImageView(new Image(getClass().getResourceAsStream("/Icons/icons8-request-64.png"))); 
        request_icon.setFitWidth(40);
        request_icon.setFitHeight(40);
        ImageView inbox_icon = new ImageView(new Image(getClass().getResourceAsStream("/Icons/icons8-inbox-64.png"))); 
        inbox_icon.setFitWidth(40);
        inbox_icon.setFitHeight(40);
        ImageView reports_icon = new ImageView(new Image(getClass().getResourceAsStream("/Icons/icons8-report-48.png"))); 
        reports_icon.setFitWidth(40);
        reports_icon.setFitHeight(40);
        ImageView contact_us_icon = new ImageView(new Image(getClass().getResourceAsStream("/Icons/icons8-contact-us-48.png"))); 
        contact_us_icon.setFitWidth(40);
        contact_us_icon.setFitHeight(40);
        ImageView live_chat = new ImageView(new Image(getClass().getResourceAsStream("/Icons/icons8-live-chat-64.png"))); 
        live_chat.setFitWidth(40);
        live_chat.setFitHeight(40);

        // Create a StackPane to overlay the label and the icon
        HBox home = new HBox();
        home.getChildren().addAll(HomePageButton, home_icon);
        home.setAlignment(Pos.CENTER);
        HBox bookkeep = new HBox();
        bookkeep.getChildren().addAll(bookkeepingbtn, bookkeeping_icon);
        bookkeep.setAlignment(Pos.CENTER);
        HBox resource = new HBox();
        resource.getChildren().addAll(digital_res_btn, digital_resources_icon);
        resource.setAlignment(Pos.CENTER);
        HBox request = new HBox();
        request.getChildren().addAll(requestPage, request_icon);
        request.setAlignment(Pos.CENTER);
        HBox inbox = new HBox();
        inbox.getChildren().addAll(InboxBTN, inbox_icon);
        inbox.setAlignment(Pos.CENTER);

//        HBox reports = new HBox();
//        reports.getChildren().addAll(ReportBTN, reports_icon);
//        reports.setAlignment(Pos.CENTER);
//        // HBox contact = new HBox();
//        // contact.getChildren().addAll(ContactUsBTN, contact_us_icon);
//        // contact.setAlignment(Pos.CENTER);
//        HBox live_chat_box = new HBox();
//        live_chat_box.getChildren().addAll(LiveChatBTN, live_chat);
//        live_chat_box.setAlignment(Pos.CENTER);

        LeftMenuBar.getChildren().add(home);
        LeftMenuBar.getChildren().add(bookkeep);
        LeftMenuBar.getChildren().add(resource);
        LeftMenuBar.getChildren().add(request);
        LeftMenuBar.getChildren().add(inbox);
//        LeftMenuBar.getChildren().add(reports);
//        // LeftMenuBar.getChildren().add(contact);
//        LeftMenuBar.getChildren().add(live_chat_box);

        menu.setGraphic(LeftMenuBar);

        // Create a menu bar and add the menu to it
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
    }

    public void setProfilePic() throws SQLException{
        
        InputStream is;

        String query = "SELECT (profile_pic) FROM ADMINUSERS WHERE Employee_ID = "+AdminUserModel.getInstance().getAdminID()+";";

        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            is = rs.getBinaryStream("profile_pic");
    
            if (is == null) {
                return;
            }else{

                profile_pic.setImage(new Image(is));

            }
          
        }
    }
}
