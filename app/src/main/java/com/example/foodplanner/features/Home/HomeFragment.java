package com.example.foodplanner.features.Home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.dataLayer.Repository;
import com.example.foodplanner.dataLayer.pojes.RandomMeal;
import com.example.foodplanner.dataLayer.pojes.RandomRoot;
import com.example.foodplanner.dataLayer.retrofitApi.APIClient;
import com.example.foodplanner.dataLayer.retrofitApi.APIinterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.protobuf.Api;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    HomeAdapter adapter;
    CardView randomCard;
    String mealId;


    // ui for random meal

    TextView randomMealName;
    TextView randomMealId;
    ImageView randomMealImage;
    Repository repository;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository= new Repository(getContext());

        recyclerView = view.findViewById(R.id.recyclerView);
        randomMealName = view.findViewById(R.id.mealName);
        randomMealId = view.findViewById(R.id.mealId);
        randomMealImage = view.findViewById(R.id.image);
        randomCard=view.findViewById(R.id.RandomCard);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("key",Context.MODE_PRIVATE);



        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();



        // TODO migrate this code to repository
        Observable<RandomRoot> RandomMealObservablre = APIClient.apiInterface.getRandomMeals();
        RandomMealObservablre
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    String imgURl = response.getMeals().get(0).getStrMealThumb();

                    Log.i("img", "onResponse:" + imgURl);
                    Glide.with(randomMealImage.getContext()).load(imgURl).into(randomMealImage);

                    randomMealName.setText(response.getMeals().get(0).getStrMeal());
                    randomMealId.setText(response.getMeals().get(0).getIdMeal().toString());
                    mealId.equals(response.getMeals().get(0).getIdMeal().toString());


                }, error -> {
                    error.getMessage();
                });


        // TODO migrate this code to repository
        Observable<RandomRoot> EgyptianMealsObservable = APIClient.apiInterface.getEgyptianMeals("Egyptian");
        EgyptianMealsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                            ArrayList<RandomMeal> meals = response.getMeals();


                            recyclerView.setHasFixedSize(true);


                            adapter = new HomeAdapter(meals,getContext());

                            recyclerView.setAdapter(adapter);


                        }
                        , error-> {

                            error.getMessage();
                        });
        randomCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeFragmentDirections.ActionHomeToDetailsFragment action = HomeFragmentDirections.actionHomeToDetailsFragment();
                action.setId(Long.parseLong(randomMealId.getText().toString()));
                Navigation.findNavController(view).navigate(action);


            }
        });
    }
    }
