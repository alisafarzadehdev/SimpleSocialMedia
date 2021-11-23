package com.alisafarzadeh.twittermvvm.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.alisafarzadeh.twittermvvm.Retrofit.BookmarkApi;
import com.alisafarzadeh.twittermvvm.Retrofit.CategoryApi;
import com.alisafarzadeh.twittermvvm.Retrofit.PostApi;
import com.alisafarzadeh.twittermvvm.model.Comment;
import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.Retrofit.MyApi;
import com.alisafarzadeh.twittermvvm.Retrofit.MyRetrofit;
import com.alisafarzadeh.twittermvvm.model.Save;
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


    /*
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
     */


    public MutableLiveData<List<Post>> MyPostResponse(String id){
        PostApi MessageApi = MyRetrofit.getMyRetrofit().create(PostApi.class);
        MutableLiveData<List<Post>> mutable = new MutableLiveData<>();
        compositeDisposable.add(MessageApi.MyPostShowObserv(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(movies -> {
                    if (movies!=null)
                    {
                        mutable.setValue(movies);
                    }
                }));
        return mutable;
    }


    //MyMessage
    public MutableLiveData<List<Post>> getMyPostMutableLiveData(String id){
        PostApi postApi = MyRetrofit.getMyRetrofit().create(PostApi.class);
        MutableLiveData<List<Post>> mutableLiveData = new MutableLiveData<>();
        postApi.MyPostShow(id).enqueue(new Callback<List<Post>>() {
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
        PostApi MessageApi = MyRetrofit.getMyRetrofit().create(PostApi.class);
        MutableLiveData<List<Post>> mutable = new MutableLiveData<>();
        compositeDisposable.add(MessageApi.ShowPostObserve()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
                .subscribe(movies -> {
                    if (movies!=null)
                    {
                        mutable.setValue(movies);
                    }
                }));
        return mutable;
    }


    public MutableLiveData<List<Status>> SendPostObservResponse
            (String media,String title,String content,int user,int category)
    {
        PostApi PostApi = MyRetrofit.getMyRetrofit().create(PostApi.class);
        MutableLiveData<List<Status>> mutable = new MutableLiveData<>();
        compositeDisposable.add(PostApi.SendPostObserv(media, title, content, user, category)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(movies -> {
                    if (movies!=null)
                    {
                        mutable.setValue(movies);
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





    public MutableLiveData<List<Post>> ShowSavePostResponse
            (int id)
    {
        BookmarkApi bookmarkShowApi = MyRetrofit.getMyRetrofit().create(BookmarkApi.class);
        MutableLiveData<List<Post>> mutable = new MutableLiveData<>();
        compositeDisposable.add(bookmarkShowApi.ShowSave(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(movies -> {
                    if (movies!=null)
                    {
                        mutable.setValue(movies);
                    }
                }));
        return mutable;
    }



    public MutableLiveData<List<Post>> ShowWithCategory(int nameCategory)
    {
        CategoryApi categoryApi = MyRetrofit.getMyRetrofit().create(CategoryApi.class);
        MutableLiveData<List<Post>> mutable = new MutableLiveData<>();
        compositeDisposable.add(categoryApi.ShowWithCat(nameCategory)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(posts -> {
            if (posts!=null) {
                mutable.setValue(posts);
            }
        }));
        return mutable;
    }


    public MutableLiveData<List<Comment>> ShowComment(int post)
    {
        MyApi commentapi = MyRetrofit.getMyRetrofit().create(MyApi.class);
        MutableLiveData<List<Comment>> mutable = new MutableLiveData<>();
        compositeDisposable.add(commentapi.ShowComment(post)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(posts -> {
                    if (posts!=null) {
                        mutable.setValue(posts);
                    }
                }));
        return mutable;
    }


    public MutableLiveData<List<Status>> SendComment (int post , int user , String comment)
    {
        MyApi commentapi = MyRetrofit.getMyRetrofit().create(MyApi.class);
        MutableLiveData<List<Status>> mutable = new MutableLiveData<>();
        compositeDisposable.add(commentapi.SendComment(post,user,comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(posts -> {
                    if (posts!=null) {
                        mutable.setValue(posts);
                    }
                }));
        return mutable;
    }

}
