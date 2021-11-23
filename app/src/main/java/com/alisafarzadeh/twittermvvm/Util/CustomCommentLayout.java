package com.alisafarzadeh.twittermvvm.Util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.alisafarzadeh.twittermvvm.R;

public class CustomCommentLayout extends Dialog implements View.OnClickListener {


    public interface GET
    {
        String setComment(String comment);
    }
    GET getmessage;


    public CustomCommentLayout(@NonNull Context context,GET getmessage) {
        super(context);
        this.getmessage = getmessage;

    }

    Button yes , no ;
    EditText comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_send_comment);
        yes = (Button) findViewById(R.id.yes_comment);
        no = (Button) findViewById(R.id.no_comment);
        comment = findViewById(R.id.txt_comment);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.yes_comment:
                getmessage.setComment(comment.getText().toString());
                hide();
                break;

            case R.id.no_comment:
                dismiss();
                break;
        }
    }
}
