package com.alisafarzadeh.twittermvvm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alisafarzadeh.twittermvvm.R;
import com.alisafarzadeh.twittermvvm.Util.CustomCommentLayout;
import com.alisafarzadeh.twittermvvm.Util.MyConnect;
import com.alisafarzadeh.twittermvvm.adapter.AllMessageRecyclerAdapter;
import com.alisafarzadeh.twittermvvm.adapter.CommentRecycler;
import com.alisafarzadeh.twittermvvm.databinding.ActivityCommentBinding;
import com.alisafarzadeh.twittermvvm.model.Comment;
import com.alisafarzadeh.twittermvvm.viewmodel.MyViewModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.util.List;

public class CommentActivity extends AppCompatActivity {

    ActivityCommentBinding binding;
    String idPost;
    int iduser;
    RecyclerView recyclerView;
    ImageButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_post);
        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btn = findViewById(R.id.messageBtn);

        MyViewModel myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        recyclerView = findViewById(R.id.recyclerView_comment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());

        dividerItemDecoration.setDrawable(getResources().getDrawable(android.R.drawable.divider_horizontal_bright));
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        SharedPreferences sharedpreferences = getSharedPreferences(getPackageName()+"MySaveUser", Context.MODE_PRIVATE);
        iduser  = sharedpreferences.getInt("ID",-1);

        String title = (String) getIntent().getExtras().get("Title");
        String content = (String) getIntent().getExtras().get("Content");
        String name = (String) getIntent().getExtras().get("Name");
        String profile = (String) getIntent().getExtras().get("Profile");
        String header = (String) getIntent().getExtras().get("Header");
        idPost = (String) getIntent().getExtras().get("IDPost");

        binding.contentComment.setText(content);
        binding.titleComment.setText(title);
        binding.nameComment.setText(name);


        Picasso.get().load(MyConnect.AddressLocal +profile)
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {

                        Bitmap output = Bitmap.createBitmap(source.getWidth(), source
                                .getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(output);

                        final int   color   = 0xff424242;
                        final Paint paint   = new Paint();
                        final Rect rect    = new Rect(0, 0, source.getWidth(), source.getHeight());
                        final RectF rectF   = new RectF(rect);
                        final float roundPx = 6;

                        paint.setAntiAlias(true);
                        canvas.drawARGB(0, 0, 0, 0);
                        paint.setColor(color);
                        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas.drawBitmap(source, rect, rect, paint);
                        source.recycle();

                        return output;
                    }

                    @Override
                    public String key() {
                        return "rounded image";
                    }
                }).into(binding.profileImageComment);


        Picasso.get().load(MyConnect.AddressLocal+header)
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {

                        Bitmap output = Bitmap.createBitmap(source.getWidth(), source
                                .getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(output);

                        final int   color   = 0xff424242;
                        final Paint paint   = new Paint();
                        final Rect rect    = new Rect(0, 0, source.getWidth(), source.getHeight());
                        final RectF rectF   = new RectF(rect);
                        final float roundPx = 6;

                        paint.setAntiAlias(true);
                        canvas.drawARGB(0, 0, 0, 0);
                        paint.setColor(color);
                        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas.drawBitmap(source, rect, rect, paint);
                        source.recycle();

                        return output;
                    }

                    @Override
                    public String key() {
                        return "rounded image";
                    }
                }).into(binding.mediaComment);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    myViewModel.SendCommentViewModel(Integer.parseInt(idPost),iduser,binding.messageEdit.getText().toString());
                    Toast.makeText(CommentActivity.this, "پیام ارسال شد", Toast.LENGTH_SHORT).show();
                    binding.messageEdit.setText("");

            }
        });


        setcommentToRecyclerView(Integer.parseInt(idPost));

        binding.SendCommentflatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomCommentLayout.GET get = new CustomCommentLayout.GET() {
                    @Override
                    public String setComment(String comment) {
                        try {
                            Toast.makeText(CommentActivity.this, "ارسال شد", Toast.LENGTH_SHORT).show();
                        }catch (Exception e)
                        {
                            Toast.makeText(CommentActivity.this, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }

                        return null;
                    }
                };
                CustomCommentLayout commentLayout=new CustomCommentLayout(CommentActivity.this,get);
                commentLayout.show();
            }
        });


        Log.d("qwqw", title+"\n"+content+"\n"+name+"\n"+profile+"\n"+header+"\n"+idPost);


    }

    CommentRecycler commentRecycler;
    MyViewModel myViewModel;
    public void setcommentToRecyclerView(int post)
    {
        myViewModel= new ViewModelProvider(this).get(MyViewModel.class);
        myViewModel.ShowCommentViewModel(Integer.parseInt(idPost))
                .observeForever( new Observer<List<Comment>>() {
                    @Override
                    public void onChanged(List<Comment> comments) {
                        commentRecycler=new CommentRecycler(comments,getApplicationContext());
                        recyclerView.setAdapter(commentRecycler);
                    }
                });

    }
}