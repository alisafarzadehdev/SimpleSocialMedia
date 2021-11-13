package com.alisafarzadeh.twittermvvm.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alisafarzadeh.twittermvvm.activity.LoginSignupActivity;
import com.alisafarzadeh.twittermvvm.model.UserId;
import com.alisafarzadeh.twittermvvm.R;
import com.alisafarzadeh.twittermvvm.Retrofit.MyApi;
import com.alisafarzadeh.twittermvvm.Retrofit.MyRetrofit;
import com.alisafarzadeh.twittermvvm.Util.Utils;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignupFragment extends Fragment {

    Button SignupToLoginBTN,SignupBTN;
    EditText SignupUsernameEdit,
            SignupPasswordEdit,
            SignupNameEdit,
            SignupBioGraphyEdit;
    ImageView SignupImageImg;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        SignupToLoginBTN = view.findViewById(R.id.signuptologinBTN);
        SignupBTN = view.findViewById(R.id.SignupBTN);
        SignupImageImg = view.findViewById(R.id.SignupAvatarImg);
        SignupBioGraphyEdit = view.findViewById(R.id.SignupBiographyEdit);
        SignupNameEdit = view.findViewById(R.id.SignupNameEdit);
        SignupPasswordEdit = view.findViewById(R.id.SignupPasswordEdit);
        SignupUsernameEdit = view.findViewById(R.id.SignupUsernameEdit);

        sharedpreferences = getActivity().getSharedPreferences(getActivity().getPackageName()+"MySaveUser", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SignupUsernameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()<7)
                {
                    ((TextInputLayout)view.findViewById(R.id.TextinputUserName)).setError("نام کاربری باید بیشتر از 7 حرف داشته باشد");
                }else
                {
                    ((TextInputLayout)view.findViewById(R.id.TextinputUserName)).setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        SignupPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()<7)
                {
                    ((TextInputLayout)view.findViewById(R.id.TextinputPassword)).setError("گذرواژه باید بیشتر از 7 حرف داشته باشد");
                }else
                {
                    ((TextInputLayout)view.findViewById(R.id.TextinputPassword)).setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        SignupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation(SignupNameEdit,SignupUsernameEdit,SignupPasswordEdit,bitmap,SignupBioGraphyEdit))
                {
                    Signup(SignupNameEdit.getText().toString(),
                            SignupUsernameEdit.getText().toString(),
                            SignupPasswordEdit.getText().toString(),
                            Utils.imagetoString(bitmap),
                            SignupBioGraphyEdit.getText().toString());
                }else{
                    Toast.makeText(getActivity(), "اطلاعات را کامل کنید", Toast.LENGTH_SHORT).show();
                }

            }
        });

        SignupImageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        SignupToLoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections navDirections =  SignupFragmentDirections.actionSignupFragmentToLoginFragment();
                Navigation.findNavController(SignupToLoginBTN).navigate(navDirections);
            }
        });

    }

    public boolean validation(EditText name , EditText username , EditText password , Bitmap avatar , EditText biography){
        boolean valid = false;
        if (password.getText().toString().length()>6 && username.getText().toString().length()>6
                && avatar!=null && name.getText().toString().length()>0 && biography.getText().toString().length()>0)
        {
            valid = true;
        }
        return valid;
    }

    Bitmap bitmap;
    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 148);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 148) {
                try {
                    Uri selectedImageUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                    SignupImageImg.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    void Signup(String Name, String Username, String Password, String Avatar, String Biography) {
        MyApi myApi = MyRetrofit.getMyRetrofit().create(MyApi.class);

        myApi.SignUp(Name, Username, Password, Avatar, Biography).enqueue(new Callback<List<UserId>>() {
            @Override
            public void onResponse(Call<List<UserId>> call, Response<List<UserId>> response) {

                Log.d("yTAG", "onResponse: "+response.body());

                editor.putBoolean("IsRegister",true);
                editor.putInt("ID", Integer.parseInt(response.body().get(0).getUser()));
                editor.commit();

                startActivity(new Intent(getActivity(), LoginSignupActivity.class));

            }

            @Override
            public void onFailure(Call<List<UserId>> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.d("yTAG", "onResponse: "+t.getMessage()+ ":: "+t.getCause());

            }
        });

    }


}
