package com.alisafarzadeh.twittermvvm.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.alisafarzadeh.twittermvvm.Retrofit.BookmarkApi;
import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.Retrofit.MyApi;
import com.alisafarzadeh.twittermvvm.Retrofit.MyRetrofit;
import com.alisafarzadeh.twittermvvm.model.Status;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
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

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<List<Post>> getAllPostObserve()
    {
        MutableLiveData<List<Post>> mutable = new MutableLiveData<>();
        compositeDisposable.add(MessageApi.ShowPostObserve()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.newThread())
        .subscribe(new Consumer<List<Post>>() {
            @Override
            public void accept(List<Post> posts) throws Throwable {
                mutable.setValue(posts);
            }
        }));
        return mutable;
    }
    /*
    MutableLiveData<List<Status>> mutableLiveData = new MutableLiveData<>();
        compositeDisposable.add(bookmarkApi.BookmarkSaveObserve(IDuser,IDpost)
            .observeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.newThread())
            .subscribe(new Consumer<List<Status>>() {
        @Override
        public void accept(List<Status> statuses) throws Throwable {
            mutableLiveData.setValue(statuses);
        }
    }));
    */


}
