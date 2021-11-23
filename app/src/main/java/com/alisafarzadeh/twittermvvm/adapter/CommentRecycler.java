package com.alisafarzadeh.twittermvvm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alisafarzadeh.twittermvvm.R;
import com.alisafarzadeh.twittermvvm.Retrofit.MyApi;
import com.alisafarzadeh.twittermvvm.Retrofit.MyRetrofit;
import com.alisafarzadeh.twittermvvm.databinding.LayoutCommentRecyclerviewBinding;
import com.alisafarzadeh.twittermvvm.databinding.LayoutRecyclermymessageBinding;
import com.alisafarzadeh.twittermvvm.model.Comment;
import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.model.Status;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentRecycler extends RecyclerView.Adapter<CommentRecycler.MyPostHolder>{
    List<Comment> comments = new ArrayList<>();
    Context context;

    public CommentRecycler(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentRecycler.MyPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutCommentRecyclerviewBinding layoutRecyclermymessageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_comment_recyclerview,parent,false);
        return new MyPostHolder(layoutRecyclermymessageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostHolder holder, int position) {
        holder.binding.setComment(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class MyPostHolder extends RecyclerView.ViewHolder {

        LayoutCommentRecyclerviewBinding binding;
        public MyPostHolder(@NonNull LayoutCommentRecyclerviewBinding itemView) {
            super(itemView.getRoot());
            binding= itemView;

        }

    }
}
