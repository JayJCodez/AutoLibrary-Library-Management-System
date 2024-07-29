package Model;

public class AdminUserModel {


    private static AdminUserModel instance = null; 

    public static String name;

    public static String adminID;


    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        AdminUserModel.adminID = adminID;
    }

    public static AdminUserModel getInstance(){
        if(instance == null) {
            instance = new AdminUserModel(name);
        }
        return instance;

    }

    public AdminUserModel(String name) {
        AdminUserModel.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        AdminUserModel.name = name;
    }
    

}
