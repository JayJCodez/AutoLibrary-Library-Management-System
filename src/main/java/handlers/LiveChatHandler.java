package handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import Connection.DBConnection;
import Model.AdminUserChatCardModel;
import Model.AdminUserModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LiveChatHandler implements Initializable{

    @FXML 
    private VBox userListBox;

    @FXML
    private VBox vbox;

    @FXML
    private TextField messageTXT;

    @FXML
    private Button sendButton;

    private PrintWriter out;

    public BufferedReader in;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML 
    private Button printlist;

    @FXML 
    private Label recipient;

    @FXML
    private Label status;

    @FXML
    private VBox vboxstatus;

    @FXML
    private Label livechatlabel;

    private String recipientid;

    private ObservableList<AdminUserChatCardModel> users = FXCollections.observableArrayList();

    // private Label messageLabel;

    private static final String SERVER_ADDRESS = "localhost"; // Server's IP address or hostname
    private static final int PORT = 12345; // Server's port number

    Connection conn;

    public LiveChatHandler() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }

    @FXML
    void print() throws IOException{

         // Send a command to request the list of connected users
         out.println("/list");

         // Receive and print the list of connected users
         String line;
         while ((line = in.readLine()) != null) {
             System.out.println(line);
         }


    }

    @FXML
    void sendMessage(ActionEvent event) throws SQLException {

        String message = messageTXT.getText();
        String query = "INSERT INTO ChatMessages (SenderID, RecipientID, MessageContent) values ("+recipientid+", "+AdminUserModel.getInstance().getAdminID()+", '"+message+"');";

        PreparedStatement ps = conn.prepareStatement(query);

        out.println("/msg" + " " +  recipientid + " " + message); // Send the message to the server

        sendText(message);

        ps.execute();

      

        messageTXT.setText(""); // Clear the text field after sending

      
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       try {
        try {
            setUsers();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        scroll.setContent(vbox);
        scroll.setFitToWidth(true);
        connectServer();
        messageReceieved();
        status.setVisible(false);
        recipient.setVisible(false);
        livechatlabel.setVisible(true);
        vboxstatus.setVisible(false);
    
    } catch (UnknownHostException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }



    @SuppressWarnings("resource")
    public void connectServer() throws UnknownHostException, IOException{


     Socket socket = new Socket(SERVER_ADDRESS, PORT);

     sendText("Connected to Live Chat\n");
    //  .appendText("Connected to Live Chat\n");

       // Initialize input and output streams for communication
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);

    out.println(AdminUserModel.getInstance().getAdminID());
    //   out.println("Jesse");

    //   messageArea.appendText("Enter messages (type 'quit' to exit):\n");
      sendText("Enter messages (type 'quit' to exit)");

    }




 public void messageReceieved(){

    new Thread(() -> {
        try {
            String message;
          
            String currentuser = AdminUserModel.getInstance().getAdminID();
            System.out.println(currentuser);
            while ((message = in.readLine()) != null) {
                if (message.startsWith(currentuser)) {
                    String replacement  =  "You ";
                    String modified_message = message.replaceFirst("\\b"+currentuser+"\\b", replacement);
                    sendText(modified_message);
                }else{


                    String msg = message;
                    Platform.runLater(() -> receieveMsg(msg));
    

                }
               
        
                // messageArea.appendText(message + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }).start();


 }


 public void receieveMsg(String received){


    Platform.runLater(() -> {
        Label recieveLabel = new Label();
        recieveLabel.setStyle("-fx-background-color:#233142;\n" + //
                        "    -fx-background-radius: 30px;\n" + //
                        "    -fx-text-fill: #5dacbd;\n" + //
                        " -fx-font-size: 15px;\n" + //
                        "  -fx-padding: 5px;");
        recieveLabel.setText(received);
        recieveLabel.setAlignment(Pos.CENTER_RIGHT);

        HBox hbox = new HBox(recieveLabel);
        hbox.setAlignment(Pos.CENTER_RIGHT); // Align the label to the left within the HBox

        vbox.getChildren().add(hbox);
     
        
    });


 }

 public void sendText(String sent){


    Platform.runLater(() -> {
       
        Label sendLabel = new Label();
        

         HBox hbox = new HBox(sendLabel);
         sendLabel.setStyle("-fx-background-color: #24527a;\n" + //
         "    -fx-background-radius: 30px;\n" + //
         "    -fx-text-fill: #5dacbd;\n" + //
         " -fx-font-size: 15px;" + //
         "  -fx-padding: 5px;");
        hbox.setAlignment(Pos.CENTER_LEFT); // Align the label to the left within the HBox

        sendLabel.setText(sent);
        sendLabel.setAlignment(Pos.CENTER_LEFT);
        
        vbox.getChildren().add(hbox);

    });

 }

public void setAdminUser(String adminID){


    AdminUserModel.getInstance().setAdminID(adminID);


}


      public ObservableList<AdminUserChatCardModel> list() throws IOException, SQLException{


        String query = "SELECT * FROM ADMINUSERS";

        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            // InputStream stream = rs.getBinaryStream("BookCover");
            // Image image =   new Image(stream);
           
            Label message = new Label();
            


            users.add(new AdminUserChatCardModel(
                Integer.toString(rs.getInt("Employee_ID")),
                rs.getString("First_Name"),
                rs.getString("Last_Name"),
                message
            ));

        // stream.close();
          

       }

        return users;

  
}

public void setUsers() throws IOException, SQLException{

    users = list();

    int column = 0;
    int row = 1;

    try{

       for(AdminUserChatCardModel user : users){

            FXMLLoader fxmlLoader1 = new FXMLLoader();
            fxmlLoader1.setLocation(getClass().getResource("/BackupAdmin/UserChatCard.fxml"));

            AnchorPane userCard = fxmlLoader1.load();
            AdminChatCardHandler handler = fxmlLoader1.getController();
            handler.setData(user);
            
            while (column == 0) {
                column++;
            }
             
            if (column == 2) {
              
                column = 0;
                ++row;
                ++column;

            }

             // Retrieve the label from the book and set event handler
             Label label = user.getMessage();
             label.setOnMouseClicked((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
                 @Override
                 public void handle(MouseEvent event) {
                     // Handle label click event here
                     System.out.println("WORKING");
                     vboxstatus.setVisible(true);
                     recipient.setVisible(true);
                     status.setVisible(true);
                     livechatlabel.setVisible(false);
                     recipientid = user.getId();
                     try {
                        recoverMessages();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                     recipient.setText(user.getFirst_Name() + " " + user.getLast_Name());
                     status.setText("ONLINE");
                     
                     try {
                        // openNewPage(book.getName(), book.getAuthor());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                 }
             });
            //  Label saveLabel = book.getSaveItem();
            //  saveLabel.setOnMouseClicked((EventHandler<? super MouseEvent>)new EventHandler<Event>() {

            //     @Override
            //     public void handle(Event arg0) {

                

            //         String query = "INSERT INTO SavedItems (book_ID, user_id) values (?,?);"; 

            //         String findbook = "SELECT * FROM LibraryBooks WHERE BookName = '"+book.getName()+"'";

            //         String finduserID = "SELECT * FROM USERS WHERE Username = '"+username+"';";

            //         try {
            //             PreparedStatement ps = conn.prepareStatement(findbook);

            //             PreparedStatement ps3 = conn.prepareStatement(finduserID);

            //             PreparedStatement ps2 = conn.prepareStatement(query);

            //             ResultSet rs = ps.executeQuery();

            //             ResultSet rs3 = ps3.executeQuery();

            //             if (rs.next()) {
            //                 book_id = rs.getInt("BookID");
            //             }

            //             if (rs3.next()) {
            //                 user_id = rs3.getInt("User_ID");
                         
            //             }
                        
            //                 ps2.setInt(1, book_id);
            //                 ps2.setInt(2, user_id);
                        
            //                 ps2.execute();

            //                 System.out.println(UserContextModel.getInstance().getUsername());

            //         } catch (SQLException e) {
            //             // TODO Auto-generated catch block
            //             e.printStackTrace();
            //         }

                    
            //     }
                
            //  });
            // if (column > 3) {

            //     column = 0;
            //     ++row;
                
            // }
          

            // Button btn = new Button();
            grid.add(userCard, column++, row);
            // BookContainer.add(btn, column, row);
            // BookContainer.applyCss(ger);
            // GridPane.setMargin(BookInfoContainer, new Insets(10));
            // BookContainer.setStyle("-fx-spacing: 20px;");
            grid.setVgap(25);

            grid.setPadding(new Insets(10));
            // BookContainer.setHgap(50);

        }
        } catch (Exception e) {
           e.printStackTrace();
        }
       
  


}

public void recoverMessages() throws SQLException{

    vbox.getChildren().clear();

    String query = "SELECT * FROM ChatMessages WHERE SenderID = "+AdminUserModel.getInstance().getAdminID()+" AND RecipientID = "+recipientid+" OR SenderID = "+recipientid+" AND RecipientID = "+AdminUserModel.getInstance().getAdminID()+" ORDER BY Timestamp;"; 

    // String receivedmsg = "SELECT * FROM ChatMessages WHERE RecipientID = "+AdminUserModel.getInstance().getAdminID()+" AND SenderID = "+recipientid+" ORDER BY Timestamp;";



    PreparedStatement ps = conn.prepareStatement(query);

    ResultSet rs = ps.executeQuery();
   
    while (rs.next()) {
        int senderID = rs.getInt("SenderID");
        // int recieverId = rs.getInt("RecipientID");
        String msg = rs.getString("MessageContent");
        if (senderID == Integer.parseInt(AdminUserModel.getInstance().getAdminID())) {
            sendText(msg);
        }
        
        if(senderID == Integer.parseInt(recipientid)){

                receieveMsg(msg);
            }
    

        
      
     
    }
    


}
}
