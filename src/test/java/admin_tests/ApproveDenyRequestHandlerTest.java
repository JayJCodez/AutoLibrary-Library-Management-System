package admin_tests;

import admin_functions.Approve_and_Deny_Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class ApproveDenyRequestHandlerTest {

    @Test
    void approve_request() throws SQLException {

        Approve_and_Deny_Request test = new Approve_and_Deny_Request();
        int result = test.approve("2",5, "4");
        Assertions.assertEquals(5, result);
    }

    @Test
    void deny_request() throws SQLException {

        Approve_and_Deny_Request test = new Approve_and_Deny_Request();
        int result = test.deny("5","5", "4");
        Assertions.assertEquals(10, result);
    }
}