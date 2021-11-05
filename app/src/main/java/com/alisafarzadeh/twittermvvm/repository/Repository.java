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



    //Bookmark
    public LiveData<List<Status>> IsBookmarkRepo(String user , String post)
    {
        return getMessageResponse.IsBookmark(user,post);
    }
    public LiveData<List<Status>> BookmarkRemoveRepo(String user , String post)
    {
        return getMessageResponse.BookmarkRemove(user,post);
    }
    public LiveData<List<Status>> BookmarkSaveRepo(String user , String post)
    {
        return getMessageResponse.BookmarkSave(user,post);
    }

}
