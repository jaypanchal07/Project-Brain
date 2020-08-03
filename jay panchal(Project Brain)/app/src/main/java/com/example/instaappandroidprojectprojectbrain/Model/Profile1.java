package com.example.instaappandroidprojectprojectbrain.Model;

import java.util.ArrayList;

public class Profile1 {

    private String email;

    private String username;

    private String name;

    private ArrayList<String> Following;


    public Profile1(){
    }
    public  Profile1(String email, String username, String name){
        this.email = email;
        this.username = username;
        this.name = name;
        this.Following = new ArrayList<> ();
    }

    public ArrayList<String> getFollowing() {
        return Following;
    }

    public void setFollowing(ArrayList<String> following) {
        Following = following;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
