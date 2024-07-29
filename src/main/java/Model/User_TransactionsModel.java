package Model;

public class User_TransactionsModel {
    
    String bookName;

    String collectionDate;

    String ReturnDate;

    String transactionID;

    String returned;

    public User_TransactionsModel(String bookName, String collectionDate, String returnDate, String transactionID, String returned) {
        this.bookName = bookName;
        this.collectionDate = collectionDate;
        this.ReturnDate = returnDate;
        this.transactionID = transactionID;
        this.returned = returned;

    }

    public String getReturned() {
        return returned;
    }

    public void setReturned(String returned) {
        this.returned = returned;
    }


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }

    public String getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(String returnDate) {
        ReturnDate = returnDate;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

}
