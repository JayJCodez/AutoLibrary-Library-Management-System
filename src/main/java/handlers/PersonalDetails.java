package handlers;

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
import Model.AdminUserModel;
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

public class PersonalDetails implements Initializable {

    @FXML
    private TextField adminTypeTxt;

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
    private TextField profile_pic_url;

    @FXML
    private TextField roadTxt;

    @FXML
    private Label updateDetailsButton;

    @FXML
    private Label upload_pic_button;

    @FXML
    private ImageView profile_image;

    private Connection conn;

    public String username;

    public String admin_ID;
 
    private File pic;

    String email;

    public PersonalDetails() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }

    @FXML
    void deleteAccount(MouseEvent event) throws SQLException, IOException {

        String query = "DELETE FROM ADMINUSERS WHERE Employee_ID = '"+AdminUserModel.getInstance().getAdminID()+"';";

    
        
        PreparedStatement deleteStatement = conn.prepareStatement(query);

        deleteStatement.execute();

        new NotificationHandler().deletedAccountNotify(firstNameTxt.getText());

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/BackupAdmin/LoginPage.fxml"));
        Parent root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Welcome");
    }

    @FXML
    void updateAccount(MouseEvent event) throws SQLException, FileNotFoundException {

        if(pic != null){


            String updateDetailsQuery = "UPDATE ADMINUSERS SET First_Name = '"+firstNameTxt.getText()+"', Last_Name = '"+lastNmeTxt.getText()+"', Email = '"+emailTxt.getText()+"', Phone_No = '"+phoneTxt.getText()+"', House_Number_Street_Name = '"+roadTxt.getText()+"', City = '"+city.getText()+"', Postcode = '"+postcode.getText()+"', profile_pic = ? WHERE Employee_ID = "+AdminUserModel.getInstance().getAdminID()+";";

            PreparedStatement ps = conn.prepareStatement(updateDetailsQuery);

            FileInputStream fis = new FileInputStream(pic);

            ps.setBinaryStream(1, fis);

            ps.execute();

        }else{

            String updateDetailsQuery = "UPDATE ADMINUSERS SET First_Name = '"+firstNameTxt.getText()+"', Last_Name = '"+lastNmeTxt.getText()+"', Email = '"+emailTxt.getText()+"', Phone_No = '"+phoneTxt.getText()+"', House_Number_Street_Name = '"+roadTxt.getText()+"', City = '"+city.getText()+"', Postcode = '"+postcode.getText()+"' WHERE Employee_ID = "+AdminUserModel.getInstance().getAdminID()+";";

            PreparedStatement ps = conn.prepareStatement(updateDetailsQuery);

            ps.execute();

        }


        new NotificationHandler().updatedDetails();

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        adminTypeTxt.setEditable(false);
        setUserDetails();
       

    }


    public void setAdminID(String adminID){

        AdminUserModel.getInstance().setAdminID(adminID);
        admin_ID = AdminUserModel.getInstance().getAdminID();
       

    }

    public void setUserDetails(){
        String query = "SELECT * FROM ADMINUSERS WHERE Employee_ID = '"+AdminUserModel.getInstance().getAdminID()+"'";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                email = rs.getString("Email");
                emailTxt.setText(email);

                
                phoneTxt.setText(rs.getString("Phone_No"));
                firstNameTxt.setText(rs.getString("First_Name"));
                lastNmeTxt.setText(rs.getString("Last_Name"));
                roadTxt.setText(rs.getString("House_Number_Street_Name"));
                city.setText(rs.getString("City"));
                postcode.setText(rs.getString("Postcode"));
                adminTypeTxt.setText(rs.getString("admin_Type"));
                InputStream is = rs.getBinaryStream("profile_pic");
                if (is == null) {
                    return;
                }else{

                    profile_image.setImage(new Image(is));

                }
      
            }

        } catch (Exception e) {
            System.out.println(e);

        }
    }

    @FXML
    void upload_picture(MouseEvent event) {

        FileChooser choose_pic = new FileChooser();
        pic = choose_pic.showOpenDialog(null);
        
        if (pic == null) {

            return;
            
        }else{

            profile_pic_url.setText(pic.getName());

        }
    }

}