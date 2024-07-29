package Model;


public class UserContextModel {


    private String username;

    private static UserContextModel instance = null; 

    private static String name;

    private static String balance;
    
    private int userid;





    public static void setInstance(UserContextModel instance) {
        UserContextModel.instance = instance;
    }


    public static UserContextModel getInstance(){
        if(instance == null) {
            instance = new UserContextModel();
        }
        return instance;

    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getBalance() {
        return balance;
    }


    public void setBalance(String balance) {
        UserContextModel.balance = balance;
    }

    public String getName() {
     

        return name;
    }


    public  void setName(String name) {
        UserContextModel.name = name;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
