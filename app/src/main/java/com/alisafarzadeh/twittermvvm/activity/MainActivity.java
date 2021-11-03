package com.alisafarzadeh.twittermvvm.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.alisafarzadeh.twittermvvm.R;
import com.alisafarzadeh.twittermvvm.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    ActivityMainBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        setSupportActionBar(binding.toolbarid);

        navController = Navigation.findNavController(this,R.id.fragmentMMain);
        NavigationUI.setupWithNavController(binding.MainNavigationView,navController);
        NavigationUI.setupActionBarWithNavController(this,navController,binding.Maindrawerlayout);

        /*
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.Maindrawerlayout, binding.toolbarid, R.string.Acc, R.string.Can);
        binding.Maindrawerlayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        sharedpreferences = getSharedPreferences(getPackageName()+"MySaveUser", Context.MODE_PRIVATE);

         */
        //binding.textView.setText(sharedpreferences.getInt("ID",-1)+"");
    }




    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController,binding.Maindrawerlayout);
    }


}