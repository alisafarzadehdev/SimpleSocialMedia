package com.alisafarzadeh.twittermvvm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserId {

    @SerializedName("UserID")
    @Expose
    String User;


    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

}
