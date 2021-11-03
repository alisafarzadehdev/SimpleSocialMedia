package com.alisafarzadeh.twittermvvm.repository;

import androidx.lifecycle.LiveData;

import com.alisafarzadeh.twittermvvm.model.Post;

import java.util.List;

public class Repository {

    GetMessageResponse getMessageResponse = new GetMessageResponse();
    public LiveData<List<Post>> getMessageLiveData()
    {
        return getMessageResponse.getAllMessageMutableLiveData();
    }
    public LiveData<List<Post>> getMyMessageLiveData(String id)
    {
        return getMessageResponse.getMyPostMutableLiveData(id);
    }



}
