package admin_tests;

import admin_functions.Admin_Home_Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

class AdminHomeHandleTest {

    @Test
    void signout() throws SQLException {
        Admin_Home_Function test = new Admin_Home_Function();
        int result = test.sign_out();
        Assertions.assertEquals(0, result);
    }
}