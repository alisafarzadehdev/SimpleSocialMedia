package com.alisafarzadeh.twittermvvm.Retrofit;

import com.alisafarzadeh.twittermvvm.model.Category;
import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.model.Status;
import com.alisafarzadeh.twittermvvm.model.UserId;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyApi {

    /*
    @GET("getmessage.php")
    Call<Model> weatherGetter(
            @Query("q") String query,
            @Query("appid") String appId
    );

    */

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
    Call<List<Status>> BookmarkSave( @Field("Id_user") String user,
                               @Field("Id_post") String post);

    @GET("ShowCategory.php")
    Call<List<Category>> ShowCategory();

    @GET("ShowPost.php")
    Call<List<Post>> ShowPost();


    @FormUrlEncoded
    @POST("SendPost.php")
    Call<List<Status>> SendPost(
            @Field("Media") String media,
            @Field("Title") String title,
            @Field("Content") String content,
            @Field("User") int user,
            @Field("Category") int category);



    @FormUrlEncoded
    @POST("SignupUserCopy.php")
    Call<String>SignUpp  (@Field("Name") String name,
                               @Field("Username") String username,
                               @Field("Password") String password,
                               @Field("Avatar") String avatar,
                               @Field("Biography") String biography);


    @FormUrlEncoded
    @POST("SignupUser.php")
    Call<List<UserId>> SignUp(@Field("Name") String name,
                              @Field("Username") String username,
                              @Field("Password") String password,
                              @Field("Avatar") String avatar,
                              @Field("Biography") String biography);

    @FormUrlEncoded
    @POST("loginn.php")
    Call<String> Loginn(@Field("username") String username,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("login.php")
    Call<List<UserId>> Login(@Field("username") String username,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("MyPostShow.php")
    Call<List<Post>> MyPostShow(@Field("Id") String id);

}
