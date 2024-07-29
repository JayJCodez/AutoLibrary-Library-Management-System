package Model;

public class PaymentTransactionsModel {

    private int id;

    public PaymentTransactionsModel(String date, int amount, int id) {
        this.date = date;
        this.amount = amount;
        this.id = id;
    }

    private int amount;

    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}
