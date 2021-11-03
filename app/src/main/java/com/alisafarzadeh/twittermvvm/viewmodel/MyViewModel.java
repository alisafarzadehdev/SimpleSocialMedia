package com.alisafarzadeh.twittermvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alisafarzadeh.twittermvvm.Retrofit.MyApi;
import com.alisafarzadeh.twittermvvm.Retrofit.MyRetrofit;
import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.repository.Repository;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyViewModel extends ViewModel {

    Repository repository = new Repository();

    public LiveData<List<Post>> getAllPostViewModel(){
        return repository.getMessageLiveData();
    }
    public LiveData<List<Post>> getMyPostViewModel(String id){
        return repository.getMyMessageLiveData(id);
    }


    private MutableLiveData<List<Post>> userLiveData = new MutableLiveData<>();

    public LiveData<List<Post>> getUser() {
        return userLiveData;
    }

    public LiveData<List<Post>> fetchUser2() {
        MyApi myApi = MyRetrofit.getMyRetrofit().create(MyApi.class);
        myApi.ShowPost().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.body() != null) {
                    userLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                userLiveData.postValue(null);
                t.printStackTrace();
            }
        });

        return userLiveData;
    }




}

