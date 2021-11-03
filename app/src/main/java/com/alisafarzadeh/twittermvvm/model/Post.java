package com.alisafarzadeh.twittermvvm.model;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

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



    @BindingAdapter("android:loadImageView")
    public static void loadImageView(ImageView imageView,String link_url){
      Log.d("ccc", "localhost/MessageApp/"+link_url);
    }




}
