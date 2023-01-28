package com.example.foodplanner.Ingredient;

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
import com.example.foodplanner.RetroFit.APIinterface;
import com.example.foodplanner.dataLayer.retrofitApi.APIClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;


public class Ingredient_Fragment extends Fragment {

    private static Retrofit retrofit;

    List<IngredientModel> ingredients;
    TextInputEditText search;
    IngredientAdapter ingredientAdapter;
    RecyclerView recyclerView;




    public Ingredient_Fragment() {
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
        return inflater.inflate(R.layout.fragment_ingredient_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.ingredientRecycler);
        search=view.findViewById(R.id.ingredient_text);

        Retrofit apiClient6= APIClient.getClient();

        APIinterface apiInterface6 = apiClient6.create(APIinterface.class);

        Observable Ingredients = apiInterface6.getIngredients("list");

        Observable<IngredientRoot> ingredientObservable  = apiInterface6.getIngredients("list");

        ingredientObservable.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                            ingredients= response.getMeals();

                            recyclerView.setHasFixedSize(true);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());

                            recyclerView.setLayoutManager(linearLayoutManager);

                            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

                            ingredientAdapter = new IngredientAdapter(ingredients);

                            recyclerView.setAdapter(ingredientAdapter);


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

                ingredientAdapter = new IngredientAdapter(ingredients.stream().filter(
                        ingredient -> ingredient.getStrIngredient().startsWith(s.toString())).collect(Collectors.toList()));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(ingredientAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
    }
}