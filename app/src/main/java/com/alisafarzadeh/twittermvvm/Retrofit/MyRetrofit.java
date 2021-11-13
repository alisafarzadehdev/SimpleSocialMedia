package com.alisafarzadeh.twittermvvm.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {

    private static final String TAG = "MyRetrofit";
    static String BaseURLADD = "http://192.168.1.5/MessageApp/";
    static String BaseURL = "http://androidhelp.ir/MessageApp/";


    static String BaseURLIP = "http://94.232.169.217/MessageApp/";

    static Retrofit myRetrofit;

    public static Retrofit getMyRetrofit()
    {

        if (myRetrofit==null)
        {
            Gson gson = new GsonBuilder()
            .setLenient().create();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .readTimeout(30,TimeUnit.SECONDS)
                    .writeTimeout(30,TimeUnit.SECONDS)
                    .build();


            myRetrofit = new Retrofit.Builder()
                    .baseUrl(BaseURLADD)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return myRetrofit;
    }
}
