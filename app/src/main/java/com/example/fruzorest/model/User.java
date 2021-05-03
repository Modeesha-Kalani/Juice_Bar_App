package com.example.fruzorest.model;

public class User {
    private String username;
    private String email;
    private String mobile;
    private String password;
    private String firstname;
    private String lastname;
    private String role;
    private String nic;
    private int userlevel;
    private int status;
    /*

     * 1 - Super Admin
     * 2 - Admin
     * 3 - Customer

     *  1 = active
     *  0 = inactive
     * */

    public User() {
    }

    public User(String username, String email, String mobile, String password, String firstname, String lastname, String role, String nic, int userlevel, int status) {
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.nic = nic;
        this.userlevel = userlevel;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public int getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(int userlevel) {
        this.userlevel = userlevel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
