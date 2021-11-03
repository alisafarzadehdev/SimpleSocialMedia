package com.alisafarzadeh.twittermvvm.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alisafarzadeh.twittermvvm.Retrofit.MyApi;
import com.alisafarzadeh.twittermvvm.Retrofit.MyRetrofit;
import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.R;
import com.alisafarzadeh.twittermvvm.databinding.LayoutRecyclerallmessageBinding;
import com.alisafarzadeh.twittermvvm.model.Status;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllMessageRecyclerAdapter extends RecyclerView.Adapter<AllMessageRecyclerAdapter.MyViewHolder> {

    List<Post> posts = new ArrayList<>();
    Context context;
    OnMyClickListener onMyClickListener;


    public AllMessageRecyclerAdapter(List<Post> posts, Context context,OnMyClickListener on) {
        this.posts = posts;
        this.context = context;
        onMyClickListener = on;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutRecyclerallmessageBinding binding =  DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_recyclerallmessage,parent,false);
        return new MyViewHolder(binding);
    }

    boolean Bookmarked = false;
    public boolean isBookmark(String post , ImageView img)
    {
        sharedpreferences = context.getSharedPreferences(context.getPackageName()+"MySaveUser", Context.MODE_PRIVATE);
        int id  = sharedpreferences.getInt("ID",-1);
        MyApi myApi = MyRetrofit.getMyRetrofit().create(MyApi.class);
        Log.d("cccz", "isBookmark: "+post+":"+id);
        myApi.IsBookmark(id+"",post+"").enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {

                Log.d("dddx", "onResponse: "+response.body().get(0).getStatus());
                if (response.body().get(0).getStatus().equals("0"))
                {
                    Bookmarked = false;
                    Log.d("qqqqw", "onResponse: ");
                    img.setImageResource(R.drawable.ic_bookmark_border);

                }else{
                    Log.d("qqqqe", "onResponse: ");
                    img.setImageResource(R.drawable.ic_bookmark);
                    Bookmarked = true;
                }
            }

            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {
                Log.d("dddx", "onFail: "+t.getMessage());
            }
        });
        return Bookmarked;
    }

    SharedPreferences sharedpreferences;
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int pos = position;

        //holder.binding.favitem.setChecked();
        holder.binding.setAllMessage(posts.get(position));

        isBookmark(posts.get(position).getIdpost(), holder.binding.bookmarkitempost);
        /*
        if ()
        {
            Log.d("IsBookmarkTure", ": "+posts.get(position).getIdpost());
        }else{
            Log.d("IsBookmarkFalse", ": "+posts.get(position).getIdpost());
        }
*/





    }



    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public interface OnMyClickListener{
        void onButtonClicked(Post post);
        void onGetIDButtonClicked(int post);
        void onPositionitem(int position);
    }




    CallBackPrice callBackPrice;

    public void GetPrice(CallBackPrice callBackPrice) {
        this.callBackPrice = callBackPrice;
    }

    public interface CallBackPrice {
        void getAllPrice(Post post);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LayoutRecyclerallmessageBinding binding;
        public MyViewHolder(@NonNull LayoutRecyclerallmessageBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
            binding.ConstraintItemAllMessage.setOnClickListener(this);
            binding.bookmarkitempost.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (view.getId() == binding.ConstraintItemAllMessage.getId()){
                //onMyClickListener(view.getId());
                onMyClickListener.onButtonClicked(posts.get(getAdapterPosition()));
            }
            if (view.getId() == binding.bookmarkitempost.getId()){
                //onMyClickListener(view.getId());
                int id = Integer.parseInt(posts.get(getAdapterPosition()).getIdpost());
                onMyClickListener.onGetIDButtonClicked(id);
                onMyClickListener.onPositionitem(getAdapterPosition());
            }
        }
    }
}
