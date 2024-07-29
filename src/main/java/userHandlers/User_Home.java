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
import Model.UserContextModel;
import handlers.DigitalResourcesSearchHandle;


public class User_Home implements Initializable {

    //Referencing all FXML GUI Objects

    Stage stage;

    Scene scene;

    private String name;

    String balance;

    private boolean state = false;

    //Setters and Getters for class Global variables. Unused currently due to the availability of UserContext Model holding same value expected to be retrieved here.


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @FXML
    private ImageView user_profile_pic;

    @FXML
    private Label AddCreditLabel;

    @FXML
    private Label BalanceLabel;

    @FXML
    private AnchorPane Border;

    @FXML
    private Label bookeeping_button;

    @FXML
    private Label ContactUsBTN;

    @FXML
    private Label HomePageButton;

    @FXML
    private Label NameLabel;

    @FXML
    private Label settingsBTN;

    @FXML
    private ImageView menu_button;

    @FXML
    private VBox LeftMenuBar;

    @FXML
    private ImageView Logo;

    @FXML
    private Label digitalresources_button;

    @FXML
    private Label signOutBTN;

    @FXML
    private AnchorPane TopMenuBar;

    @FXML
    private BorderPane borderView;

    Connection conn;

    int userid;

    public User_Home() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }

    @FXML
    void ContactUsBtn(MouseEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/AdminFXMLPages/ContactUs.fxml"));
        AnchorPane view = loader.load();
        borderView.setCenter(view);
    }

    @FXML
    void DigitalResourcesbtn(MouseEvent event) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/AdminFXMLPages/DigitalResourcesSearch.fxml"));
        AnchorPane view = loader.load();
        borderView.setCenter(view);

        DigitalResourcesSearchHandle res = loader.getController();

        res.setUserID(userid);

        System.out.println(userid);

    }

    // This function loads the Top Up page with the help of our FXML Loader seen below.
    // Functions such as setter and getter methods are called from an instance of the Top Up page controller.
    // The Top Up page controller is used to access and handle the functions called in the User_TopUp class file.

    @FXML
    void TopUpPage(MouseEvent event) throws IOException, SQLException {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/userFXMLpages/User_TopUpPage.fxml"));
        AnchorPane view = loader.load();
        borderView.setCenter(view);

        User_TopUp topup = loader.getController();

        topup.setName(UserContextModel.getInstance().getName());
        topup.setCurrentBalance(balance);
        topup.setUserId(userid);
        topup.loadTableData();




    }

    //This function starts up the book keeping page which is actually a view and not a whole stage

    @FXML
    void btnBookkeeping(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/userFXMLpages/User_Bookkppeing.fxml"));
        AnchorPane view = loader.load();
        borderView.setCenter(view);

        User_Bookkeeping book = loader.getController();
        book.setUser(UserContextModel.getInstance().getUsername());



    }

    //This function redirects the user back to the the user home dashboard on click of the label I have set to function as a button.

    @FXML
    void btnHomeClicked(MouseEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/userFXMLpages/User_HomeDashboard.fxml"));
            AnchorPane view = loader.load();
            borderView.setCenter(view);

            User_HomeDashboard dash = loader.getController();
            dash.setUsername(UserContextModel.getInstance().getUsername());



        } catch (Exception e) {
            System.err.println(e);
        }


    }

    //This function desccribes the event of the logout button label.
    //Main function is just to redirect to login page.

    @FXML
    void signOut(MouseEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/AdminFXMLPages/LoginPage.fxml"));
            Parent root = loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene =  new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Home");
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    //This is the initializer class which is ran on initialization of the User_Home page.

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        try {

            AnchorPane.setTopAnchor(Border, 0.0);
            AnchorPane.setBottomAnchor(Border, 0.0);
            AnchorPane.setLeftAnchor(Border, 0.0);
            AnchorPane.setRightAnchor(Border, 0.0);


            createMenu();


            // Here, I am calling on to class functions in this current class.

            // displayPage(name) is a function where I have set the the home dashboard to display on calling function.

            displayPage(name);



            //This function is currently not in use as the balance does not refresh while logged in.
            //That was the main aim of implemeting the function below.




            //Exceptions are caught so we don't end up with a long stack of errors in the background and avoid crashing of the program.
        } catch (Exception e) {
            System.err.println(e);
        }

    }


    // displayPage(name) is a function where I have set the the home dashboard to display on calling function.
    //Two Funtions are called inside this function but are called to act in the controller of the user home dashboard.
    public void displayPage(String name) throws IOException{


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/userFXMLpages/User_HomeDashboard.fxml"));
        AnchorPane view = loader.load();
        User_HomeDashboard homeDashboard =  loader.getController();
        homeDashboard.liveData(name);
        homeDashboard.refreshData(name);
        borderView.setCenter(view);



    }

    //This function is called in the LoginPageHandler starts function in this current class.
    //This will update out User model with current user details
    //it will also set specific Label to display User Name on sign in.

    public void displayUser(String name, String username) throws SQLException{


        UserContextModel.getInstance().setUsername(username);
        UserContextModel.getInstance().setName(name);
        NameLabel.setText("Welcome,   " + name);

        //I printed out this variable to test that I am recieveing the current user name in this class.
        //This is because the variable is actually set in the previous class and then passed on the value to this current class.
        System.out.println(UserContextModel.getInstance().getName());

        String query = "SELECT (profile_pic) FROM USERS WHERE Username = '"+UserContextModel.getInstance().getUsername()+"'";

        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            InputStream is = rs.getBinaryStream("profile_pic");
            user_profile_pic.setPreserveRatio(true);
            user_profile_pic.setImage(new Image(is));
        }
    }

    //This functions displays the balance of th current user on sign in 

    public void displayBalance(String balance){


        UserContextModel.getInstance().setBalance(balance);

        BalanceLabel.setText("£ " + balance + ".00");


    }

    //This fucntion as mentioned before is not currently playing any part of this program.
    //However this class function aims to update the user balance while logged in after Library credit is topped up. 

    public void refreshBalance(int newBalance) throws SQLException{

        BalanceLabel.setText("£ " + Integer.toString(newBalance) + ".00");
    }

    public void refreshedAddCredit(int newBalance) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/userFXMLpages/User_TopUpPage.fxml"));
        AnchorPane view = loader.load();
        borderView.setCenter(view);

        User_TopUp topup = loader.getController();
        topup.setCurrentBalance("£ " + newBalance + ".00");
        topup.setUserId(userid);
        topup.loadTableData();

    }

    @FXML
    void settingsClicked(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/userFXMLpages/Non-Admin Settings.fxml"));
        AnchorPane settingsDisplay = loader.load();
        borderView.setCenter(settingsDisplay);

        String username = UserContextModel.getInstance().getUsername();

        UserSettings user_Settings = loader.getController();
        user_Settings.setUsername(UserContextModel.getInstance().getUsername());

        System.out.println(username);

    }

    public void getID(String username) throws SQLException{

        String query = "SELECT User_ID FROM USERS WHERE Username = '"+username+"'";

        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            int id = rs.getInt("User_ID");

            userid = id;

            // System.out.println("USER ID OUTPUT" + userid);

        }


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
        bookkeep.getChildren().addAll(bookeeping_button, bookkeeping_icon);
        bookkeep.setAlignment(Pos.CENTER);
        HBox resource = new HBox();
        resource.getChildren().addAll(digitalresources_button, digital_resources_icon);
        resource.setAlignment(Pos.CENTER);
        HBox reports = new HBox();
//        reports.getChildren().addAll(ReportSBTN, reports_icon);
        reports.setAlignment(Pos.CENTER);
        HBox contact = new HBox();
        contact.getChildren().addAll(ContactUsBTN, contact_us_icon);
        contact.setAlignment(Pos.CENTER);
//        HBox live_chat_box = new HBox();
//        live_chat_box.getChildren().addAll(LiveChatBTN, live_chat);
//        live_chat_box.setAlignment(Pos.CENTER);

        LeftMenuBar.getChildren().add(home);
        LeftMenuBar.getChildren().add(bookkeep);
        LeftMenuBar.getChildren().add(resource);
//        LeftMenuBar.getChildren().add(reports);
        LeftMenuBar.getChildren().add(contact);
//        LeftMenuBar.getChildren().add(live_chat_box);

        menu.setGraphic(LeftMenuBar);

        // Create a menu bar and add the menu to it
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
    }


    @FXML
    void open_menu(MouseEvent event) {



        if(!state){


            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.5));
            slide.setNode(LeftMenuBar);

            slide.setToX(-300);
            slide.play();

            slide.setOnFinished(ActionEvent -> {

//                menu_button.setRotate(90);
            });

            state = true;

        }else{


            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.5));
            slide.setNode(LeftMenuBar);

            slide.setToX(0);
            slide.play();

            slide.setOnFinished(ActionEvent -> {

//                menu_button.setRotate(90);

            });

            state = false;

        }





    }

}




