package userHandlers;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import Alerts.NotificationHandler;
import Connection.DBConnection;
import Model.PaymentTransactionsModel;
import Model.ResourceTableModel;
import Model.UserContextModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class User_TopUp implements Initializable {

    //Creating global instance of notfication class

    NotificationHandler notifyTopUop = new NotificationHandler();

    //Referencing all FXML GUI Objects

    @FXML
    private TableColumn<PaymentTransactionsModel, Integer> transaction_amount;

    @FXML
    private TableColumn<PaymentTransactionsModel, String> transaction_date;

    @FXML
    private TableColumn<PaymentTransactionsModel, Integer> transaction_id;

    @FXML
    private TableView<PaymentTransactionsModel> transactions_table;

    @FXML
    private Label addcredit_btn;

    @FXML
    private Label TopUpErrLBL;

    @FXML
    private Label CurrentBalanceLBL;

    @FXML
    private TextField amountTXT;

    @FXML
    private Label numberEight;

    @FXML
    private Label numberFive;

    @FXML
    private Label numberFour;

    @FXML
    private Label numberNine;

    @FXML
    private Label numberOne;

    @FXML
    private Label numberSeven;

    @FXML
    private Label numberSix;

    @FXML
    private Label numberThree;

    @FXML
    private Label numberTwo;

    @FXML
    private Label numberZero;

    @FXML
    private Label delete;

    @FXML
    private Label point;

    String balance;

    int amount;

    String number;

    Connection conn;

    private int userId;

    ObservableList<PaymentTransactionsModel> transactionsList = FXCollections.observableArrayList();

    public User_TopUp() throws SQLException{


        this.conn = DBConnection.getInstance().connectingDB();

    }


    public void setUserId(int userId) {
        this.userId = userId;
    }


//public function called upon in previous class to set model value which is the Name of the current logged in user.

    public void setName(String name){

        UserContextModel.getInstance().setName(name);

    }

//public function called upon in previous stage (User_Home.java) in order to set factory values for this current class (User_TopUp.java)

    public void setCurrentBalance(String balance){

        CurrentBalanceLBL.setText(UserContextModel.getInstance().getBalance());

    }


    public void loadTableData() throws SQLException {

        loadTable();

        try {
            transaction_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            transaction_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            transaction_date.setCellValueFactory(new PropertyValueFactory<>("date"));
//            OptionsCol.setCellFactory(cellFactory);
            transactions_table.setItems(transactionsList);


        } catch (Exception e) {
            System.err.println(e);
        }



    }


    public void loadTable() throws SQLException {

        String query = "select * from PaymentTransactions where user_id="+userId+";";

        PreparedStatement load_transactions = conn.prepareStatement(query);

        ResultSet results = load_transactions.executeQuery();

        while (results.next()) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(results.getTimestamp("payment_time"));

            transactionsList.add(new PaymentTransactionsModel(
                   date,
                    results.getInt("amount"),
                    results.getInt("paymentId")
            ));

        }






    }

// This function handles the function used to Top - up Library Credits  

    @FXML
    void AddCredit(MouseEvent event) throws NumberFormatException, SQLException {

        //Creating string variable to insert amount in String format in preparation for notification display 
        String amountString = amountTXT.getText();

        try {

            //Conditon to handle exception if the text field is empty 

            if (amountTXT.getText().isEmpty()) {
                amountTXT.focusVisibleProperty();
                TopUpErrLBL.setText("Please enter the amount of the transaction!");
            }if (!amountTXT.getText().isEmpty()) {

                notifyTopUop.TopUpSuccess(amountString);

                //Inserting integer Balance into the Global varaiable in preparation for basic mathemematical functions.

                amount = Integer.valueOf(amountTXT.getText());

                int newBalance = Integer.valueOf(CurrentBalanceLBL.getText());

                //Calaculating new balance of current user and updating it in database information of the user.

                int total = amount + newBalance;

                //Setting new amount to the model

                UserContextModel.getInstance().setBalance(Integer.toString(total));

                //Testing whether my model has recieved user details passed into query. Otherwise query parameter can be null if no test is done to view live value.

                System.out.println(UserContextModel.getInstance().getName());

                //Updating the users balance in the database

                String query = "INSERT INTO PaymentTransactions (user_id, amount) VALUES(?,?)";

                PreparedStatement store_transaction = conn.prepareStatement(query);

                store_transaction.setInt(1, userId);
                store_transaction.setInt(2, amount);


                String insertNewBalance = "UPDATE USERS SET Balance = "+total+" WHERE User_ID = "+userId+";";

                PreparedStatement balanceCheck = conn.prepareStatement(insertNewBalance);


                store_transaction.execute();

                balanceCheck.execute();




                Stage stage;

                FXMLLoader loader = new FXMLLoader();

                loader.setLocation(getClass().getResource("/userFXMLpages/User_Home.fxml"));

                Parent root = loader.load();

                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Home");

                User_Home home = loader.getController();


                home.userid = userId;
                home.refreshBalance(total);
                home.displayUser(UserContextModel.getInstance().getName(), UserContextModel.getInstance().getUsername());
                home.refreshedAddCredit(Integer.parseInt(UserContextModel.getInstance().getBalance()));



            }

        } catch (Exception e) {

            TopUpErrLBL.setText("Error: Please double-check values entered!");
            System.out.println(e);

        }


    }

//Delete by last integer of Top Up value entered in TextField.

    @FXML
    void delete_lastInt(MouseEvent event) {

        number = amountTXT.getText();

        StringBuffer sb = new StringBuffer(number);

        sb.deleteCharAt(sb.length()-1);

        amountTXT.setText(sb.toString());

    }

//Insert '.' anywhere in the amount when mouse clicked on label containing character.

    @FXML
    void insert_dot(MouseEvent event) {

        number = amountTXT.getText();

        String newValue = number + ".";

        amountTXT.setText(newValue);

    }

    //Insert number 8 into the TextField

    @FXML
    void no_eight(MouseEvent event) {

        number = amountTXT.getText();

        String newValue = number + "8";

        amountTXT.setText(newValue);
    }

    //Insert number 5 into the TextField.

    @FXML
    void no_five(MouseEvent event) {

        number = amountTXT.getText();

        String newValue = number + "5";

        amountTXT.setText(newValue);

    }

    //Insert number 4 into the TextField

    @FXML
    void no_four(MouseEvent event) {

        number = amountTXT.getText();

        String newValue = number + "4";

        amountTXT.setText(newValue);

    }

    //Insert number 9 into the TextField.

    @FXML
    void no_nine(MouseEvent event) {

        number = amountTXT.getText();

        String newValue = number + "9";

        amountTXT.setText(newValue);

    }

    //Insert number 1 into the TextField.


    @FXML
    void no_one(MouseEvent event) {

        number = amountTXT.getText();

        String newValue = number + "1";

        amountTXT.setText(newValue);

    }

    //Insert number 7 into the TextField.

    @FXML
    void no_seven(MouseEvent event) {

        number = amountTXT.getText();

        String newValue = number + "7";

        amountTXT.setText(newValue);

    }

    //Insert number 6 into the TextField.

    @FXML
    void no_six(MouseEvent event) {

        number = amountTXT.getText();

        String newValue = number + "6";

        amountTXT.setText(newValue);

    }

    //Insert number 3 into the TextField.

    @FXML
    void no_three(MouseEvent event) {

        number = amountTXT.getText();

        String newValue = number + "3";

        amountTXT.setText(newValue);

    }

    //Insert number 2 into the TextField.

    @FXML
    void no_two(MouseEvent event) {

        number = amountTXT.getText();

        String newValue = number + "2";

        amountTXT.setText(newValue);

    }

    //Insert number 0 into the TextField.

    @FXML
    void no_zero(MouseEvent event) {

        number = amountTXT.getText();

        String newValue = number + "0";

        amountTXT.setText(newValue);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

