package handlers;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import Connection.DBConnection;
import Model.UserContextModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ResourceExpanded implements Initializable{

    @FXML
    private Label chooseDownloadPath;

    @FXML 
    private BorderPane border;

    @FXML
    private ImageView img;

    @FXML
    private Label requestBTN;

    @FXML
    private Label resource_extension;

    @FXML
    private Label resource_name;

    @FXML
    private Label resource_type;

    @FXML
    private Label saveBTN;

    @FXML
    private Label saveFile;

    @FXML 
    private Label resourceID;

    int userID;

    int resID;

    File selectedDir;

    DirectoryChooser dc = new DirectoryChooser();;

    String path;


    String status;


    Connection conn;



    public ResourceExpanded() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }
 

    public void setData(String res_name, String res_type, String res_ext,Image image, int user_id, int res_id){

        border.setCenter(img);
        resource_name.setText(res_name);
        resource_type.setText(res_type);
        resource_extension.setText(res_ext);
        img.setImage(image);
        img.setPreserveRatio(true);
        UserContextModel.getInstance().setUserid(user_id);
        resourceID.setText(Integer.toString(res_id));
     
        
       
        
        

    }

    @FXML
    void choose_path(MouseEvent event) {



 
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

    @FXML
    void request(MouseEvent event) throws SQLException {

        if(UserContextModel.getInstance().getUserid() == 0){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("You are not a non-admin user. For admins this is for viewing purposes only.");
            alert.showAndWait();


        }

        String query2 = "SELECT * FROM ResourcePermissions WHERE user_id = "+UserContextModel.getInstance().getUserid()+" AND resourceID = "+resourceID.getText().toString()+"";

        PreparedStatement pq = conn.prepareStatement(query2);

        ResultSet rp = pq.executeQuery();

        if (rp.next()) {
            String reuseQuery = "UPDATE ResourcePermissions SET approvalStatus = 'Pending', WHERE user_id = "+UserContextModel.getInstance().getUserid()+" AND resourceID = "+resourceID.getText().toString()+";";
            
            Statement s = conn.prepareStatement(reuseQuery);

            s.execute(reuseQuery);
        }else{

        String query = "INSERT INTO ResourcePermissions (user_id, resourceID) values ("+UserContextModel.getInstance().getUserid()+", '"+resourceID.getText().toString()+"');";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.execute();

        requestBTN.setDisable(true);
        requestBTN.setText("Pending");
        }
    }

    @FXML
    void save_file(MouseEvent event) throws SQLException, IOException{


        String query = "SELECT * FROM Resources WHERE File_ID = '"+Integer.parseInt(resourceID.getText())+"';";

        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();


        if (rs.next()) {


            OutputStream downloadstream = new FileOutputStream(new File(path + "/" + rs.getString("File_Name") + "." + rs.getString("File_Extension")));
            byte[] content = new byte[1024];
            int size;
            InputStream download = rs.getBinaryStream("File_Blob");
            while((size = download.read(content)) != -1) {
                downloadstream.write(content, 0, size);
            }

            downloadstream.close();
            download.close();


        }

    }

    public void setRequestDetails(int user_id, int res_id) throws SQLException{


        UserContextModel.getInstance().setUserid(user_id);


        userID = user_id;
        resID = res_id;
       
   



     

    }

    public void checkCurrentReq(int resource_id) throws SQLException{

   
        chooseDownloadPath.setVisible(false);
        saveFile.setVisible(false);
        saveBTN.setVisible(false);

        String query = "SELECT * FROM ResourcePermissions WHERE user_id = "+UserContextModel.getInstance().getUserid()+" AND resourceID = "+resource_id+";";

        PreparedStatement ps = conn.prepareStatement(query);
        
        ResultSet rs = ps.executeQuery();

    
        if (rs.next()) {
            System.out.println(rs.getString(7));
            status = rs.getString(7);
            if (status.equals("Pending") == true) {
                requestBTN.setText(status);
                requestBTN.setDisable(true);
            }

            if (status.equals("Approved")) {
                requestBTN.setText(status);
                requestBTN.setDisable(true);
                saveFile.setVisible(true);
                chooseDownloadPath.setVisible(true);

            }
        }

    
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    
try {
  
} catch (Exception e) {
    // TODO: handle exception
}
       
    }

}
