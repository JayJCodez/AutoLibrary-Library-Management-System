package Model;

import java.io.InputStream;

import java.sql.SQLException;




public class ResourceSearch {
    
    String Name;

    String Type;

    InputStream Preview;

    String Extension;


    
    public ResourceSearch(String name, String type, InputStream preview, String extension) {
        Name = name;
        Type = type;
        Preview = preview;
        Extension = extension;
    }

      public String getExtension() {
        return Extension;
    }


    public void setExtension(String extension) {
        Extension = extension;
    }
  
    public InputStream getPreview() throws SQLException {

        return Preview;
    }

    public void setPreview(InputStream preview) {
        Preview = preview;
    }
    
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }


}
