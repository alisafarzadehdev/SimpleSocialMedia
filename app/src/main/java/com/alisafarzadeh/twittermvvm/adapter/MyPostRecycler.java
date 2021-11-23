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
import com.alisafarzadeh.twittermvvm.databinding.LayoutRecyclermymessageBinding;
import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.model.Status;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPostRecycler extends RecyclerView.Adapter<MyPostRecycler.MyPostHolder>{
    List<Post> posts = new ArrayList<>();
    Context context;
    Click click;

    public MyPostRecycler(List<Post> posts, Context context, Click click) {
        this.posts = posts;
        this.context = context;
        this.click = click;
    }

    public interface Click{
        int onclickPosition(int position);
        int onclickPost(Post post, int position);
    }
    public void DeleteID(int id)
    {
        MyApi api = MyRetrofit.getMyRetrofit().create(MyApi.class);
        api.DeletePost(id).enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                Log.d("sssx", "response: "+response.body().get(0).getStatus());
            }

            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {
                Log.d("sssx", "onFailure: "+t.getMessage());
            }
        });
    }

    @NonNull
    @Override
    public MyPostRecycler.MyPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutRecyclermymessageBinding layoutRecyclermymessageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_recyclermymessage,parent,false);
        return new MyPostHolder(layoutRecyclermymessageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostHolder holder, int position) {
        int p = position;
        holder.binding.setAllMessage(posts.get(position));


        holder.binding.removeitempost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //posts.remove(p);
                //notifyItemChanged(p);
                DeleteID(Integer.parseInt(posts.get(p).getIdpost()));
                posts.remove(p);
                notifyItemRemoved(p);
                notifyItemRangeChanged(p, posts.size());

                //notifyItemRangeChanged(p,posts.size()-1);
                Log.d("ssszq", "onClick: "+p+":"+posts.size());
            }
        });


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class MyPostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LayoutRecyclermymessageBinding binding;
        public MyPostHolder(@NonNull LayoutRecyclermymessageBinding itemView) {
            super(itemView.getRoot());
            binding= itemView;
            binding.removeitempost.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view.getId()==binding.removeitempost.getId())
            {
                click.onclickPosition(getAdapterPosition());
                click.onclickPost(posts.get(getAdapterPosition()),getAdapterPosition());

            }

        }
    }
}
