package com.example.foodplanner.features.Category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.dataLayer.NetworkCallBacks;
import com.example.foodplanner.dataLayer.Repository;
import com.example.foodplanner.R;
import com.example.foodplanner.features.Category.adapter.CategoryAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Retrofit;


public class Category_Fragment extends Fragment {

    private static Retrofit retrofit;
    List<CategoriesModel> categories;
    TextInputEditText textInputEditText;
    CategoryAdapter categoryAdapter;
    RecyclerView recyclerView;


    public Category_Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.categoryRecycler);
        textInputEditText=view.findViewById(R.id.categoryinput);



        // TODO migrate this code to repository
        new Repository(getContext()).getCategoryListApi(new NetworkCallBacks<List<CategoriesModel>>() {
            @Override
            public void responseData(List<CategoriesModel> data) {
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                categoryAdapter = new CategoryAdapter(categories);
                recyclerView.setAdapter(categoryAdapter);
            }
        });

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                categoryAdapter = new CategoryAdapter(categories.stream().filter(
                        category -> category.getStrCategory().startsWith(s.toString())).collect(Collectors.toList()));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(categoryAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            });

        }
    }