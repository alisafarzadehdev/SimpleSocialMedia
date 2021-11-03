package com.alisafarzadeh.twittermvvm.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.Retrofit.MyApi;
import com.alisafarzadeh.twittermvvm.Retrofit.MyRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMessageResponse {

    MyApi myApi = MyRetrofit.getMyRetrofit().create(MyApi.class);

    public MutableLiveData<List<Post>> getAllMessageMutableLiveData()
    {
        MutableLiveData<List<Post>> mutableLiveData = new MutableLiveData<>();
        myApi.ShowPost().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.i("Error", "onFailure: " + t.getMessage());
            }
        });
        return mutableLiveData;
    }


    public MutableLiveData<List<Post>> getMyPostMutableLiveData(String id){
        MutableLiveData<List<Post>> mutableLiveData = new MutableLiveData<>();
        myApi.MyPostShow(id).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.i("Error", "onFailure:MyPostShow " + t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
