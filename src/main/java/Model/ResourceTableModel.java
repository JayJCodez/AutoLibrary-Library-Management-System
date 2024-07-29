package Model;

public class ResourceTableModel {

    int File_ID;

    String File_Name;

    String File_Type;

    String File_Extension;


    public ResourceTableModel(int file_ID, String file_Name, String file_Type, String file_Extension) {
        File_ID = file_ID;
        File_Name = file_Name;
        File_Type = file_Type;
        File_Extension = file_Extension;
    }

    public int getFile_ID() {
        return File_ID;
    }

    public void setFile_ID(int file_ID) {
        File_ID = file_ID;
    }

    public String getFile_Name() {
        return File_Name;
    }

    public void setFile_Name(String file_Name) {
        File_Name = file_Name;
    }

    public String getFile_Type() {
        return File_Type;
    }

    public void setFile_Type(String file_Type) {
        File_Type = file_Type;
    }

    public String getFile_Extension() {
        return File_Extension;
    }

    public void setFile_Extension(String file_Extension) {
        File_Extension = file_Extension;
    }
    
}
