package com.alisafarzadeh.twittermvvm.model;

public class Users {

    String Name ;
    String Username ;
    String Password ;
    String Avatar ;
    String Biography ;

    public Users(String name, String username, String password, String avatar, String biography) {
        Name = name;
        Username = username;
        Password = password;
        Avatar = avatar;
        Biography = biography;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getBiography() {
        return Biography;
    }

    public void setBiography(String biography) {
        Biography = biography;
    }
}
