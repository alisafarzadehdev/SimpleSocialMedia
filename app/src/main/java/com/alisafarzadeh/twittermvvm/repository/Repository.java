package com.alisafarzadeh.twittermvvm.repository;

import androidx.lifecycle.LiveData;

import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.model.Save;
import com.alisafarzadeh.twittermvvm.model.Status;

import java.util.List;

public class Repository {

    GetMessageResponse getMessageResponse = new GetMessageResponse();

    /*
    public LiveData<List<Post>> getMessageLiveData()
    {
        return getMessageResponse.getAllMessageMutableLiveData();
    }

     */

    public LiveData<List<Post>> ShowSavePostRepo(int id)
    {
        return getMessageResponse.ShowSavePostResponse(id);
    }


    public LiveData<List<Post>> getMyMessageLiveData(String id)
    {
        return getMessageResponse.getMyPostMutableLiveData(id);
    }

    public LiveData<List<Post>> getAllMessageLiveData()
    {
        return getMessageResponse.getAllPostObserve();
    }

    public LiveData<List<Status>> SendPostObservLiveData
            (String media,String title,String content,int user,int category)
    {
        return getMessageResponse.SendPostObservResponse(media, title, content, user, category);
    }

    public LiveData<List<Post>> MyMessageLiveData(String id)
    {
        return getMessageResponse.MyPostResponse(id);
    }



    public LiveData<List<Post>> ShowWithPost(int category)
    {
        return getMessageResponse.ShowWithCategory(category);
    }

}
