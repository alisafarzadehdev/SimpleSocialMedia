package com.alisafarzadeh.twittermvvm.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.alisafarzadeh.twittermvvm.Retrofit.BookmarkApi;
import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.Retrofit.MyApi;
import com.alisafarzadeh.twittermvvm.Retrofit.MyRetrofit;
import com.alisafarzadeh.twittermvvm.model.Status;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class GetMessageResponse {

    MyApi MessageApi = MyRetrofit.getMyRetrofit().create(MyApi.class);
    BookmarkApi bookmarkApi = MyRetrofit.getMyRetrofit().create(BookmarkApi.class);

    //AllMessage
    public MutableLiveData<List<Post>> getAllMessageMutableLiveData()
    {
        MutableLiveData<List<Post>> mutableLiveData = new MutableLiveData<>();
        MessageApi.ShowPost().enqueue(new Callback<List<Post>>() {
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

    //MyMessage
    public MutableLiveData<List<Post>> getMyPostMutableLiveData(String id){
        MutableLiveData<List<Post>> mutableLiveData = new MutableLiveData<>();
        MessageApi.MyPostShow(id).enqueue(new Callback<List<Post>>() {
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



    public MutableLiveData<List<Status>> IsBookmark( String IDuser, String IDpost)
    {
        MutableLiveData<List<Status>> mutableLiveData = new MutableLiveData<>();
        bookmarkApi.IsBookmark(IDuser,IDpost).enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                mutableLiveData.setValue(response.body());
            }
            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
    public MutableLiveData<List<Status>> BookmarkRemove( String IDuser, String IDpost)
    {
        MutableLiveData<List<Status>> mutableLiveData = new MutableLiveData<>();
        bookmarkApi.BookmarkRemove(IDuser,IDpost).enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
    public MutableLiveData<List<Status>> BookmarkSave( String IDuser, String IDpost)
    {
        MutableLiveData<List<Status>> mutableLiveData = new MutableLiveData<>();
        bookmarkApi.BookmarkSave(IDuser,IDpost).enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }

}
