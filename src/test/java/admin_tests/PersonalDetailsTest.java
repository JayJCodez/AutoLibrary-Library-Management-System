package admin_tests;

import admin_functions.Update_Account_Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;

class PersonalDetailsTest {

    Update_Account_Function test = new Update_Account_Function();

    PersonalDetailsTest() throws SQLException {
    }

    @Test
    void deleteAccount() throws SQLException {

        boolean result = test.delete_account("6");
        Assertions.assertTrue(result);

    }

    @Test
    void updateAccount() throws SQLException, FileNotFoundException {


        int result = test.update_account_function(2, "James", "Duncan", "jayo@jo.com", "07731349783", "Buckingham Road", "London", "SE9 6BD");
        Assertions.assertEquals(2, result);
    }
}