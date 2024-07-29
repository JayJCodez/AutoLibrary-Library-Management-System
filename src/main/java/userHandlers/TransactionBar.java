package userHandlers;

import Model.User_TransactionsModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TransactionBar {

    @FXML
    private Label bookName;

    @FXML
    private Label collectionDate;

    @FXML
    private Label returnDate;

    @FXML
    private Label transactionID;

    @FXML
    private Label return_status;

    public void setData(User_TransactionsModel transactions){


        bookName.setText(transactions.getBookName());
        collectionDate.setText(transactions.getCollectionDate());
        returnDate.setText(transactions.getReturnDate());
        transactionID.setText(transactions.getTransactionID());

        String status = transactions.getReturned();

        if(status.equals("True")) {
            return_status.setText("Returned");
        }else{

            return_status.setText("Not Returned");

        }

    }

}
