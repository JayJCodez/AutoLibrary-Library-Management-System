package Model;

public class Resources {
    Integer FileID;

    String Filename;

    String FileType;

    String FileExtension;
    
    
    public Integer getFileID() {
        return FileID;
    }

    public void setFileID(Integer fileID) {
        FileID = fileID;
    }

    public String getFilename() {
        return Filename;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }

    public String getFileType() {
        return FileType;
    }

    public void setFileType(String fileType) {
        FileType = fileType;
    }

    public String getFileExtension() {
        return FileExtension;
    }

    public void setFileExtension(String fileExtension) {
        FileExtension = fileExtension;
    }
}
