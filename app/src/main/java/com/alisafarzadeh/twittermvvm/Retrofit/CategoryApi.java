package com.alisafarzadeh.twittermvvm.Retrofit;

import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.model.Status;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CategoryApi {
    @FormUrlEncoded
    @POST("ShowPostWithCategory.php")
    Observable<List<Post>> ShowWithCat(@Field("cat") int cat);
}
