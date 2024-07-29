package admin_functions;

import Connection.DBConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update_Account_Function {

    public Connection conn;

    public Update_Account_Function() throws SQLException {

        this.conn = DBConnection.getInstance().connectingDB();

    }

    public boolean delete_account(String adminid) throws SQLException {

        String query = "DELETE FROM ADMINUSERS WHERE Employee_ID = '"+adminid+"';";



        PreparedStatement deleteStatement = conn.prepareStatement(query);

        int result = deleteStatement.executeUpdate();

        if (result == 1) {

            System.out.println("account deleted successfully");
            return true;
        }else{
            return false;
        }
    }

    public int update_account_function(int id, String firstname, String lastname, String Email, String phone, String street, String City, String Postcode) throws SQLException, FileNotFoundException {

        String first_name = null;
        String last_name = null;
        String email = null;
        String phone_number = null;
        String road = null;
        String city = null;
        String postcode = null;
        int adminid = 0;

        first_name = firstname;
        last_name = lastname;
        email = Email;
        phone_number = phone;
        road = street;
        city = City;
        postcode = Postcode;
        adminid = id;


        String updateDetailsQuery = "UPDATE ADMINUSERS SET First_Name = '"+first_name+"', Last_Name = '"+last_name+"', Email = '"+email+"', Phone_No = '"+phone_number+"', House_Number_Street_Name = '"+road+"', City = '"+city+"', Postcode = '"+postcode+"', profile_pic = ? WHERE Employee_ID = "+ adminid+";";

        PreparedStatement ps = conn.prepareStatement(updateDetailsQuery);

        FileInputStream fis = new FileInputStream(new File("src/test/java/functions/icons8-star-96.png"));

        ps.setBinaryStream(1, fis);

        ps.execute();

        System.out.println("updated account successfully");

        return 2;
    }
}
