package admin_functions;

import Connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Admin_Home_Function {

    Connection conn;

    int admin_ID = 0;

    public Admin_Home_Function() throws SQLException {
        this.conn = DBConnection.getInstance().connectingDB();
    }

    public int sign_out(){

        try {

            PreparedStatement ps = conn.prepareStatement("UPDATE ADMINUSERS SET [status] = 0 WHERE Employee_ID = ?;");

            ps.setInt(1, admin_ID);
            ps.executeUpdate();

            System.out.println("Successfully logged out");
        } catch (Exception e) {
            System.err.println(e);
        }

        return 0;
    }

}
