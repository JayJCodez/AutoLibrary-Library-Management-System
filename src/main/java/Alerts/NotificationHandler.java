package Alerts;

import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class NotificationHandler {
    TrayNotification tray = new TrayNotification();


    public void UnsuccessfulLogin(){

        String title = "Unsuccessful Login Attempt";
        String message = "The user credentials entered are not recognized.\nPlease double-check credentials!";


        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.ERROR);
        tray.showAndDismiss(Duration.millis(5000));;

    }


     public void displaySuccessNotification(String name){

        String title = "Successfully logged in...";
        String message = "Welcome to AutoLibrary,    "+name+"";



        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(5000));
    }

    public void registerSuccess(){

        String title = "Successfully Registered Account";
        String message = "Your account has successfully been registered and verified!\nPlease login to activate your account!";



        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(5000));


    }

    public void TopUpSuccess(String amount){


        String title = "Top Up Successful!!";
        String message = " You have successfully Topped up £ "+amount+".00\n\nPlease re - attempt login to see refreshed Balance.";



        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(5000));

    }

    public void issueBookSuccess(String bookName, String username, String date){

        String title = "Book issued successfully";
        String message = " You have successfully issued "+bookName+" \nto "+username+" till "+date+"";



        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(5000));


    }

    public void deletedAccountNotify(String name){

        String title = name + "'s" + " Acoount Deleted";
        String message = "Your account has been successfully\nremoved from our Databases";



        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.INFORMATION);
        tray.showAndDismiss(Duration.millis(5000));


    }

    public void updatedDetails(){

        String title = "Successfully Updated Details";
        String message = "Your details have been updated successfully";



        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(5000));


    }
}
