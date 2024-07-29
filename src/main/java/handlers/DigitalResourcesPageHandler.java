package handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Connection.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class DigitalResourcesPageHandler {

    private String fileName = null;

    FileChooser saveFile = new FileChooser();

    File PracticeselectFile = null;

    // private Media media;

    // private Media ad1;

    private MediaPlayer mediaPlayer;

    @FXML
    private Label Advert1;

    @FXML
    private Label Advert2;

    @FXML
    private Label Advert3;

    @FXML
    private Label Advert4;

    @FXML
    private Label Advert5;

    @FXML
    private Button Playbtn;

    @FXML
    private Label durationlabel;

    @FXML
    private MediaView mediaView;


    @FXML
    private BorderPane borderView;

    @FXML
    private AnchorPane view;

    Connection conn;

    public DigitalResourcesPageHandler() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }

//    @FXML
//    void SearchDigitalResources(ActionEvent event) throws IOException {
//
//        try {
//
//        Group root = FXMLLoader.load(getClass().getResource("/AdminFXMLPages/DigitalResourcesSearch.fxml"));
//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.setTitle("Library of Resources");
//            // view = FXMLLoader.load(getClass().getResource("/resources/DigitalResourcesSearch.fxml"));
//            // view.setVisible(true);
//
//        } catch (IOException e) {
//            System.err.println(e);
//        }
//
//    }


//    @FXML
//    void savefile(ActionEvent event) throws SQLException, IOException {
//
//
//        fileName = PracticeselectFile.getName();
//        FileInputStream fis = new FileInputStream(PracticeselectFile);
//
//
//        String Insert_File = "insert into Files (Filename, Content) values ('"+fileName+"',?);";
//
//        // String uploadFile = "`UPDATE Files SET Content = '?', WHERE Filename = "+fileName+";";
//        PreparedStatement insertfile = conn.prepareStatement(Insert_File);
//        // PreparedStatement updatefile = conn.prepareStatement(uploadFile);
//
//
//        String selectImgBlob = "select Content from Files where Filename = 'TutorialPytube.mp4'";
//        PreparedStatement selectImage = conn.prepareStatement(selectImgBlob);
//
//
//        ResultSet rs = selectImage.executeQuery();
//
//        insertfile.setBinaryStream(1, fis);
//
//        // updatefile.executeUpdate();
//
//        insertfile.execute();
//
//        fis.close();
//
//        if (rs.next()) {
//           try {
//            InputStream getIn = rs.getBinaryStream(1);
//
//
//            FileOutputStream getOut = new FileOutputStream(new File("src/resources/Tutorial.mp4"));
//            // File output = rs.getBlob(0);
//
//            int r = 0;
//
//            while ((r = getIn.read()) != -1) {
//                getOut.write(r);
//            }
//            getOut.close();
//           } catch (Exception e) {
//            System.err.println(e);
//           }
//            //  FileInputStream showImgSaved = new FileInputStream(getIn);
//
//
//            // System.out.println(rs.getInt(1));
//        }
//
//
//
//   conn.close();
//
//    }



    @FXML
    void stopMedia(MouseEvent event) {

       mediaPlayer.pause() ;
       mediaPlayer.play();


    }


    @FXML
    void playAd2(MouseEvent event) {


        File file = new File("src/resources/Victory Lap x RTW Central Cee & Dave Freestyle LIVE (Highlights).mp4");
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);

    }

    @FXML
    void playAd1(MouseEvent event) {

        File file = new File("src/resources/Adverts/TutorialPytube.mp4");
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        // mediaPlayer.setAutoPlay(true);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);

        // Scene scene = mediaView.getScene();


        mediaView.fitHeightProperty();
        mediaView.fitWidthProperty();

    }

      @FXML
    void Play(ActionEvent event) {

        mediaPlayer.play();
    }

    @FXML
    void Pausebtn(ActionEvent event) {

        mediaPlayer.pause();
        // try {

        //     FileChooser fileChooser = new FileChooser();
        //     fileChooser.setTitle("Select Media");
        //     File PracticeselectFile = fileChooser.showOpenDialog(null);

        //     if (PracticeselectFile != null) {
        //         String url = PracticeselectFile.toURI().toString();

        //         media = new Media(url);

        //         mediaPlayer = new MediaPlayer(media);

        //         mediaView.setMediaPlayer(mediaPlayer);

        //         mediaPlayer.setAutoPlay(true);

        //         durationlabel.setText(media.getDuration().toString());

        //         Scene scene = mediaView.getScene();

                // mediaView.fitWidthProperty().bind(scene.widthProperty());
                // mediaView.fitHeightProperty().bind(scene.heightProperty());

        //     }
        // } catch (Exception e) {
        //     System.err.println(e);
        // }

    }
}
