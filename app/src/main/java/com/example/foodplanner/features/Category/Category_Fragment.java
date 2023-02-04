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

import com.example.foodplanner.dataLayer.RetroFit.APIinterface;
import com.example.foodplanner.R;
import com.example.foodplanner.dataLayer.retrofitApi.APIClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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


        Retrofit apiClient2 = APIClient.getClient();

        APIinterface apiInterface2 = apiClient2.create(APIinterface.class);

        Observable categoryList = apiInterface2.getCategories();
        Observable<CategoryRoot> categoryListObservable = apiInterface2.getCategories();
        categoryListObservable.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                            categories = response.getCategories();

                            recyclerView.setHasFixedSize(true);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());

                            recyclerView.setLayoutManager(linearLayoutManager);

                            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

                            categoryAdapter = new CategoryAdapter(categories);

                            recyclerView.setAdapter(categoryAdapter);


                        }
                        , error -> {

                            error.getMessage();
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