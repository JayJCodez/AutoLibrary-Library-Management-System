package Model;

public class ResourceAsyncSearch {
    
    String File_Name;

    String File_Type;

    

    public ResourceAsyncSearch(String file_Name, String file_Type) {
        File_Name = file_Name;
        File_Type = file_Type;
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
}
