package admin_functions;

import Connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Approve_and_Deny_Request {

    public Connection conn;

    public Approve_and_Deny_Request() throws SQLException {

        this.conn = DBConnection.getInstance().connectingDB();

    }

    public int approve(String resource_id, int admin_id, String userid) throws SQLException {

        String query = "UPDATE ResourcePermissions\n" + //
                "SET employee_id = "+admin_id+",\n" + //
                "    approval_time = CURRENT_TIMESTAMP,\n" + //
                "    approvalStatus = 'Approved'\n" + //
                "WHERE resourceID = "+resource_id+"AND user_id="+userid+";";

        PreparedStatement ps = conn.prepareStatement(query);

        int result = ps.executeUpdate();

        if (result == 1) {

            return 5;

        }else{

            return 4;

        }

    }


    public int deny(String resource_id, String adminid, String userid) throws SQLException {
        String query = "UPDATE ResourcePermissions\n" + //
                "SET employee_id = "+adminid+",\n" + //
                "    approval_time = CURRENT_TIMESTAMP,\n" + //
                "    approvalStatus = 'Approved'\n" + //
                "WHERE resourceID = "+resource_id+" AND user_id="+userid+";";

        PreparedStatement ps = conn.prepareStatement(query);

        int result = ps.executeUpdate();

        if(result == 1) {


            return 10;

        }else{
            return 0;
        }

    }
}
