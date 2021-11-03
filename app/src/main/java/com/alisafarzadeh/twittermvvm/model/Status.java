package com.alisafarzadeh.twittermvvm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {


    @SerializedName("Status")
    @Expose
    String Status;


    public String getStatus() {
        return Status;
    }

    public void setUser(String user) {
        Status = user;
    }


}
