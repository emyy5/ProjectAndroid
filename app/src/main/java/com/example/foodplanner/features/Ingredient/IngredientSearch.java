package com.example.foodplanner.features.Ingredient;

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

import com.example.foodplanner.R;
import com.example.foodplanner.dataLayer.retrofitApi.APIClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;


public class IngredientSearch extends Fragment {

    private static Retrofit retrofit;
    List<IngredientSearchModel> meals;
    TextInputEditText textInputEditText;
    IngredientSearchAdapter ingredientSearchAdapter;
    RecyclerView recyclerView;

    //TextView test;

    String ingredientname;


    public IngredientSearch() {
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
        return inflater.inflate(R.layout.fragment_ingredient_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ingredientname = IngredientSearchArgs.fromBundle(getArguments()).getIngredientname();

        recyclerView = view.findViewById(R.id.ingredientSearchRecycler);
        textInputEditText=view.findViewById(R.id.ingredientinput);

        Observable<IngredientSearchRoot> ingredientMealsObservable = APIClient.apiInterface.getIngredientMeals(ingredientname);
        ingredientMealsObservable.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                            meals = response.getMeals();
                            recyclerView.setHasFixedSize(true);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

                            ingredientSearchAdapter = new IngredientSearchAdapter(meals);

                            recyclerView.setAdapter(ingredientSearchAdapter);


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

                ingredientSearchAdapter = new IngredientSearchAdapter(meals.stream().filter(
                        meals -> meals.getStrMeal().startsWith(s.toString())).collect(Collectors.toList()));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(ingredientSearchAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });


    }
}