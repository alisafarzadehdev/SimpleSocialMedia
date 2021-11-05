package com.alisafarzadeh.twittermvvm.Retrofit;

import com.alisafarzadeh.twittermvvm.model.Status;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BookmarkApi {

    @FormUrlEncoded
    @POST("IsBookmark.php")
    Call<List<Status>> IsBookmark(@Field("userid") String IDuser,
                                  @Field("postid") String IDpost);

    @FormUrlEncoded
    @POST("BookmarkRemove.php")
    Call<List<Status>> BookmarkRemove( @Field("Id_user") String user,
                                       @Field("Id_post") String post);


    @FormUrlEncoded
    @POST("BookmarkPost.php")
    Observable<List<Status>> BookmarkSaveObserve(@Field("Id_user") String user,
                                                 @Field("Id_post") String post);

    @FormUrlEncoded
    @POST("BookmarkPost.php")
    Call<List<Status>> BookmarkSave( @Field("Id_user") String user,
                                     @Field("Id_post") String post);


}
