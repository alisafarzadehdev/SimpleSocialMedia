package com.alisafarzadeh.twittermvvm.Retrofit;

import android.os.Build;

import com.alisafarzadeh.twittermvvm.Util.MyConnect;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {

    private static final String TAG = "MyRetrofit";


    static String BaseURLIP = "http://94.232.169.217/MessageApp/";

    static Retrofit myRetrofit;

    public static Retrofit getMyRetrofit()
    {

        if (myRetrofit==null)
        {
            List<ConnectionSpec> tlsSpecs = Arrays.asList(ConnectionSpec.CLEARTEXT,ConnectionSpec.MODERN_TLS);

            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                tlsSpecs = Arrays.asList(ConnectionSpec.CLEARTEXT,ConnectionSpec.COMPATIBLE_TLS);
            }

            Gson gson = new GsonBuilder()
            .setLenient().create();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .readTimeout(30,TimeUnit.SECONDS)
                    .writeTimeout(30,TimeUnit.SECONDS)
                    .connectionSpecs(tlsSpecs)
                    .build();


            myRetrofit = new Retrofit.Builder()
                    .baseUrl(MyConnect.AddressLocal)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return myRetrofit;
    }
}
