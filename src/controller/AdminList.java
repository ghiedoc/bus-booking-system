/*
 * Model class for the table in the admin control panel
 */
package controller;

/**
 *
 * @author Gillian
 */
public class AdminList {
    private String name;
    private String admin;
    private String password;
    
    public AdminList(String name, String admin){
        this.name = name;
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
    
    
}
