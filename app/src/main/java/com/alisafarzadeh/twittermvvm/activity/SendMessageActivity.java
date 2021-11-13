package com.alisafarzadeh.twittermvvm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alisafarzadeh.twittermvvm.Retrofit.PostApi;
import com.alisafarzadeh.twittermvvm.model.Category;
import com.alisafarzadeh.twittermvvm.model.Status;
import com.alisafarzadeh.twittermvvm.R;
import com.alisafarzadeh.twittermvvm.Retrofit.MyApi;
import com.alisafarzadeh.twittermvvm.Retrofit.MyRetrofit;
import com.alisafarzadeh.twittermvvm.Util.Utils;
import com.alisafarzadeh.twittermvvm.databinding.ActivitySendMessageBinding;
import com.alisafarzadeh.twittermvvm.viewmodel.MyViewModel;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Transformation;

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
    LifecycleOwner owner;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_send_message);
        pd = new ProgressDialog(SendMessageActivity.this);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_send_message);
        sharedpreferences = getSharedPreferences(getPackageName()+"MySaveUser", Context.MODE_PRIVATE);
        spinner = (MaterialSpinner) findViewById(R.id.CategorySendPost);
        getCategory();
        owner = this;

        binding.HeaderSendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });


        binding.BTNSendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendPost();
                pd.setMessage("صبر کنید");
                pd.show();
                sendPostObserv(SendMessageActivity.this,
                        owner,
                        Utils.imagetoString(bitmap),
                        binding.TitleSendPost.getText().toString(),
                        binding.MessageSendPost.getText().toString(),
                        sharedpreferences.getInt("ID",-1),
                        binding.CategorySendPost.getSelectedIndex()+1);
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


    }


    Bitmap bitmap;

    //sendpostobserv
    public void sendPostObserv(Context context, LifecycleOwner lifecycleOwner, String media, String title, String content, int user, int category){
        MyViewModel viewmodel = new ViewModelProvider(this).get(MyViewModel.class);
        viewmodel.SendPostObserveViewModel(media, title, content, user, category)
                .observe(lifecycleOwner, new Observer<List<Status>>() {
                    @Override
                    public void onChanged(List<Status> statuses) {
                        Toast.makeText(context, "ارسال شد.", Toast.LENGTH_SHORT).show();
                        pd.hide();
                        startActivity(new Intent(SendMessageActivity.this,MainActivity.class));
                    }
                });

    }

    public void sendPost(){

        PostApi myApi = MyRetrofit.getMyRetrofit().create(PostApi.class);
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