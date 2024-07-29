package admin_functions;

import Connection.DBConnection;
import handlers.AdminHomeHandle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class Login_Register_Functions {

    public Connection conn;

    public String Authotp;

    public Login_Register_Functions() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }

    public int sign_in(String username, String password) throws SQLException {


        //Handling Sign in Button event




        //Preparing sql query in string format so it is ready for execution.

        String findName = "select * from USERS where Email ='"+username+"' OR Username ='"+username+"' AND Password = '"+password+"';";
        String query = "SELECT * FROM ADMINUSERS WHERE Email = '"+username+"' AND Password = '"+password+"';";


        PreparedStatement st=conn.prepareStatement(findName);


        try {

            //Executing query to find out if the potential user is an admin or not.

            PreparedStatement check_user_type = conn.prepareStatement(query);

            ResultSet admin = check_user_type.executeQuery();

            // Finding First Name of the user logging into the system.

            ResultSet rs=st.executeQuery();


            if (rs.next()) {


                if (rs.getString("Password").matches(password)){


                    // Storing the name of the user in a String

                    String received = rs.getString("First_Name");

//                    user_name = rs.getString("Username");

                    String Balance = Integer.toString(rs.getInt("Balance"));

//                    name = received;
//
//                    user_balance = Balance;


                    System.out.println("Logged in.....");

                    //Condition to redirect Admin Users to their seperate interface.


                }else{


                    System.out.println("Logged Unsuccessful.....");

                }


            }else if (admin.next()) {

                //Prepring the admin name for the function to set it as an instance in the Home page.
                if (admin.getString("Password").matches(password)){


                    String adminName = admin.getString("First_Name");
                    String adminID = Integer.toString(admin.getInt("Employee_ID"));


                    System.out.println("Logged in.....");

                    String update_status = "UPDATE ADMINUSERS SET [status] = 1 WHERE Employee_ID = ?;";

                    PreparedStatement ps = conn.prepareStatement(update_status);

                    ps.setInt(1, Integer.parseInt(adminID));

                    ps.executeUpdate();



                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getClassLoader().getResource("AdminFXMLPages/Home.fxml"));
                    Parent root = loader.load();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Home");
                    AdminHomeHandle adminUser = loader.getController();
                    adminUser.setAdminContext(adminName, adminID);

                }else{

                    System.out.println("Login Unsuccessful.....");

                }



            }
            else{

                // Handling general exception for wrong credentials
                System.out.println("Something Went Wrong");

            }



        } catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return 0;

    }

    public int send_code(String email) throws MessagingException {

        int otp;

        Random random = new Random();

        int min = 1000000;
        int max = 9999999;

        otp = random.nextInt(max-min)+min;

        Authotp = Integer.toString(otp);

        if (email != null){

            System.out.println("Email seen. Email sent");

        }else {

            System.out.println("Email is null");
        }



        return otp;

    }


    public int register_user(String Email, String Username, String FirstName, String LastName, String Password) throws SQLException, FileNotFoundException {

        //Here I am preparing user information for insertion into the database.




        String checkusers = "SELECT * FROM USERS WHERE Email = '"+Email+"' AND Username = '"+Username+"';";

        String query = "Insert into Users (First_Name, Last_Name, Email, Password, Username, profile_pic) values ('"+FirstName+"', '"+LastName+"', '"+Email+"' ,'"+Password+"', '"+Username+"', ?);";



        PreparedStatement registerPreparedStatement = conn.prepareStatement(query);

        PreparedStatement checkPreparedStatement = conn.prepareStatement(checkusers);


        ResultSet userSet = checkPreparedStatement.executeQuery();


        if (!userSet.next()) {


            System.out.println("SUCCESSFUL");



        }else{


            System.out.println("This email already exists");

        }


        return 0;
    }
}
