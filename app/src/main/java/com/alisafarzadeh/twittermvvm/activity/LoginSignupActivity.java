package com.alisafarzadeh.twittermvvm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.alisafarzadeh.twittermvvm.R;

public class LoginSignupActivity extends AppCompatActivity {

    NavController navController;

    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginsignup);

        sharedpreferences = getSharedPreferences(getPackageName()+"MySaveUser", Context.MODE_PRIVATE);

        navController = Navigation.findNavController(this,R.id.fragmentContainerView);
        NavigationUI.setupActionBarWithNavController(this ,navController);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean b = sharedpreferences.getBoolean("IsRegister",false);
        Log.d("cTAG", "onStart: "+b);
        if (b)
        {
            startActivity(new Intent(this,MainActivity.class));
        }
    }

}