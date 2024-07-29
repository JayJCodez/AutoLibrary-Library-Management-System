package handlers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import Connection.DBConnection;
import Model.AdminUserModel;
import Model.RequestBoxModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ApproveDenyRequestHandler implements Initializable{

    @FXML
    private GridPane grid;

    ObservableList<RequestBoxModel> requestlist = FXCollections.observableArrayList();

    Connection conn;




    public ApproveDenyRequestHandler() throws SQLException{


        this.conn = DBConnection.getInstance().connectingDB();
    }

    public ObservableList<RequestBoxModel> list() throws SQLException, IOException{

        String status = "Pending";

        String query = "SELECT \n" + //
                "    rp.user_id, \n" + //
                "    rp.request_time, \n" + //
                "    rp.approvalStatus, \n" + //
                "    u.Username, \n" + //
                "    rp.resourceID, \n" + //
                "    r.File_Name,\n" + //
                "    r.ResourceCoverImg\n" + //
                "FROM \n" + //
                "    ResourcePermissions rp\n" + //
                "JOIN \n" + //
                "    USERS u ON rp.user_id = u.User_ID\n" + //
                "JOIN \n" + //
                "    Resources r ON rp.resourceID = r.File_ID\n" + //
                "WHERE approvalStatus = '"+status+"';\n" + //
                "";

        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();


        while (rs.next()) {

            InputStream stream = rs.getBinaryStream("ResourceCoverImg");
            Image image =   new Image(stream);

            Label approvebtn = new Label();
            Label denybtn = new Label();

            Timestamp stamp = rs.getTimestamp("request_time");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String time = sdf.format(stamp);



            requestlist.add(new RequestBoxModel(
                    image,
                    rs.getString("Username"),
                    rs.getString("File_Name"),
                    time,
                    approvebtn,
                    denybtn,
                    rs.getInt("resourceID")
            ));

            stream.close();


        }



        return requestlist;



    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        try {
            loadRequestList();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setAdminID(String adminID){


        AdminUserModel.getInstance().setAdminID(adminID);
        System.out.println(AdminUserModel.getInstance().getAdminID());

    }

    public void loadRequestList() throws SQLException, IOException{

        list().clear();
        try {
            requestlist = list();
        } catch (SQLException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        int column = 0;
        int row = 1;



        try {
            for(RequestBoxModel res : requestlist){

                FXMLLoader fxmlLoader1 = new FXMLLoader();
                fxmlLoader1.setLocation(getClass().getResource("/AdminFXMLPages/RequestBox.fxml"));

                AnchorPane RequestContainer = fxmlLoader1.load();
                RequestBoxHandler handler = fxmlLoader1.getController();
                handler.setData(res);

                while (column == 0) {
                    column++;
                }

//                if (column == 5) {
//
//                    column = 0;
//                    ++row;
//                    ++column;
//
//                }

                // Retrieve the label from the book and set event handler
                Label label = res.getApprovebtn();
                Label denybtn = res.getDenybtn();
                label.setOnMouseClicked((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        // Handle label click event here

                        try {

                            approve_request(res);
                            label.setDisable(true);
                            label.setText("Approved");
                            denybtn.setDisable(true);

                            grid.getChildren().clear();

                            loadRequestList();




                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }



                    }
                });



                denybtn.setOnMouseClicked((EventHandler<? super MouseEvent>)new EventHandler<Event>() {

                    @Override
                    public void handle(Event arg0) {



                        //ADD DENIAL QUERY



                    }

                });



                grid.add(RequestContainer, column++, row);

                grid.setVgap(25);

                grid.setHgap(50);

            }

        } catch (Exception e) {
            // TODO: handle exception
        }


    }

    public void approve_request(RequestBoxModel res) throws SQLException {

        String query = "UPDATE ResourcePermissions\n" + //
                "SET employee_id = "+Integer.parseInt(AdminUserModel.getInstance().getAdminID())+",\n" + //
                "    approval_time = CURRENT_TIMESTAMP,\n" + //
                "    approvalStatus = 'Approved'\n" + //
                "WHERE resourceID = "+res.getResourceid()+";";

        PreparedStatement ps = conn.prepareStatement(query);

        ps.execute();


    }
}
