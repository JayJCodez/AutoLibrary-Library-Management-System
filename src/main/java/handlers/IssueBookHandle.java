package handlers;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.mail.MessagingException;
import com.itextpdf.text.DocumentException;
import Alerts.NotificationHandler;
import Connection.DBConnection;
import Mailer.Mail;
import PdfReportGenerator.ReportGenerator;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


public class IssueBookHandle {

    @FXML
    private TextField BookIDTxt;

    @FXML
    private DatePicker CollectionDateBox;

    @FXML
    private Label ErrorLabel;

    @FXML
    private Label IssueBookbtn;

    @FXML
    private DatePicker ReturnDateBox;

    @FXML
    private Label SearchButton;

    @FXML
    private Label UsernameLabel;

    @FXML
    private TextField UsernameTxt;

    @FXML
    private Label bookNameLbl;

    @FXML
    private Label searchUserBTN;

    @FXML
    private ComboBox<LocalTime> timepicker;

    Connection conn;

    public IssueBookHandle() throws SQLException{

        this.conn = DBConnection.getInstance().connectingDB();

    }

    @FXML
    void FindBook(MouseEvent event) throws SQLException {

        String BookID = BookIDTxt.getText();
        int BookIDInt = Integer.parseInt(BookID);

        if(BookIDInt == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Book ID");
            alert.showAndWait();
            return;
        }


        String FIND_BOOK = "SELECT * FROM LibraryBooks where BookID = '"+BookIDInt+"'";


        PreparedStatement pstmtFindBook = conn.prepareStatement(FIND_BOOK);
        ResultSet F = pstmtFindBook.executeQuery();

        if (F.next()) {

            String BookName = F.getString(2);
            bookNameLbl.setText(BookName);

        }

    }

    @FXML
    void IssueBook(MouseEvent event) throws DocumentException, IOException, MessagingException, InterruptedException {

        try {
            String BookID = BookIDTxt.getText();
            int BookIDInt = Integer.parseInt(BookID);
            String Username = UsernameTxt.getText();

            LocalDate Collection_Date = CollectionDateBox.getValue();
            LocalDate ReturnDate = ReturnDateBox.getValue();

            if(BookIDInt == 0 || Username == null || Collection_Date == null || ReturnDate == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please complete the form");
                alert.showAndWait();
                return;
            }


            // Timestamp returnTimestamp = Timestamp.valueOf(ReturnDate.atStartOfDay());

            // Timestamp.valueOf(CollectionDate.atStartOfDay());


            // Timestamp collection_date = Timestamp.valueOf(CollectionDate.toLocalDa)




            String ISSUEBOOK = "insert into USERTRANSACTIONS (BookID, BookName, Username, Collection_Date, Return_Date) values  (?, ?, ?, ?, ?)";
            String CHECK_BOOK_AVAILABILITY = "SELECT * FROM USERTRANSACTIONS where BookID = '"+BookIDInt+"';";
            String BookName = "SELECT * FROM LibraryBooks WHERE BookID = "+BookID+";";
            PreparedStatement pstmt = conn.prepareStatement(ISSUEBOOK);
            PreparedStatement pstmtCheckAvailability = conn.prepareStatement(CHECK_BOOK_AVAILABILITY);
            PreparedStatement book_name = conn.prepareStatement(BookName);






            ResultSet Q = book_name.executeQuery();

            ResultSet R = pstmtCheckAvailability.executeQuery();

            if (R.next()) {
                ErrorLabel.setText("Sorry....this book is currently unavailable");
            }else{

                pstmt.setInt(1, BookIDInt);
                pstmt.setString(2, bookNameLbl.getText());
                pstmt.setString(3, Username);
                pstmt.setDate(4, java.sql.Date.valueOf(Collection_Date));
                pstmt.setDate(5, java.sql.Date.valueOf(ReturnDate));

                pstmt.executeUpdate();

                if (Q.next()) {
                    String book_Name = Q.getString("BookName");
                    new NotificationHandler().issueBookSuccess(book_Name, Username, ReturnDate.toString());





                    String info_query = "SELECT * FROM USERS WHERE Username = '"+Username+"';";
                    PreparedStatement ps = conn.prepareStatement(info_query);

                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {

                        String email = rs.getString("Email");
                        String first_line_addy = rs.getString("House_Number_Street_Name");
                        String city = rs.getString("City");
                        String postcode = rs.getString("Postcode");
                        String first_name = rs.getString("First_Name");

                        ReportGenerator.getInstance().generate(first_name, book_Name, first_line_addy, city, postcode, Username);


                        //    TimeUnit.SECONDS.sleep(2);



                        //Here the Mailer class is calls so as to initiate the function of sending an email with the set contents.

                        Mail mail = new Mail();

                        mail.setupServerProperties();

                        mail.invoiceDraft(email);

                        mail.sendInvoiceEmail();;
                    }
                }

                ErrorLabel.setText("Successfully issued this Book to "+Username+" !!!");

            }

        } catch (SQLException e) {
            System.err.println(e);
            ErrorLabel.setText("Error");
        }

    }

    @FXML
    void findUser(MouseEvent event) throws SQLException {



        if(UsernameTxt.getText() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Username");
            return;
        }

        String check_user = "SELECT * FROM USERS where Username = '"+UsernameTxt.getText()+"';";
        PreparedStatement pstmt = conn.prepareStatement(check_user);

        ResultSet R = pstmt.executeQuery();

        if (R.next()) {

            String user = R.getString("First_Name");
            UsernameLabel.setText(user);

        }else{

            UsernameLabel.setText("User does not exist!");

        }

    }




}
