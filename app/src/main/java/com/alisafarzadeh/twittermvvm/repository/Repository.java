package com.alisafarzadeh.twittermvvm.repository;

import androidx.lifecycle.LiveData;

import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.model.Status;

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

    public LiveData<List<Post>> getAllMessageLiveData()
    {
        return getMessageResponse.getAllPostObserve();
    }



}
