package com.alisafarzadeh.twittermvvm.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alisafarzadeh.twittermvvm.Retrofit.MyApi;
import com.alisafarzadeh.twittermvvm.Retrofit.MyRetrofit;
import com.alisafarzadeh.twittermvvm.activity.SendMessageActivity;
import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.R;
import com.alisafarzadeh.twittermvvm.adapter.AllMessageRecyclerAdapter;
import com.alisafarzadeh.twittermvvm.model.Status;
import com.alisafarzadeh.twittermvvm.viewmodel.MyViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MMainMessageFragment extends Fragment {

    RecyclerView recyclerView;
    AllMessageRecyclerAdapter adapter;
    FloatingActionButton flat;
    List<Post> postlist = new ArrayList<>();
    MyViewModel myViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_main, container, false);
        flat = v.findViewById(R.id.SendMessageflatbtn);
        recyclerView = v.findViewById(R.id.MessageMainRecycler);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        adapter = new AllMessageRecyclerAdapter(postlist, getActivity(),null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    SharedPreferences sharedpreferences;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedpreferences = getActivity().getSharedPreferences(getActivity().getPackageName()+"MySaveUser", Context.MODE_PRIVATE);
        int id  = sharedpreferences.getInt("ID",-1);
        Log.d("iduser", "onViewCreated: "+id);

        myViewModel.fetchUser2().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                //postlist.addAll(posts);
                //adapter.notifyDataSetChanged();
                adapter = new AllMessageRecyclerAdapter(posts, getActivity(), new AllMessageRecyclerAdapter.OnMyClickListener() {
                    @Override
                    public void onButtonClicked(Post post) {
                        Log.d("TAG", "onButtonClicked: "+post.getContent()+post.getTitle());
                    }

                    @Override
                    public void onGetIDButtonClicked(int post) {
                        Log.d("fTAG", post+"    :   "+id);
                        MyApi myApi = MyRetrofit.getMyRetrofit().create(MyApi.class);
                        myApi.BookmarkSave(id+"",post+"").enqueue(new Callback<List<Status>>() {
                            @Override
                            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                                Log.d("book", "onResponse: "+response.body().get(0).getStatus());
                            }

                            @Override
                            public void onFailure(Call<List<Status>> call, Throwable t) {
                                Log.d("book", "Fail: "+t.getMessage());

                            }
                        });
                    }

                    @Override
                    public void onPositionitem(int position) {

                    }
                });

                recyclerView.setAdapter(adapter);
                Log.d("xtagf", "onChanged: "+postlist.size());
            }
        });
        /*
        adapter.GetPrice(new AllMessageRecyclerAdapter.CallBackPrice() {
            @Override
            public void getAllPrice(Post post) {
                try {
                    Log.d("cccc", "getAllPrice: " + post.getContent());
                    Toast.makeText(getActivity(), post.getContent() + "", Toast.LENGTH_SHORT).show();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

         */







        flat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SendMessageActivity.class));
            }
        });

    }
}