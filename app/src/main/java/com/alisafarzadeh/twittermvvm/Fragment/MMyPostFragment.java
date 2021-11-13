package com.alisafarzadeh.twittermvvm.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alisafarzadeh.twittermvvm.R;
import com.alisafarzadeh.twittermvvm.Retrofit.MyApi;
import com.alisafarzadeh.twittermvvm.Retrofit.MyRetrofit;
import com.alisafarzadeh.twittermvvm.adapter.AllMessageRecyclerAdapter;
import com.alisafarzadeh.twittermvvm.adapter.MyPostRecycler;
import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.model.Status;
import com.alisafarzadeh.twittermvvm.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MMyPostFragment extends Fragment {

    RecyclerView recyclerView;
    MyPostRecycler adapter;
    List<Post> postlist = new ArrayList<>();
    MyViewModel myViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_m_my_post, container, false);
        recyclerView = v.findViewById(R.id.MyMessageRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());

        dividerItemDecoration.setDrawable(getResources().getDrawable(android.R.drawable.divider_horizontal_bright));

        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        return v;
    }

    SharedPreferences sharedpreferences;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedpreferences = getActivity().getSharedPreferences(getActivity().getPackageName()+"MySaveUser", Context.MODE_PRIVATE);
        int id  = sharedpreferences.getInt("ID",-1);

        /*
        myViewModel.getMyPostViewModel(id+"").observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {

                Log.d("sss", "onChanged: "+posts.size()+":"+posts.get(0).getContent());
                adapter = new AllMessageRecyclerAdapter(posts, getActivity(), new AllMessageRecyclerAdapter.OnMyClickListener() {
                    @Override
                    public void onButtonClicked(Post post) {

                    }

                    @Override
                    public void onGetIDButtonClicked(int post) {

                    }

                    @Override
                    public void onPositionitem(int position) {

                    }
                });
                recyclerView.setAdapter(adapter);
            }
        });

         */

        myViewModel.MyMessageViewModel(id+"").observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                adapter = new MyPostRecycler(posts, getActivity(), new MyPostRecycler.Click() {
                    @Override
                    public int onclickPosition(int position) {
                        Log.d("eeet", "onButtonClicked: "+position);

                        return 0;

                    }

                    @Override
                    public int onclickPost(Post post, int position) {


                        return 0;
                    }


                });

                     /*
                        new AllMessageRecyclerAdapter.OnMyClickListener() {
                    @Override
                    public void onButtonClicked(Post post) {
                        Log.d("eeet", "onButtonClicked: "+post.getIdpost());
                        DeleteID(Integer.parseInt(post.getIdpost()));

                    }

                    @Override
                    public void onGetIDButtonClicked(int post) {
                        Log.d("eeet", "onGetIDButtonClicked: "+post);
                    }

                    @Override
                    public void onPositionitem(int position) {
                        Log.d("eeet", "onPositionitem: "+position);

                    }
                });
                */
                        recyclerView.setAdapter(adapter);
            }
        });

    }



}