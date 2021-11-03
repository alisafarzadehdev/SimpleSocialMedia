package com.alisafarzadeh.twittermvvm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alisafarzadeh.twittermvvm.model.Category;
import com.alisafarzadeh.twittermvvm.model.Status;
import com.alisafarzadeh.twittermvvm.R;
import com.alisafarzadeh.twittermvvm.Retrofit.MyApi;
import com.alisafarzadeh.twittermvvm.Retrofit.MyRetrofit;
import com.alisafarzadeh.twittermvvm.Util.Utils;
import com.alisafarzadeh.twittermvvm.databinding.ActivitySendMessageBinding;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMessageActivity extends AppCompatActivity {

    ActivitySendMessageBinding binding;
    SharedPreferences sharedpreferences;
    MaterialSpinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_send_message);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_send_message);
        sharedpreferences = getSharedPreferences(getPackageName()+"MySaveUser", Context.MODE_PRIVATE);
        spinner = (MaterialSpinner) findViewById(R.id.CategorySendPost);
        getCategory();

        binding.HeaderSendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });
        binding.BTNSendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPost();
            }
        });

    }

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
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    binding.HeaderSendPost.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getCategory(){

        MyApi myApi = MyRetrofit.getMyRetrofit().create(MyApi.class);
        myApi.ShowCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                {
                    List<Category> categories = response.body();
                    List<String> list =new ArrayList<>();
                    for (Category cats:categories) {
                        list.add(cats.getCategoryName());
                    }
                    spinner.setItems(list);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(SendMessageActivity.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        //spinner.setItems("Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow");
        /*spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

            }
        });

         */
    }


    Bitmap bitmap;
    public void sendPost(){

        MyApi myApi = MyRetrofit.getMyRetrofit().create(MyApi.class);
        binding.HeaderSendPost.buildDrawingCache();

        myApi.SendPost(
                Utils.imagetoString(bitmap),
                binding.TitleSendPost.getText().toString(),
                binding.MessageSendPost.getText().toString(),
                sharedpreferences.getInt("ID",-1),
                binding.CategorySendPost.getSelectedIndex()+1)
                .enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                Log.d("ddd",response.body().get(0).getStatus());
            }

            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {
                Toast.makeText(SendMessageActivity.this, "پیام ارسال نشد", Toast.LENGTH_SHORT).show();
                Log.d("xTAG", "onFailure: "+t.getCause()+t.getMessage());
            }
        });


    }

}