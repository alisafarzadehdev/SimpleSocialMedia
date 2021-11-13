package com.alisafarzadeh.twittermvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alisafarzadeh.twittermvvm.Retrofit.MyApi;
import com.alisafarzadeh.twittermvvm.Retrofit.MyRetrofit;
import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.model.Save;
import com.alisafarzadeh.twittermvvm.model.Status;
import com.alisafarzadeh.twittermvvm.repository.Repository;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyViewModel extends ViewModel {

    Repository repository = new Repository();


    /*
    public LiveData<List<Post>> getAllPostViewModel(){
        return repository.getMessageLiveData();
    }
    */

    public LiveData<List<Post>> getMyPostViewModel(String id){
        return repository.getMyMessageLiveData(id);
    }


    private MutableLiveData<List<Post>> userLiveData = new MutableLiveData<>();

    public LiveData<List<Post>> getUser() {
        return userLiveData;
    }


    public LiveData<List<Post>> getAllPostObserveViewModel(){
        return repository.getAllMessageLiveData();
    }

    public LiveData<List<Status>> SendPostObserveViewModel
            (String media,String title,String content,int user,int category){
        return repository.SendPostObservLiveData(media, title, content, user, category);
    }



    public LiveData<List<Post>> MyMessageViewModel(String id)
    {
        return repository.MyMessageLiveData(id);
    }


    public LiveData<List<Post>> ShowSavePostViewModel(int id)
    {
        return repository.ShowSavePostRepo(id);
    }

    public LiveData<List<Post>> ShowWithCategoryViewModel(int cat)
    {
        return repository.ShowWithPost(cat);
    }

}

