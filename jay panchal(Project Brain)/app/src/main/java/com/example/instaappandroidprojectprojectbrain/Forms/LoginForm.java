package com.example.instaappandroidprojectprojectbrain.Forms;



public class LoginForm {

    private String email;
    private String password;

    public LoginForm() {}

    public LoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        return "email : " + email + "\n" + "password : " + password;
    }
}
