package Model;

public class BookViewModel {
    
    String Book_Name;

    String Publisher;

    String Extension;

    public BookViewModel(String book_Name, String publisher, String extension) {
        Book_Name = book_Name;
        Publisher = publisher;
        Extension = extension;
    }

    public String getExtension() {
        return Extension;
    }

    public void setExtension(String extension) {
        Extension = extension;
    }

    

    public String getBook_Name() {
        return Book_Name;
    }

    public void setBook_Name(String book_Name) {
        Book_Name = book_Name;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }


}
