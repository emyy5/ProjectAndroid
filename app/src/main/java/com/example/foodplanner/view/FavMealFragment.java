package com.example.foodplanner.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;


import com.example.foodplanner.dataLayer.room.FavproductDao;
import com.example.foodplanner.R;
import com.example.foodplanner.dataLayer.room.RandomMeal;
import com.example.foodplanner.dataLayer.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FavMealFragment extends Fragment {

    FavproductDao favproductDao;
    private static final String BASE_URL= "https://www.themealdb.com/api/json/v1/1/";
    private static final String TAG = "API_Client";
    List<RandomMeal> product;
    RecyclerView recyclerView;
    List<RandomMeal> myList = new ArrayList<>();
    FavAdapter myAdapter;
    Repository repository;

    public FavMealFragment() {

    }



    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.fav_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new FavAdapter(requireContext(), myList);
        repository = new Repository(requireContext());
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        repository.getStoreProduct().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<RandomMeal>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<RandomMeal> products) {
                        myAdapter.setProducts(products);
                        myAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });


        recyclerView.setAdapter(myAdapter);
    }
}
