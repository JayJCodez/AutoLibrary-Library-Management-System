package PdfReportGenerator;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



public class ReportGenerator {
    

       
    private static ReportGenerator instance = null;

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    

    public static ReportGenerator getInstance(){
        if(instance == null) {
            instance = new ReportGenerator();
        }
        return instance;
    }


    public ByteArrayOutputStream getBaos() {
        return baos;
    }


    public void setBaos(ByteArrayOutputStream baos) {
        this.baos = baos;
    }


    public void generate(String name, String bookname, String firstlineaddy, String city, String postcode, String user_name_txt) throws FileNotFoundException, DocumentException{

    

    Document doc = new Document();
    PdfWriter.getInstance(doc, new FileOutputStream(new File("invoice.pdf")));




    doc.open();



 

    String reportHeaderContent = "Hello " + name + "," + """
            
        \nIt is my pleasure to confirm that your transaction at the library was successful. Please be sure to keep track of return dates and enjoy the read.

        This is a receipt of this transaction. Please keep this email at least until book is returned in case there is need for future reference.

            """;

    

    //Setting fonts for the PDF page.
 
    Font headerFont = new Font(FontFamily.HELVETICA,20, Font.BOLD);
   
    Font user_info_font = new Font(FontFamily.HELVETICA, 17, Font.BOLD);

    float three_col_width = 190f;

    float fullwidth[] = {three_col_width*3};

    float twocol=300f;

    float twocol150 = twocol + 150f;

    float twocolumnWidth[] = {twocol150,twocol};


    //Creating new table

    PdfPTable table = new PdfPTable(twocolumnWidth);




    //Allowing table to fill width of the page

    table.setWidthPercentage(100);

    Phrase heading = new Phrase("Receipt of Transaction", headerFont);

    //First cell of the column

    PdfPCell TopLeftLabel = new PdfPCell(new Phrase(heading));


  



    //Border property of first cell

    TopLeftLabel.setBorder(0);





    //Creating table to keep company name and heading

    // PdfPTable topleftheader = new PdfPTable(1);

    // PdfPCell leftheader = new PdfPCell(new Phrase(heading));

    // leftheader.addElement(topleftheader);

    // topleftheader.addCell(heading);

    // TopLeftLabel.addElement(leftheader);




    //Second cell of the table

    PdfPCell TopRightLabel = new PdfPCell();


    //Border property of second cell

    TopRightLabel.setBorder(0);





    //Creating a table to insert inside the second cell just defined.

    PdfPTable nestedTable =  new PdfPTable(new float[]{twocol/2,twocol/2});

    nestedTable.setWidthPercentage(100);

    TopRightLabel.addElement(nestedTable);






    //Creating cells for nested info table in cell.
    
    PdfPCell InvoiceNoLBL = new PdfPCell();





    //Making border invisible for header properties

    InvoiceNoLBL.setBorder(0);

    PdfPCell InvoiceNo = new PdfPCell();

    InvoiceNo.setBorder(0);

    PdfPCell InvoiceDateLBL = new PdfPCell();

    InvoiceDateLBL.setBorder(0);

    PdfPCell InvoiceDate = new PdfPCell();

    InvoiceDate.setBorder(0);




    //assigning values to String object to be inserted

    String invoice_no_lbl = "Invoice No.";
    String invoice_no = "93894332";
    String invoice_date_lbl = "Invoice Date:";
    String invoice_date = "12/02/2024";





    //Adding text to nested table

    InvoiceNoLBL.addElement(new Phrase(invoice_no_lbl));




    //Aligning cell text to left side of the page

    InvoiceNo.setHorizontalAlignment(Element.ALIGN_RIGHT);

    InvoiceNo.addElement(new Phrase(invoice_no));

    InvoiceDateLBL.addElement(new Phrase(invoice_date_lbl));





     //Aligning cell text to left side of the page for second row of second column.

    InvoiceDate.setHorizontalAlignment(Element.ALIGN_RIGHT);

    InvoiceDate.addElement(new Phrase(invoice_date));



    //Adding cell 1

    nestedTable.addCell(InvoiceNoLBL);
    nestedTable.addCell(InvoiceNo);
    nestedTable.addCell(InvoiceDateLBL);
    nestedTable.addCell(InvoiceDate);
    

    //Adding cells assigned for header to the table representing that section.

    table.addCell(TopLeftLabel);
    table.addCell(TopRightLabel);

    //Measurements for spacing and other relative fucntion paramter requests

    float f = 50f;



    //Creating spacing after table

    table.setSpacingAfter(f);

    TopRightLabel.addElement(nestedTable);

    Paragraph content = new Paragraph(reportHeaderContent);




    //Adding a border-line to the pdf document
    
    PdfPTable border = new PdfPTable(fullwidth);



    //Setting the table width to spread across the page

    border.setWidthPercentage(100);





    
    //Creating cell to add to the table

    PdfPCell cell = new PdfPCell();




    //Creating horizontal line below cells at the top of the page.

    cell.setBorder(1);
    cell.setBorderColorBottom(BaseColor.CYAN);



    
    //Adding cell that containes the border
    border.addCell(cell);






    //Adding the second segment of the page 

    PdfPTable user_information = new PdfPTable(2);
 


    

    //Creating space between itself and the border line

    user_information.setSpacingBefore(f);



    //Setting table to full width

    user_information.setWidthPercentage(100);


    

    //Creating all phrases for this component of the pdf
    
    Phrase bookInformationlbl = new Phrase("""
            Book Information

            """, user_info_font);
    Phrase bookNamelbl = new Phrase("""
        Book Name

            """, user_info_font);
    Phrase libraryInfolbl = new Phrase("""
        Library Information

            """, user_info_font);
    // Phrase libraryName = new Phrase("AutoLibrary");
    Phrase bookNameTXT = new Phrase(bookname + """
        

            """);
    Phrase libraryNameTXT = new Phrase("""
        Library Address

            """, user_info_font);
    Phrase libraryAddress = new Phrase("""
        39 Manor Court,
        Newbiggin-By-The-Sea,
        NE64 6HF

            """); 
    Phrase membershipInfo = new Phrase("""
        Membership Information

            """, user_info_font);
    Phrase userAddresslbl = new Phrase("""
        Member Address

            """, user_info_font);
    Phrase userAddress = new Phrase(firstlineaddy + "\n" + city + "\n" + postcode + """
    
            """);
    Phrase usernameLbl = new Phrase("""
        Username

            """, user_info_font);
    Phrase username = new Phrase(user_name_txt + """

            """);



    //Due to the access to a lower version of the API I have to create some objects myself.

    PdfPCell emptyCell = new PdfPCell(new Phrase(""));
    emptyCell.setBorder(0);



    PdfPCell booknameLblCell = new PdfPCell(bookNamelbl);

    booknameLblCell.setBorder(0);



    PdfPCell bookNameTxtCell = new PdfPCell(bookNameTXT);

    bookNameTxtCell.setBorder(0);




    PdfPCell member_info_Cell = new PdfPCell(membershipInfo);

    member_info_Cell.setBorder(0);




    PdfPCell user_address_lbl_Cell = new PdfPCell(userAddresslbl);

    user_address_lbl_Cell.setBorder(0);




    PdfPCell user_address_txt_Cell = new PdfPCell(userAddress);

    user_address_txt_Cell.setBorder(0);


    


    // Creating tables going into this component 

    PdfPTable bookinfo = new PdfPTable(1);
    PdfPCell bookinfoCell = new PdfPCell(bookinfo);
    bookinfoCell.setBorder(0);
    bookinfo.addCell(booknameLblCell);
    bookinfo.addCell(bookNameTxtCell);




    PdfPTable libraryInfoTxt = new PdfPTable(1);

    PdfPCell libraryinfoCell = new PdfPCell(libraryInfoTxt);

    libraryinfoCell.setBorder(0);



    PdfPCell libnameTxt = new PdfPCell(libraryNameTXT);

    libnameTxt.setBorder(0);




    PdfPCell libaddressTxt = new PdfPCell(libraryAddress);

    libaddressTxt.setBorder(0);





    PdfPCell libnameCell = new PdfPCell(libnameTxt);

    libnameCell.setBorder(0);




    PdfPCell libaddressCell = new PdfPCell(libaddressTxt);

    libaddressCell.setBorder(0);
    libraryInfoTxt.addCell(new PdfPCell(libnameCell));
    libraryInfoTxt.addCell(new PdfPCell(libaddressCell));


    

    PdfPTable usernameinfo = new PdfPTable(1);

    PdfPCell user_info_Cell = new PdfPCell(usernameinfo);

    user_info_Cell.setBorder(0);

    


    PdfPCell username_lbl_Cell = new PdfPCell(usernameLbl);

    username_lbl_Cell.setBorder(0);



    PdfPCell username_txt = new PdfPCell(username);

    username_txt.setBorder(0);
    usernameinfo.addCell(new PdfPCell(username_lbl_Cell));
    usernameinfo.addCell(new PdfPCell(username_txt));



    //Inserting the book information label into the cell so it can be styled.
    PdfPCell book_info_lbl_cell = new PdfPCell(bookInformationlbl);
    book_info_lbl_cell.setBorder(0);




    //Inserting the library info label into the cell so it can be styled.
    PdfPCell libraryinfolblCell = new PdfPCell(libraryInfolbl);
    libraryinfolblCell.setBorder(0);



    //Adding the cell containing the label book name to the table
    user_information.addCell(book_info_lbl_cell);
    user_information.addCell(libraryinfolblCell);

    user_information.addCell(bookinfoCell);
    user_information.addCell(libraryinfoCell);

    user_information.addCell(member_info_Cell);
    user_information.addCell(emptyCell);

    user_information.addCell(user_address_lbl_Cell);
    user_information.addCell(emptyCell);

    user_information.addCell(user_address_txt_Cell);
    user_information.addCell(emptyCell);

    user_information.addCell(user_info_Cell);
    user_information.addCell(emptyCell);




     //Adding a border-line to the pdf document
    
    PdfPTable border2 = new PdfPTable(fullwidth);




    //Setting the table width to spread across the page
    
    border2.setWidthPercentage(100);

    float gap = 10f;

    border2.setSpacingBefore(gap);



        
    //Creating cell to add to the table
    
    PdfPCell bordercell = new PdfPCell();



    
    //Creating horizontal line below cells at the top of the page.
    
    bordercell.setBorder(1);
    bordercell.setBorderColorBottom(BaseColor.CYAN);
        
    //Adding cell that containes the border
    border2.addCell(bordercell);


    //Setting up last sector of the page for Terms and Conditions

    Phrase termsAndConditions = new Phrase("Terms and Conditions", user_info_font);

    PdfPTable t_and_c = new PdfPTable(1);

    t_and_c.setWidthPercentage(100);

    float smallspace = 30f;

    t_and_c.setSpacingBefore(smallspace);

    t_and_c.setSpacingAfter(smallspace);

    PdfPCell tandc = new PdfPCell(termsAndConditions);

    tandc.setBorder(0);

    t_and_c.addCell(tandc);


    

    //Creating terms and conditions lsit.

    List termsandcondList = new List(true);

    termsandcondList.add("Books issued must be returned before date discussed to avoid additional charge of £ 5 per day.");




    //Adding all doc elements in order of structure.


    doc.add(table);

    doc.add(border);

    doc.add(user_information);

    doc.add(border2);

    doc.add(new Paragraph("""
            
            """));

    doc.add(content);

    doc.add(border);

    doc.add(t_and_c);

    doc.add(termsandcondList);

    //Closing document 

    doc.close();

    setBaos(baos);


}
}
