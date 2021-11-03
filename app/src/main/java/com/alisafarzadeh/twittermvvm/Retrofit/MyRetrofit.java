package com.alisafarzadeh.twittermvvm.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {

    private static final String TAG = "MyRetrofit";
    static String BaseURLADD = "http://192.168.1.5/MessageApp/";
    static Retrofit myRetrofit;

    public static Retrofit getMyRetrofit()
    {
        if (myRetrofit==null)
        {
            Gson gson = new GsonBuilder()
            .setLenient().create();

            myRetrofit = new Retrofit.Builder()
                    .baseUrl(BaseURLADD)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return myRetrofit;
    }
}
