package admin_tests;

import admin_functions.Return_Book_Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class ReturnBookHandlerTest {

    @Test
    void returnBook() throws SQLException {

        Return_Book_Function test = new Return_Book_Function();
        boolean result = test.return_Book("jayo", "2");
        Assertions.assertTrue(result);


    }
}