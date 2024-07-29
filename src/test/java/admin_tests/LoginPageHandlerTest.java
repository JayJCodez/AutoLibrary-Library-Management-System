package admin_tests;

import de.saxsys.javafx.test.JfxRunner;
import admin_functions.Login_Register_Functions;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

@RunWith(JfxRunner.class)
public class LoginPageHandlerTest {

    Login_Register_Functions test = new Login_Register_Functions();

    public LoginPageHandlerTest() throws SQLException {

    }

    @Test
    public void signIn() throws SQLException {

        String username = "JayJCodez";
        String password = "138Jesse.";
        int result = test.sign_in(username, password);
        assertEquals(0, result);
    }


    @Test
    public void sendCodeBTN() throws MessagingException {

        String email = "JayJCodez@gmail.com";
        int result = test.send_code(email);
        assertEquals(Integer.parseInt(test.Authotp), result);


    }



    @Test
    public void registerUser() throws SQLException, FileNotFoundException {

        String FirstName = "JAMES";
        String LastName = "DUNCAN";
        String Email = "james@gmail.com";
        String Password =  "LibraryManagement";
        String Username = "Jimmy";
        int result = test.register_user(Email, Username, FirstName, LastName, Password);
        assertEquals(0, result);

    }
}
