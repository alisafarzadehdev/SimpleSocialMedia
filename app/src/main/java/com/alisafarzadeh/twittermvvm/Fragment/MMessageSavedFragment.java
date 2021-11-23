package com.alisafarzadeh.twittermvvm.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alisafarzadeh.twittermvvm.R;
import com.alisafarzadeh.twittermvvm.Util.Utils;
import com.alisafarzadeh.twittermvvm.activity.CommentActivity;
import com.alisafarzadeh.twittermvvm.adapter.AllMessageRecyclerAdapter;
import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class MMessageSavedFragment extends Fragment {

    RecyclerView recyclerView;
    AllMessageRecyclerAdapter recyclerAdapter;
    List<Post> postSave = new ArrayList<>();
    SharedPreferences sharedpreferences;
    MyViewModel myViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_m_message_saved, container, false);
        recyclerView =  view.findViewById(R.id.rec_savedpost);
        recyclerAdapter = new AllMessageRecyclerAdapter(postSave,getActivity(),null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(android.R.drawable.divider_horizontal_bright));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        recyclerView.setAdapter(recyclerAdapter);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        sharedpreferences = getActivity().getSharedPreferences(getActivity().getPackageName()+"MySaveUser", Context.MODE_PRIVATE);
        int id  = sharedpreferences.getInt("ID",-1);

        myViewModel.ShowSavePostViewModel(id).observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {

                recyclerAdapter = new AllMessageRecyclerAdapter(posts, getActivity(), new AllMessageRecyclerAdapter.OnMyClickListener() {
                    @Override
                    public void onButtonClicked(Post post, int position) {
                        Utils.SendValuePostActivity
                                (getActivity(), CommentActivity.class
                                        ,post.getIdpost(),post.getTitle(),post.getContent(),post.getNameuser(),post.getAvatar(),post.getMedia());

                    }
                });
                recyclerView.setAdapter(recyclerAdapter);

            }
        });



        super.onViewCreated(view, savedInstanceState);
    }
}