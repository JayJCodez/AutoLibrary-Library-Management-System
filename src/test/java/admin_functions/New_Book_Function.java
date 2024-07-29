package admin_functions;

import Connection.DBConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class New_Book_Function {

    public Connection conn;

    public New_Book_Function() throws SQLException {

        this.conn = DBConnection.getInstance().connectingDB();

    }

    public boolean new_book(String book_name, String publisher_full_name, String book_price, String genre){


        String BookName = null;
        String Publisher = null;
        String BookPrice = null;
        BookName = book_name;
        Publisher = publisher_full_name;
        BookPrice = book_price;
        int BookPriceInt = Integer.parseInt(BookPrice);

        if(BookName.isEmpty() || Publisher.isEmpty() || BookPrice.isEmpty()){

            System.out.println("Please fill all the fields");

            return false;
        }

        try {



            File imgFile = new File("src/test/java/functions/icons8-star-96.png");
            String extension = imgFile.getName().substring(imgFile.getName().lastIndexOf(".") + 1);



            FileInputStream fis = new FileInputStream(imgFile);



            String ADD_NEW_BOOK = "insert into LibraryBooks (BookName, BookPublisher, BookPrice, BookCover, Book_Extension, genre) values  (?, ?, ?, ?, ?, ?)";
            String CHECK_DUPLICATE_BOOK = "SELECT * FROM LibraryBooks where BookName = '"+BookName+"';";
            PreparedStatement pstmt = conn.prepareStatement(ADD_NEW_BOOK);
            PreparedStatement pstmtCheck = conn.prepareStatement(CHECK_DUPLICATE_BOOK);



            ResultSet R = pstmtCheck.executeQuery();

            if (R.next()) {
                System.out.println("This book is already stored in the database");
            }
            else{


                pstmt.setString(1,BookName);
                pstmt.setString(2, Publisher);
                pstmt.setInt(3, BookPriceInt);
                pstmt.setBinaryStream(4, fis);
                pstmt.setString(5, extension);
                pstmt.setString(6, genre);

                pstmt.executeUpdate();

                System.out.println("Book Added Successfully to your Database");

            }
        } catch (SQLException e ) {
            System.err.println(e);
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        return true;
    }

}
