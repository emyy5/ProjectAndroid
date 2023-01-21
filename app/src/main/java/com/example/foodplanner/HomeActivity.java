package com.example.foodplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity {

    private static Retrofit retrofit;
    RecyclerView recyclerView;
    MealAdapter adapter;


    // ui for random meal

    TextView randomMealName;
    TextView randomMealId;
    ImageView randomMealImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        randomMealName = findViewById(R.id.mealName);
        randomMealId = findViewById(R.id.mealId);
        randomMealImage = findViewById(R.id.image);


        Retrofit apiClient = APIClient.getClient();

        APIinterface apiInterface = apiClient.create(APIinterface.class);

        Observable Randomobservable = apiInterface.getRandomMeals();

        Observable<RootMeal> RandomMealObservablre = apiInterface.getRandomMeals();
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
        Observable<RootMeal> EgyptianMealsObservable = apiInterface.getEgyptianMeals("Egyptian");
        EgyptianMealsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    ArrayList<Meal> meals = response.getMeals();


                        recyclerView.setHasFixedSize(true);


                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

                        recyclerView.setLayoutManager(linearLayoutManager);

                        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

                        adapter = new MealAdapter(meals);

                        recyclerView.setAdapter(adapter);


                }
                , error-> {

                    error.getMessage();
                        });
    }
}
