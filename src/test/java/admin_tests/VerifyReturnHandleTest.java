package admin_tests;

import admin_functions.Verify_Return_Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class VerifyReturnHandleTest {

    @Test
    void verifyReturn() throws SQLException {

        Verify_Return_Function test = new Verify_Return_Function();
        boolean result = test.verify_return(0,"");
        Assertions.assertTrue(result);
    }
}