package com.alisafarzadeh.twittermvvm.Retrofit;

import com.alisafarzadeh.twittermvvm.model.Category;
import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.model.Status;
import com.alisafarzadeh.twittermvvm.model.UserId;
import com.alisafarzadeh.twittermvvm.model.Users;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyApi {

    @GET("test.php")
    Call<List<UserId>> TestUser();


    @FormUrlEncoded
    @POST("Myinfooo.php")
    Call<List<Users>> MyInfo(@Field("id") String name);

    @GET("ShowCategory.php")
    Call<List<Category>> ShowCategory();

    @FormUrlEncoded
    @POST("Signup.php")
    Call<List<UserId>> SignUp(@Field("Name") String name,
                              @Field("Username") String username,
                              @Field("Password") String password,
                              @Field("Avatar") String avatar,
                              @Field("Biography") String biography);

    @FormUrlEncoded
    @POST("login.php")
    Call<String> Login(@Field("username") String username,
                             @Field("password") String password);


    @FormUrlEncoded
    @POST("DeletePost.php")
    Call<List<Status>> DeletePost (@Field("id") int id);


}
