package com.alisafarzadeh.twittermvvm.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.alisafarzadeh.twittermvvm.R;
import com.alisafarzadeh.twittermvvm.Retrofit.MyApi;
import com.alisafarzadeh.twittermvvm.Retrofit.MyRetrofit;
import com.alisafarzadeh.twittermvvm.Retrofit.PostApi;
import com.alisafarzadeh.twittermvvm.Util.MyConnect;
import com.alisafarzadeh.twittermvvm.Util.Utils;
import com.alisafarzadeh.twittermvvm.databinding.ActivityMainBinding;
import com.alisafarzadeh.twittermvvm.databinding.LayoutHeaderdrawerBinding;
import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.model.UserId;
import com.alisafarzadeh.twittermvvm.model.Users;
import com.alisafarzadeh.twittermvvm.viewmodel.MyViewModel;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    ActivityMainBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarid);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setSubtitle("");
        navController = Navigation.findNavController(this,R.id.fragmentMMain);
        NavigationUI.setupWithNavController(binding.MainNavigationView,navController);
        NavigationUI.setupActionBarWithNavController(this,navController,binding.Maindrawerlayout);




        NavigationView navigationView = (NavigationView) findViewById(R.id.MainNavigationView);
        View headerView = navigationView.getHeaderView(0);

        //= navigationView.inflateHeaderView(R.layout.layout_headerdrawer);
        ImageView img = headerView.findViewById(R.id.imageViewNavAvatar);
        TextView txt = headerView.findViewById(R.id.textViewNavName);

        sharedpreferences = getSharedPreferences(getPackageName()+"MySaveUser", Context.MODE_PRIVATE);
        int id  = sharedpreferences.getInt("ID",-1);
        MyApi p = MyRetrofit.getMyRetrofit().create(MyApi.class);
        p.MyInfo(id+"").enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                txt.setText(response.body().get(0).getName());

                //Picasso.get().load("http://androidhelp.ir/MessageApp/"+response.body().get(0).getAvatar()).into(img);
                Picasso.get().load(MyConnect.AddressLocal +response.body().get(0).getAvatar()).into(img);
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Log.d("xxx", "onFailure: "+t.getMessage());
            }
        });


    }




    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController,binding.Maindrawerlayout);
    }


}