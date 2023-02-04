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
import com.example.foodplanner.dataLayer.RetroFit.APIinterface;
import com.example.foodplanner.dataLayer.retrofitApi.APIClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Country_Fragment extends Fragment {

    private static Retrofit retrofit;

    List<CountryModel> countries;
    TextInputEditText search;
    CountryAdapter countryAdapter;
    RecyclerView recyclerView;

    public Country_Fragment() {
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
        return inflater.inflate(R.layout.fragment_country_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.countryRecycler);
        search=view.findViewById(R.id.countryinput);

        Retrofit apiClient4 = APIClient.getClient();

        APIinterface apiInterface4 = apiClient4.create(APIinterface.class);

        Observable Countries = apiInterface4.getCountry("list");

        Observable<CountryRoot> CountriesObservable  = apiInterface4.getCountry("list");

        CountriesObservable.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                            countries= response.getMeals();

                            recyclerView.setHasFixedSize(true);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());

                            recyclerView.setLayoutManager(linearLayoutManager);

                            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

                            countryAdapter = new CountryAdapter(countries);

                            recyclerView.setAdapter(countryAdapter);


                        }
                        , error -> {

                            error.getMessage();
                        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                countryAdapter = new CountryAdapter(countries.stream().filter(
                        country -> country.getStrArea().startsWith(s.toString())).collect(Collectors.toList()));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(countryAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

    }
}