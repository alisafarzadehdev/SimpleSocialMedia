package com.alisafarzadeh.twittermvvm.Retrofit;

import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.model.Status;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostApi {

    @GET("ShowPost.php")
    Observable<List<Post>> ShowPostObserve();

    @FormUrlEncoded
    @POST("SendPost.php")
    Call<List<Status>> SendPost(
            @Field("Media") String media,
            @Field("Title") String title,
            @Field("Content") String content,
            @Field("User") int user,
            @Field("Category") int category);

    @FormUrlEncoded
    @POST("SendPost.php")
    Observable<List<Status>> SendPostObserv(
            @Field("Media") String media,
            @Field("Title") String title,
            @Field("Content") String content,
            @Field("User") int user,
            @Field("Category") int category);


    @FormUrlEncoded
    @POST("MyPostShow.php")
    Call<List<Post>> MyPostShow(@Field("Id") String id);

    @FormUrlEncoded
    @POST("MyPostShow.php")
    Observable<List<Post>> MyPostShowObserv(@Field("Id") String id);


}
