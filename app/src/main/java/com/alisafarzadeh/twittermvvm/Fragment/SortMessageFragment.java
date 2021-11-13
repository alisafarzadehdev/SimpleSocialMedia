package com.alisafarzadeh.twittermvvm.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.alisafarzadeh.twittermvvm.activity.SendMessageActivity;
import com.alisafarzadeh.twittermvvm.adapter.AllMessageRecyclerAdapter;
import com.alisafarzadeh.twittermvvm.model.Category;
import com.alisafarzadeh.twittermvvm.model.Post;
import com.alisafarzadeh.twittermvvm.viewmodel.MyViewModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SortMessageFragment extends Fragment {

    List<Post> posts = new ArrayList<>();
    RecyclerView recyclerView;
    AllMessageRecyclerAdapter adapter;
    MaterialSpinner materialSpinner;
    MyViewModel myViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_sort_message, container, false);

        recyclerView = v.findViewById(R.id.RecyclerCategoryShowPost);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());

        dividerItemDecoration.setDrawable(getResources().getDrawable(android.R.drawable.divider_horizontal_bright));
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        materialSpinner =  v.findViewById(R.id.CategoryShowPost);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        getCategory();
        materialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                showWithCat(position);
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    public void getCategory(){

        MyApi myApi = MyRetrofit.getMyRetrofit().create(MyApi.class);
        myApi.ShowCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                {
                    List<Category> categories = response.body();
                    List<String> list =new ArrayList<>();
                    for (Category cats:categories) {
                        list.add(cats.getCategoryName());
                    }
                    materialSpinner.setItems(list);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("xxxx", "onFailure: "+t.getMessage());
            }
        });


    }

    public void showWithCat(int position)
    {

        myViewModel.ShowWithCategoryViewModel(position+1).observe(getViewLifecycleOwner(),posts1 -> {
            try {
                Log.d("TAGll", "showWithCat: "+posts1.get(0).getNameuser());
                adapter = new AllMessageRecyclerAdapter(posts1, getActivity(), new AllMessageRecyclerAdapter.OnMyClickListener() {
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

            }catch (Exception e)
            {
                Toast.makeText(getActivity(), "محتوایی در این دسته نیست", Toast.LENGTH_SHORT).show();
            }


        });

    }
}
