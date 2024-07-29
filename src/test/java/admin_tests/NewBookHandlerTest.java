package admin_tests;

import admin_functions.New_Book_Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class NewBookHandlerTest {

    @Test
    void addNewBookTDB() throws SQLException {

        New_Book_Function test = new New_Book_Function();
        boolean result = test.new_book("Library Management Systems", "Jesse Okuji", "300", "Mystery");
        Assertions.assertTrue(result);

    }
}