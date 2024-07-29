package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application  {

  

    @Override
    public void start(Stage stage1) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("resources/LoginPage.fxml"));
        // Scene scene = new Scene(root);
        // stage1.setTitle("Login");
        // stage1.setScene(scene);
        // stage1.show();
       try {
        Parent root =  FXMLLoader.load(getClass().getClassLoader().getResource("AdminFXMLPages/LoginPage.fxml"));
        Scene scene = new Scene(root);
        stage1.setTitle("WELCOME");
        stage1.setScene(scene);
        stage1.show();
        
       } catch (Exception e) {
        System.err.println(e);
        e.printStackTrace();
           } 

    }


    public static void main(String[] args) {
        launch(args);
    }



    }
