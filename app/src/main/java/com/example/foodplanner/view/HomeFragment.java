package com.example.foodplanner.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.dataLayer.Repository;
import com.example.foodplanner.dataLayer.pojes.RandomMeal;
import com.example.foodplanner.dataLayer.pojes.RandomRoot;
import com.example.foodplanner.dataLayer.retrofitApi.APIClient;
import com.example.foodplanner.dataLayer.retrofitApi.APIinterface;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    RandomAdapter adapter;
    Button fav_btn;

    // ui for random meal

    TextView randomMealName;
    TextView randomMealId;
    ImageView randomMealImage;
    Repository repository;


    public HomeFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        repository= new Repository(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        randomMealName = view.findViewById(R.id.mealName);
        randomMealId = view.findViewById(R.id.mealId);
        randomMealImage = view.findViewById(R.id.image);
        fav_btn= view.findViewById(R.id.fav_btn);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


        ImageView logutBtn = view.findViewById(R.id.logoutBtn);
        logutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Navigation.findNavController(view).navigate(R.id.logIn);
            }
        });

        fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.favmeal);

            }
        });

        Retrofit apiClient = APIClient.getClient();

        APIinterface apiInterface = apiClient.create(APIinterface.class);

        Observable Randomobservable = apiInterface.getRandomMeals();

        Observable<RandomRoot> RandomMealObservablre = apiInterface.getRandomMeals();
        RandomMealObservablre
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    String imgURl = response.getMeals().get(0).getStrMealThumb();

                    Log.i("img", "onResponse:" + imgURl);
                    Glide.with(randomMealImage.getContext()).load(imgURl).into(randomMealImage);

                    randomMealName.setText(response.getMeals().get(0).getIdMeal().toString());
                    randomMealId.setText(response.getMeals().get(0).getStrMeal());


                }, error -> {
                    error.getMessage();
                });


        Observable EgyptianMeals = apiInterface.getEgyptianMeals("Egyptian");
        Observable<RandomRoot> EgyptianMealsObservable = apiInterface.getEgyptianMeals("Egyptian");
        EgyptianMealsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                            ArrayList<RandomMeal> meals = response.getMeals();


                            recyclerView.setHasFixedSize(true);


                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());

                            recyclerView.setLayoutManager(linearLayoutManager);

                            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

                            adapter = new RandomAdapter(meals,getContext());

                            recyclerView.setAdapter(adapter);


                        }
                        , error-> {

                            error.getMessage();
                        });
    }
    }
