package admin_tests;

import admin_functions.SendMessage_Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class SendMessageHandleTest {

    @Test
    void sendMessage() throws SQLException {

        SendMessage_Function test = new SendMessage_Function();

        int result = test.sendMessage(
                "Sending an email via Library mail",
                5,
                true,
                4,
                true,
                "Just Saying Hi");

        Assertions.assertEquals(3, result);

    }
}