package com.alisafarzadeh.twittermvvm.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.alisafarzadeh.twittermvvm.Util.MyConnect;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import kotlin.jvm.JvmStatic;

public class Post {

    String idpost;
    String media;
    String title;
    String content;
    String nameuser;
    String namecategory;
    String avatar;
    String iduser;
    boolean live;

    String Biography;

    public String getBiography() {
        return Biography;
    }

    public void setBiography(String biography) {
        Biography = biography;
    }

    String idbookmark;

    public String getIdbookmark() {
        return idbookmark;
    }

    public void setIdbookmark(String idbookmark) {
        this.idbookmark = idbookmark;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public String getIdpost() {
        return idpost;
    }

    public void setIdpost(String idpost) {
        this.idpost = idpost;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNameuser() {
        return nameuser;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }

    public String getNamecategory() {
        return namecategory;
    }

    public void setNamecategory(String namecategory) {
        this.namecategory = namecategory;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }



    @BindingAdapter("android:loadImageProfile")
    public static void loadImageProfile(ImageView imageView,String link_url){
        String URLIP = "http://94.232.169.217/MessageApp/";
        String URL = "http://androidhelp.ir/MessageApp/";
        String URLLOCAL =  "http://192.168.1.5/MessageApp/";

        Picasso.get().load(MyConnect.AddressLocal +link_url).into(imageView);
    }

    @BindingAdapter("android:loadImageView")
    public static void loadImageView(ImageView imageView,String link_url){
        Log.d("ccc", "http://192.168.1.5/MessageApp/"+link_url);
        String URLIP = "http://94.232.169.217/MessageApp/";

        String URLLOCAL =  "http://192.168.1.5/MessageApp/";
        String URL = "http://androidhelp.ir/MessageApp/";
        Picasso.get().load(MyConnect.AddressLocal+link_url)
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {

                        Bitmap output = Bitmap.createBitmap(source.getWidth(), source
                                .getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(output);

                        final int   color   = 0xff424242;
                        final Paint paint   = new Paint();
                        final Rect  rect    = new Rect(0, 0, source.getWidth(), source.getHeight());
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
                }).resize(130,130).centerCrop().into(imageView);
    }



}
