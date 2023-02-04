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

import com.example.foodplanner.Category.SearchCategoryArgs;
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


public class SearchCategory extends Fragment {


    String categoryname;
    private static Retrofit retrofit;
    List<CategorySearchModel> meals;
    RecyclerView recyclerView;
    TextInputEditText textInputEditText;

    CategorySearchAdapter categorySearchAdapter;

    public SearchCategory() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        categoryname= SearchCategoryArgs.fromBundle(getArguments()).getCategoryname();

        recyclerView = view.findViewById(R.id.categorySearchRecycler);
        textInputEditText=view.findViewById(R.id.mealSearchInput2);

//        testcountry=view.findViewById(R.id.testcountry);

        // testcountry.setText(countryname);



        Retrofit apiClient9 = APIClient.getClient();

        APIinterface apiInterface9 = apiClient9.create(APIinterface.class);

        Observable mealByCategory = apiInterface9.getMealsByCategory(categoryname);

        Observable<CategorySearchRoot> mealByCategoryObservable = apiInterface9.getMealsByCategory(categoryname);
        mealByCategoryObservable.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    meals = response.getMeals();

                    recyclerView.setHasFixedSize(true);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());

                    recyclerView.setLayoutManager(linearLayoutManager);

                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

                    categorySearchAdapter = new CategorySearchAdapter(meals);

                    recyclerView.setAdapter(categorySearchAdapter);



//


                }, error -> {
                    error.getMessage();
                });
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                categorySearchAdapter = new CategorySearchAdapter(meals.stream().filter(
                        meals -> meals.getStrMeal().startsWith(s.toString())).collect(Collectors.toList()));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(categorySearchAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
    }
}