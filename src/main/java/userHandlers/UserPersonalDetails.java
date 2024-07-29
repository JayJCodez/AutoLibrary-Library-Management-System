package userHandlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import Alerts.NotificationHandler;
import Connection.DBConnection;
import Model.UserContextModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UserPersonalDetails implements Initializable {

    @FXML
    private ImageView profile_pic;

    @FXML
    private TextField profile_pic_url;

    @FXML
    private Label upload_pic_button;

    @FXML
    private TextField userType;

    @FXML
    private TextField city;

    @FXML
    private Label deleteAccountBtn;

    @FXML
    private TextField emailTxt;

    @FXML
    private TextField firstNameTxt;

    @FXML
    private TextField lastNmeTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField postcode;

    @FXML
    private TextField roadTxt;

    private Connection conn;

    private File pic;

    @FXML
    private Label updateDetailsButton;

    public UserPersonalDetails() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }

    @FXML
    void deleteAccount(MouseEvent event) throws SQLException, IOException {

        String query = "DELETE FROM USERS WHERE Username = '"+UserContextModel.getInstance().getUsername()+"'";

        PreparedStatement ps = conn.prepareStatement(query);

        ps.execute();

        new NotificationHandler().deletedAccountNotify(firstNameTxt.getText());

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/AdminFXMLPages/LoginPage.fxml"));
        Parent root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Welcome");


    }

    @FXML
    void updateAccount(MouseEvent event) throws SQLException, FileNotFoundException {



        if(pic != null){

            String query = "UPDATE USERS SET First_Name = '"+firstNameTxt.getText()+"', Last_Name = '"+lastNmeTxt.getText()+"', Email = '"+emailTxt.getText()+"', House_Number_Street_Name = '"+roadTxt.getText()+"', City = '"+city.getText()+"', Postcode = '"+postcode.getText()+"', Phone_No = '"+phoneTxt.getText()+"', profile_pic = ? WHERE Username = '"+UserContextModel.getInstance().getUsername()+"';";

            PreparedStatement s = conn.prepareStatement(query);

            FileInputStream fis = null;

            fis = new FileInputStream(pic);

            s.setBinaryStream(1, fis);

            s.execute();


        }else{

            String query = "UPDATE USERS SET First_Name = '"+firstNameTxt.getText()+"', Last_Name = '"+lastNmeTxt.getText()+"', Email = '"+emailTxt.getText()+"', House_Number_Street_Name = '"+roadTxt.getText()+"', City = '"+city.getText()+"', Postcode = '"+postcode.getText()+"', Phone_No = '"+phoneTxt.getText()+"' WHERE Username = '"+UserContextModel.getInstance().getUsername()+"';";

            PreparedStatement s = conn.prepareStatement(query);

            s.execute();



        }

        

        new NotificationHandler().updatedDetails();


    }


    public void setUsername(String username){

        UserContextModel.getInstance().setUsername(username);

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        loadUserDetails();

    }

    public void loadUserDetails(){

        String query = "SELECT * FROM USERS WHERE Username = '"+UserContextModel.getInstance().getUsername()+"'";

        try {
            PreparedStatement ps = conn.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                firstNameTxt.setText(rs.getString("First_Name"));
                lastNmeTxt.setText(rs.getString("Last_Name"));
                roadTxt.setText(rs.getString("House_Number_Street_Name"));
                city.setText(rs.getString("City"));
                postcode.setText(rs.getString("Postcode"));
                emailTxt.setText(rs.getString("Email"));
                phoneTxt.setText(rs.getString("Phone_No"));
                InputStream is = rs.getBinaryStream("profile_pic");
                profile_pic.setImage(new Image(is));

            }



        } catch (SQLException e) {

            e.printStackTrace();
        }


    }


    @FXML
    void upload_picture(MouseEvent event) {

        FileChooser pic_chooser = new FileChooser();
        pic = pic_chooser.showOpenDialog(null);

        if (pic == null) {
            return;
        }else{
            profile_pic_url.setText(pic.getName());
        }

    }


}
