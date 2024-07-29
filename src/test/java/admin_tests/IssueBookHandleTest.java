package admin_tests;

import admin_functions.Issue_Book_Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

class IssueBookHandleTest {

    @Test
    void issueBook() throws SQLException {

        Issue_Book_Function test = new Issue_Book_Function();
        int result = test.issue_Book_Function(String.valueOf(4), "jayo");
        Assertions.assertEquals(result, 0);
    }
}