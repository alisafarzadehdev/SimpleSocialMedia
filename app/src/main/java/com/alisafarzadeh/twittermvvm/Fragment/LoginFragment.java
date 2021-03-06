package com.alisafarzadeh.twittermvvm.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alisafarzadeh.twittermvvm.activity.LoginSignupActivity;
import com.alisafarzadeh.twittermvvm.model.UserId;
import com.alisafarzadeh.twittermvvm.R;
import com.alisafarzadeh.twittermvvm.Retrofit.MyApi;
import com.alisafarzadeh.twittermvvm.Retrofit.MyRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    EditText LoginUsernameEdit,LoginPasswordEdit;
    Button LoginToSignupBTN , LoginBTN;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        LoginToSignupBTN = view.findViewById(R.id.logintosignupBTN);
        LoginBTN = view.findViewById(R.id.LoginBTN);

        LoginUsernameEdit = view.findViewById(R.id.LoginUsernameEdit);
        LoginPasswordEdit = view.findViewById(R.id.LoginPasswordEdit);

        sharedpreferences = getActivity().getSharedPreferences(getActivity().getPackageName()+"MySaveUser", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Login(LoginUsernameEdit.getText().toString(),LoginPasswordEdit.getText().toString());
                Loginn(LoginUsernameEdit.getText().toString(),LoginPasswordEdit.getText().toString());
            }
        });

        LoginToSignupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections directions = LoginFragmentDirections.actionLoginFragmentToSignupFragment();
                Navigation.findNavController(LoginToSignupBTN).navigate(directions);
            }
        });

    }

    public void Loginn(String username , String password)
    {
        MyApi myApi = MyRetrofit.getMyRetrofit().create(MyApi.class);
        myApi.Loginn(username,password).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.d("yTAG", "onResponse: "+ response.body());

                //???????? ?????? ?????????????? ???? ?????????? ???????? ?????? ?????? ???????? ????????
                editor.putBoolean("IsRegister",true);
                editor.putInt("ID", Integer.parseInt(response.body()));
                editor.commit();

                startActivity(new Intent(getActivity(), LoginSignupActivity.class));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(getActivity(), "?????? ???????????? ?? ?????????? ???????????? ??????!", Toast.LENGTH_SHORT).show();
                Log.d("yTAG", "Error: "+t.getMessage()+"\n"+t.getCause());

            }
        });

    }

    public void Login(String username , String password)
    {
        MyApi myApi = MyRetrofit.getMyRetrofit().create(MyApi.class);
        myApi.Login(username,password).enqueue(new Callback<List<UserId>>() {
            @Override
            public void onResponse(Call<List<UserId>> call, Response<List<UserId>> response) {
                Log.d("yTAG", "onResponse: "+ Integer.parseInt(response.body().get(0).getUser()));

                //???????? ?????? ?????????????? ???? ?????????? ???????? ?????? ?????? ???????? ????????
                editor.putBoolean("IsRegister",true);
                editor.putInt("ID", Integer.parseInt(response.body().get(0).getUser()));
                editor.commit();

                startActivity(new Intent(getActivity(), LoginSignupActivity.class));
            }

            @Override
            public void onFailure(Call<List<UserId>> call, Throwable t) {
                Log.d("yTAG", "onResponse: "+t.getMessage());
            }
        });

    }
}