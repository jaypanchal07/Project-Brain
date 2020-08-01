package com.example.instaappandroidprojectprojectbrain.Forms;


public class RegisterForm1 {

    private String email;
    private String username;
    private String password;
    private String Name;

    public RegisterForm1() {}

    public RegisterForm1(String email, String username, String password, String Name) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.Name = Name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String toString(){
        return "email: " + email + "\npassword: " + password + "\nusername: " + username + "\nName: " + Name;
    }
}
