package com.alisafarzadeh.twittermvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.model.Status;
import com.alisafarzadeh.twittermvvm.repository.Repository;

import java.util.List;

public class BookmarkViewModel extends ViewModel {

    Repository repository = new Repository();

    public LiveData<List<Status>> getIsBookmarkViewModel(String user , String post){
        return repository.IsBookmarkRepo(user,post);
    }

    public LiveData<List<Status>> getBookmarkRemoveViewModel(String user , String post){
        return repository.BookmarkSaveRepo(user,post);
    }

    public LiveData<List<Status>> getBookmarkSaveRepoViewModel(String user , String post){
        return repository.BookmarkRemoveRepo(user,post);
    }

}
