package Mailer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import com.itextpdf.text.DocumentException;
import PdfReportGenerator.ReportGenerator;

public class Mail {

    ByteArrayInputStream bais;

    public void setBais(ByteArrayInputStream bais) {
        this.bais = bais;
    }

    public ByteArrayInputStream getBais() {
        return bais;
    }

    protected String fromUser = "jesseokuji123@gmail.com";

    protected String userpw = "nkrz jqxv hiuh vqgl";

    protected String emailHost = "smtp.gmail.com";



    Session newSession = null;
    MimeMessage mimeMessage;
    MimeMessage invoice_mail;
    // public static void main(String[] args) throws Exception {

    //      Mail mail = new Mail();
    //      mail.setupServerProperties();
    //      mail.draftEmail();
    //      mail.sendEmail();
    // }



    public MimeMessage draftEmail(String email, String body) throws AddressException, MessagingException {


       String[] emailRecipients = {""+email+""};
       String emailSubject = "AutoLibrary Verification";
    //    body = "TEST BODY OF MY EMAIL";
       mimeMessage = new MimeMessage(newSession);

       for (int i = 0; i < emailRecipients.length; i++) {

        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRecipients[i]));
        mimeMessage.setSubject(emailSubject);
        // mimeMessage.setText("HELLOOOOO");
        MimeMultipart multipart = new MimeMultipart();

        MimeBodyPart bodyPart = new MimeBodyPart();
        // bodyPart.setContent(body, "hmtl/text");
        // bodyPart.setText("TRIAL");
        bodyPart.setText(body);
        multipart.addBodyPart(bodyPart);
        mimeMessage.setContent(multipart);



       }

       return mimeMessage;

    }

    public MimeMessage invoiceDraft(String email) throws IOException, MessagingException, DocumentException{

        String[] emailRecipients = {""+email+""};
        String emailSubject = "AutoLibrary Verification";
     //    body = "TEST BODY OF MY EMAIL";
        invoice_mail = new MimeMessage(newSession);

        for (int i = 0; i < emailRecipients.length; i++) {

        invoice_mail.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRecipients[i]));
        invoice_mail.setSubject(emailSubject);
         // mimeMessage.setText("HELLOOOOO");
         MimeMultipart multipart = new MimeMultipart();

         MimeBodyPart bodyPart = new MimeBodyPart();



        //Byte Array Data Source (Unused)

        ByteArrayInputStream bais = new ByteArrayInputStream(ReportGenerator.getInstance().getBaos().toByteArray());




        // Creating bodypart containing attachment
;
        MimeBodyPart file = new MimeBodyPart(bais);
        file.setFileName("invoice.pdf");
        DataSource source = new FileDataSource("invoice.pdf");
        file.setDataHandler(new DataHandler(source));





         String body = """

            An invoice has been provided. Please check your attachments to access this.

                """;


         bodyPart.setText(body);
         multipart.addBodyPart(bodyPart);
         multipart.addBodyPart(file);
         invoice_mail.setContent(multipart);




        }


        return invoice_mail;

    }


    public void setupServerProperties() {



        Authenticator authenticator = new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(fromUser, userpw);
            }

        };


        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        // properties.put("mail.smtp.password", "nkrz jqxv hiuh vqgl");
        newSession = Session.getDefaultInstance(properties, authenticator);


};

    public void sendEmail() throws MessagingException {

        Transport transport = newSession.getTransport("smtp");
        transport.connect(emailHost, fromUser, userpw);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
        System.out.println("EMAIL SENT SUCCESSFULLY");
    }

    public void sendInvoiceEmail() throws MessagingException {


        Transport transport = newSession.getTransport("smtp");
        transport.connect(emailHost, fromUser, userpw);
        transport.sendMessage(invoice_mail, invoice_mail.getAllRecipients());
        transport.close();
        System.out.println("EMAIL SENT SUCCESSFULLY");
    }




    public Session getNewSession() {
        return newSession;
    }

    public void setNewSession(Session newSession) {
        this.newSession = newSession;
    }

    public MimeMessage getMimeMessage() {
        return mimeMessage;
    }

    public void setMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    public MimeMessage getInvoice_mail() {
        return invoice_mail;
    }

    public void setInvoice_mail(MimeMessage invoice_mail) {
        this.invoice_mail = invoice_mail;
    }
}
