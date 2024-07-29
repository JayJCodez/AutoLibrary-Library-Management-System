package handlers;

import Alerts.NotificationHandler;
import Mailer.Mail;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import userHandlers.User_Home;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import javax.mail.MessagingException;
import javax.mail.Session;

import Connection.DBConnection;
import Model.UserContextModel;



public class LoginPageHandler implements Initializable {

    Session session;

    String email;

    public String Authotp;

    String first_name;

    public String user_name;

    public String name;

    public String user_balance;

    UserContextModel user;

    NotificationHandler notify = new NotificationHandler();

    @FXML
    private TextField PasswordTxt_visible;

    @FXML
    private TextField newPWtxt_visible;

    @FXML
    private HBox register_pw_container;

    @FXML
    private PasswordField PasswordTxt;

    @FXML
    private TextField UsernameTxt;

    @FXML
    private Label btnRegister;

    @FXML
    private Label btnSignIn;

    @FXML
    private Label codeconfirmedLBL;

    @FXML
    private Label confirmVerifyLBL;

    @FXML
    private Label emailLBL;

    @FXML
    private TextField emailTXTBox;

    @FXML
    private Label firstnameLBL;

    @FXML
    private TextField firstnameTXTBox;

    @FXML
    private Label lastnameLBL;

    @FXML
    private TextField lastnameTxt;

    @FXML
    private AnchorPane layer1;

    @FXML
    private AnchorPane layer2;

    @FXML
    private Label loginLBL;

    @FXML
    private Label loginPW_LBL;

    @FXML
    private Label loginlabeltxt;

    @FXML
    private Label newPWlabel;

    @FXML
    private PasswordField newPWtxt;

    @FXML
    private Label newUsernameLbl;

    @FXML
    private TextField newUsernameTXT;

    @FXML
    private Label registerERRlabel;

    @FXML
    private Label registerLBL;


    @FXML
    private TextField picurl_text;

    @FXML
    private Label upload_pic_btn;


    @FXML
    private Label uploadpiclabel;


    @FXML
    private Label registerTRANSbtn;

    @FXML
    private Label signinTRANSbtn;

    @FXML
    private TextField verificationcodetxtbox;

    @FXML
    private Label verifyBTN;

    @FXML
    private HBox loginpw_container;

    @FXML
    private ImageView showLoginPW;

    @FXML
    private ImageView showRegisterPW;

    @FXML
    private Label verifycodeBTN;

    private Connection conn;

    private File pic = null;

    private boolean state = true;

    private boolean state1 = true;

    public LoginPageHandler() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Here I am making visible and invisible the labels, textfields and buttons that only apply at certain parts of this stage.

        try {


            PasswordTxt.textProperty().addListener((observable, oldValue, newValue) -> {
                PasswordTxt_visible.setText(newValue);
            });


            newPWtxt.textProperty().addListener((observable, oldValue, newValue) -> {
                newPWtxt_visible.setText(newValue);
            });

            btnSignIn.toFront();


            registerTRANSbtn.setVisible(true);
            btnSignIn.setVisible(true);
            loginLBL.setVisible(true);
            loginPW_LBL.setVisible(true);
            loginlabeltxt.setVisible(true);
            PasswordTxt.setVisible(true);
            UsernameTxt.setVisible(true);


            PasswordTxt_visible.setVisible(false);
            newPWtxt_visible.setVisible(false);
            showRegisterPW.setVisible(false);
            picurl_text.setVisible(false);
            upload_pic_btn.setVisible(false);
            uploadpiclabel.setVisible(false);
            newUsernameLbl.setVisible(false);
            newUsernameTXT.setVisible(false);
            firstnameLBL.setVisible(false);
            lastnameLBL.setVisible(false);
            firstnameTXTBox.setVisible(false);
            emailTXTBox.setVisible(false);
            lastnameTxt.setVisible(false);
            verificationcodetxtbox.setVisible(false);
            verifyBTN.setVisible(false);
            verifycodeBTN.setVisible(false);
            registerLBL.setVisible(false);
            emailLBL.setVisible(false);
            btnRegister.setVisible(false);
            signinTRANSbtn.setVisible(false);
            confirmVerifyLBL.setVisible(false);
            codeconfirmedLBL.setVisible(false);
            newPWlabel.setVisible(false);
            newPWtxt.setVisible(false);
            registerERRlabel.setVisible(false);


        } catch (Exception e) {
            System.err.println(e);
        }


    }


    @FXML
    void show_login_password(MouseEvent event) {
        if (state) {
            showLoginPW.setImage(new Image("Icons/off-button.png"));
            PasswordTxt_visible.setVisible(true);
            state = false;
        } else {

            showLoginPW.setImage(new Image("Icons/toggle-button.png"));
            PasswordTxt_visible.setVisible(false);
            state = true;
        }
    }

    @FXML
    void show_register_password(MouseEvent event) {


        if(state1){

            state1 = false;
            showRegisterPW.setImage(new Image("Icons/off-button.png"));
            newPWtxt_visible.setVisible(true);

        }else {

            newPWtxt_visible.setVisible(false);
            showRegisterPW.setImage(new Image("Icons/toggle-button.png"));
            state1 = true;

        }

    }

    @FXML
    void choosepicture(MouseEvent event) {



        FileChooser choose_pic = new FileChooser();

        pic = choose_pic.showOpenDialog(null);

        if (pic == null) {
            System.err.println("You havent chosen anything");
        }else{
            picurl_text.setText(pic.getAbsolutePath());
        }

    }

    //This function handles the sign in process as a whole for both admin and non-admin users.

    @FXML
    public void SignIn(MouseEvent event) throws IOException, SQLException {




        //Handling Sign in Button event

        String username =  UsernameTxt.getText();
        String password =  PasswordTxt.getText();



        //Preparing sql query in string format so it is ready for execution.

        String findName = "select * from USERS where Email ='"+username+"' OR Username ='"+username+"' AND Password = '"+password+"';";
        String query = "SELECT * FROM ADMINUSERS WHERE Email = '"+username+"' AND Password = '"+password+"';";


        PreparedStatement st=conn.prepareStatement(findName);


        try {

            //Executing query to find out if the potential user is an admin or not.

            PreparedStatement check_user_type = conn.prepareStatement(query);

            ResultSet admin = check_user_type.executeQuery();

            // Finding First Name of the user logging into the system.

            ResultSet rs=st.executeQuery();


            if (rs.next()) {


                if (rs.getString("Password").matches(password)){


                    // Storing the name of the user in a String

                    String received = rs.getString("First_Name");

                    user_name = rs.getString("Username");

                    String Balance = Integer.toString(rs.getInt("Balance"));

                    name = received;

                    user_balance = Balance;


                    notify.displaySuccessNotification(name);


                    // displaySuccessNotification(name);


                    display_user_Page(event);



                    // System.out.println(name);


                    System.out.println("Logged in.....");

                    //Condition to redirect Admin Users to their seperate interface.


                }else{


                    new NotificationHandler().UnsuccessfulLogin();
                    System.out.println("Logged Unsuccessful.....");

                }


            }else if (admin.next()) {

                //Prepring the admin name for the function to set it as an instance in the Home page.
                if (admin.getString("Password").matches(password)){


                    String adminName = admin.getString("First_Name");
                    String adminID = Integer.toString(admin.getInt("Employee_ID"));

                    //Load notification to show succesful login

                    notify.displaySuccessNotification(adminName);
                    System.out.println("Logged in.....");

                    String update_status = "UPDATE ADMINUSERS SET [status] = 1 WHERE Employee_ID = ?;";

                    PreparedStatement ps = conn.prepareStatement(update_status);

                    ps.setInt(1, Integer.parseInt(adminID));

                    ps.executeUpdate();

                    // Load the view for the admin page

                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getClassLoader().getResource("AdminFXMLPages/Home.fxml"));
                    Parent root = loader.load();
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Home");
                    AdminHomeHandle adminUser = loader.getController();
                    adminUser.setAdminContext(adminName, adminID);

                }else{

                    new NotificationHandler().UnsuccessfulLogin();
                    System.out.println("Login Unsuccessful.....");

                }



            }
            else{

                // Handling general exception for wrong credentials
                System.out.println("Something Went Wrong");

                //Displaying notification making user aware of current error.


                notify.UnsuccessfulLogin();


            }



        } catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



    //This function handles the transition animation of the login page when the register Button is clicked.

    @FXML
    void RegisterTRANS(MouseEvent event) {

        TranslateTransition slide = new TranslateTransition();

        slide.setDuration(Duration.seconds(0.7));

        slide.setNode(layer1);

        slide.setToX(388);

        slide.play();

        layer2.setTranslateX(-282);

        firstnameLBL.setVisible(true);
        lastnameLBL.setVisible(true);
        firstnameTXTBox.setVisible(true);
        emailTXTBox.setVisible(true);
        lastnameTxt.setVisible(true);
        verificationcodetxtbox.setVisible(true);
        verifyBTN.setVisible(true);
        verifycodeBTN.setVisible(true);
        registerLBL.setVisible(true);
        emailLBL.setVisible(true);
        btnRegister.setVisible(false);
        signinTRANSbtn.setVisible(true);



        PasswordTxt_visible.setVisible(false);
        showLoginPW.setVisible(false);
        newUsernameLbl.setVisible(false);
        newUsernameTXT.setVisible(false);
        registerTRANSbtn.setVisible(false);
        btnSignIn.setVisible(false);
        loginLBL.setVisible(false);
        loginPW_LBL.setVisible(false);
        loginlabeltxt.setVisible(false);
        PasswordTxt.setVisible(false);
        UsernameTxt.setVisible(false);
        registerERRlabel.setVisible(false);
        picurl_text.setVisible(false);
        upload_pic_btn.setVisible(false);
        uploadpiclabel.setVisible(false);

        slide.setOnFinished((e->{



        }));


    }


    //This function handles the transition animation of the login page when the sign in 'Page' Button is clicked.

    @FXML
    void SignInTRANS(MouseEvent event) {

        btnSignIn.toFront();

        TranslateTransition slide = new TranslateTransition();

        slide.setDuration(Duration.seconds(0.7));

        slide.setNode(layer2);

        slide.setToX(0);

        slide.play();

        layer1.setTranslateX(0);


        //Handling the visibility and invisibility of buttons and labels

        firstnameLBL.setVisible(false);
        lastnameLBL.setVisible(false);
        firstnameTXTBox.setVisible(false);
        emailTXTBox.setVisible(false);
        lastnameTxt.setVisible(false);
        verificationcodetxtbox.setVisible(false);
        verifyBTN.setVisible(false);
        verifycodeBTN.setVisible(false);
        registerLBL.setVisible(false);
        emailLBL.setVisible(false);
        btnRegister.setVisible(false);
        signinTRANSbtn.setVisible(false);
        codeconfirmedLBL.setVisible(false);
        confirmVerifyLBL.setVisible(false);
        newPWtxt.setVisible(false);
        newPWlabel.setVisible(false);
        newUsernameLbl.setVisible(false);
        newUsernameTXT.setVisible(false);
        picurl_text.setVisible(false);
        upload_pic_btn.setVisible(false);
        uploadpiclabel.setVisible(false);
        showRegisterPW.setVisible(false);




        registerTRANSbtn.setVisible(true);
        btnSignIn.setVisible(true);
        loginLBL.setVisible(true);
        loginPW_LBL.setVisible(true);
        loginlabeltxt.setVisible(true);
        PasswordTxt.setVisible(true);
        UsernameTxt.setVisible(true);
        showLoginPW.setVisible(true);


        slide.setOnFinished((e->{



        }));
    }

    //This function handles the sending of authentication emails in the stage of registering.

    @FXML
    void sendCodeBTN(MouseEvent event) throws MessagingException {


          send_code();


    }

    public int send_code() throws MessagingException {

        //Here the otp is generated and converted to string format.

        int otp;

        Random random = new Random();

        int min = 1000000;
        int max = 9999999;

        otp = random.nextInt(max-min)+min;

        Authotp = Integer.toString(otp);

        email = emailTXTBox.getText();

        if (email != null){


            //Here the Mailer class is calls so as to initiate the function of sending an email with the set contents.


            Mail mail = new Mail();

            mail.setupServerProperties();
            mail.draftEmail(email, Authotp);
            mail.sendEmail();



        }else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid email address");
            alert.showAndWait();
            System.out.println("Email is null");
        }



        confirmVerifyLBL.setVisible(true);

        return otp;

    }

    //This function handles the verification of the OTP code sent in the immediate previous class. Confirming registration only when that OTP is confirmed.

    @FXML
    void Verify(MouseEvent event) {

        verify_code();

    }

    public void verify_code(){

        if (!Authotp.equals(verificationcodetxtbox.getText())) {

            codeconfirmedLBL.setVisible(true);
            codeconfirmedLBL.setText("Verification was unsuccessful. Please confirm your code!");
        }else if (Authotp.equals(verificationcodetxtbox.getText())) {
            codeconfirmedLBL.setVisible(true);
            codeconfirmedLBL.setText("Verification was successful. Please register your account!");
            btnRegister.setVisible(true);
            newPWlabel.setVisible(true);
            newPWtxt.setVisible(true);
            newUsernameLbl.setVisible(true);
            newUsernameTXT.setVisible(true);
            picurl_text.setVisible(true);
            upload_pic_btn.setVisible(true);
            uploadpiclabel.setVisible(true);
            showRegisterPW.setVisible(true);

        }

    }

    //This function finishes off the registeration process by inserting new user information into the ussr DB.

    @FXML
    void registerUser(MouseEvent event) throws SQLException, FileNotFoundException {
        String FirstName = firstnameTXTBox.getText();
        String LastName = lastnameTxt.getText();
        String Email = emailTXTBox.getText();
        String Password =  newPWtxt.getText();
        String Username = newUsernameTXT.getText();

        register_user(Email, Username, FirstName, LastName, Password);

    }

    public int register_user(String Email, String Username, String FirstName, String LastName, String Password) throws SQLException, FileNotFoundException {

        //Here I am preparing user information for insertion into the database.




        String checkusers = "SELECT * FROM USERS WHERE Email = '"+Email+"' AND Username = '"+Username+"';";

        String query = "Insert into Users (First_Name, Last_Name, Email, Password, Username, profile_pic) values ('"+FirstName+"', '"+LastName+"', '"+Email+"' ,'"+Password+"', '"+Username+"', ?);";



        PreparedStatement registerPreparedStatement = conn.prepareStatement(query);

        PreparedStatement checkPreparedStatement = conn.prepareStatement(checkusers);


        ResultSet userSet = checkPreparedStatement.executeQuery();


        if (!userSet.next()) {


            if (pic == null) {
                pic = new File("src/main/resources/Icons/icons8-avatar-96.png");
            }

            FileInputStream fis = new FileInputStream(pic);

            registerPreparedStatement.setBinaryStream(1, fis);

            registerPreparedStatement.execute();

            // Initially I was prinitng out this meessga to the console to let me know when the user has been sucessfully registered.

            System.out.println("SUCCESSFUL");

            //Now I will add a notification function that will make the successful registration known to the user through the interface.


            notify.registerSuccess();


        }else{


            registerERRlabel.setVisible(true);
            registerERRlabel.setText("This email already exists");

        }


        return 0;
    }


    //Display user home page using FXML Loader
    private void display_user_Page(MouseEvent event) throws IOException, SQLException{



        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/userFXMLpages/User_Home.fxml"));
        Parent root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Home");

        User_Home home = loader.getController();
        home.displayUser(name, user_name);
        home.displayPage(name);
        home.displayBalance(user_balance);
        home.getID(user_name);





    }
}