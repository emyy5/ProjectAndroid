package com.example.foodplanner.features.Country;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.dataLayer.retrofitApi.APIClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
public class CountrySearch extends Fragment {

    String countryname;
    List<CountrySearchModel> meals = new ArrayList<>();
    RecyclerView recyclerView;
    TextInputEditText textInputEditText;
    CountrySearchAdapter countrySearchAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.countrySearchRecycler);
        textInputEditText=view.findViewById(R.id.mealSearchInput);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);


        // TODO migrate this code to repository
        countryname = CountrySearchArgs.fromBundle(getArguments()).getCountryname();
        Observable<CountrySearchRoot> mealByCountryObservable = APIClient.apiInterface.getMealsByCountry(countryname);
        mealByCountryObservable.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    meals = response.getMeals();
                    countrySearchAdapter = new CountrySearchAdapter(meals);

                    recyclerView.setAdapter(countrySearchAdapter);
                }, error -> {
                    error.getMessage();
                });

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<CountrySearchModel> countrySearchModelList = meals.stream().filter(
                        meals -> meals.getStrMeal().startsWith(s.toString())).collect(Collectors.toList());
                countrySearchAdapter = new CountrySearchAdapter(countrySearchModelList);
                recyclerView.setAdapter(countrySearchAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
    }
}