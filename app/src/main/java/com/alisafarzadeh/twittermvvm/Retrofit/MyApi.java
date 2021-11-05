package com.alisafarzadeh.twittermvvm.Retrofit;

import com.alisafarzadeh.twittermvvm.model.Category;
import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.model.Status;
import com.alisafarzadeh.twittermvvm.model.UserId;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
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


    @GET("ShowCategory.php")
    Call<List<Category>> ShowCategory();

    @GET("ShowPost.php")
    Call<List<Post>> ShowPost();

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
