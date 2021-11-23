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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alisafarzadeh.twittermvvm.Util.Utils;
import com.alisafarzadeh.twittermvvm.activity.CommentActivity;
import com.alisafarzadeh.twittermvvm.activity.SendMessageActivity;
import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.R;
import com.alisafarzadeh.twittermvvm.adapter.AllMessageRecyclerAdapter;
import com.alisafarzadeh.twittermvvm.viewmodel.MyViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MMainMessageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    AllMessageRecyclerAdapter adapter;
    FloatingActionButton flat;
    List<Post> postlist = new ArrayList<>();
    MyViewModel myViewModel;

    SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_main, container, false);
        refreshLayout = v.findViewById(R.id.SwipeAllMessage);
        refreshLayout.setOnRefreshListener(this);
        flat = v.findViewById(R.id.SendMessageflatbtn);
        recyclerView = v.findViewById(R.id.MessageMainRecycler);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());

        dividerItemDecoration.setDrawable(getResources().getDrawable(android.R.drawable.divider_horizontal_bright));
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new AllMessageRecyclerAdapter(postlist, getActivity(),null);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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

        myViewModel.getAllPostObserveViewModel().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                Log.d("sss", "onChanged: "+posts.size());
                postlist = posts;
                recyclerView.scrollToPosition(postlist.size()-1);
                adapter = new AllMessageRecyclerAdapter(posts, getActivity(), new AllMessageRecyclerAdapter.OnMyClickListener() {
                    @Override
                    public void onButtonClicked(Post post, int position) {
                        Utils.SendValuePostActivity
                                (getActivity(), CommentActivity.class
                                        ,post.getIdpost(),post.getTitle(),post.getContent(),post.getNameuser(),post.getAvatar(),post.getMedia());

                    }
                });
                recyclerView.setAdapter(adapter);
            }
        });
        /*
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
                        BookmarkApi myApi = MyRetrofit.getMyRetrofit().create(BookmarkApi.class);
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

    @Override
    public void onRefresh() {

        myViewModel.getAllPostObserveViewModel().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                Log.d("sss", "onChanged: "+posts.size());
                postlist = posts;
                adapter = new AllMessageRecyclerAdapter(posts, getActivity(), new AllMessageRecyclerAdapter.OnMyClickListener() {
                    @Override
                    public void onButtonClicked(Post post, int position) {
                        Utils.SendValuePostActivity
                                (getActivity(), CommentActivity.class
                                        ,post.getIdpost(),post.getTitle(),post.getContent(),post.getNameuser(),post.getAvatar(),post.getMedia());

                    }
                });
                recyclerView.setAdapter(adapter);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 2000);

            }
        });


    }
}