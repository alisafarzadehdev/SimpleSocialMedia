package com.alisafarzadeh.twittermvvm.Util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class Utils {

    public static String imagetoString(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
    public static void SendValuePostActivity(Context context , Class Class ,String Post, String title , String content , String name ,
                                             String profile , String PostHeader)
    {


        Intent i = new Intent(context,Class);
        i.putExtra("Title",title);
        i.putExtra("Content",content);
        i.putExtra("Name",name);
        i.putExtra("Profile",profile);
        i.putExtra("Header",PostHeader);
        i.putExtra("IDPost",Post);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}
