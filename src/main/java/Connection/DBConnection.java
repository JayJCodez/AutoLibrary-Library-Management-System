package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {



    private static DBConnection instance = null; 

    public Connection connection;

    


    public static DBConnection getInstance(){
        if(instance == null) {
            instance = new DBConnection();
        }
        return instance;

    }


    public Connection getConnection() {
        return connection;
    }




    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection connectingDB() throws SQLException{

         String server = "localhost";
         String port = "3306";
         String databse = "System";
         String username = "root";
         String password = "138Jesse.";

        return connection = DriverManager.getConnection("jdbc:mysql://" + server + ":" + port + "/" + databse, username, password);

    //     String server = "autolibrarylms.database.windows.net";
    //     // String port = "3306";
    //     String databse = "LibraryDB";
    //     String username = "JayJCodez";
    //     String password = "138Jesse.";

    //     String connString = 
    //     String.format(  "jdbc:sqlserver://" + server + ";" 
    //     + "database=" + databse + ";" 
    //     + "user=" + username + ";"
    //     + "password=" + password + ";"
    //     + "encrypt=true;" 
    //     + "trustServerCertificate=false;" 
    //     + "loginTimeout=30;", server, databse, username, password);

    //    return connection = DriverManager.getConnection(connString);

    }


}
