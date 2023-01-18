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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity {
    private static Retrofit retrofit;
    RecyclerView recyclerView;
    MealAdapter adapter;


    // ui for random meal
    TextView txt;
    TextView txt2;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recyclerView);
        txt = findViewById(R.id.mealName);
        txt2 = findViewById(R.id.mealId);
        image = findViewById(R.id.image);


        Retrofit apiClient= APIClient.getClient();

        APIinterface apiInterface =  apiClient.create( APIinterface.class);

        Call<RootMeal> call = apiInterface.getRandomMeals();


        call.enqueue(new Callback<RootMeal>() {
            @Override
            public void onResponse(Call<RootMeal> call, Response<RootMeal> response) {

                String imgURl = response.body().getMeals().get(0).getStrMealThumb();

                Log.i("img", "onResponse:"+imgURl);
                Glide.with(image.getContext()).load(imgURl).into(image);

                txt.setText(response.body().getMeals().get(0).getIdMeal().toString());
                txt2.setText(response.body().getMeals().get(0).getStrMeal());

//////////////////////////////////////////////////////////////////////////////////

            }

            @Override
            public void onFailure(Call<RootMeal> call, Throwable t) {

            }
        });


        Retrofit apiClient2= APIClient.getClient();

        Call<RootMeal> call2= apiInterface.getFilteredMeals("Egyptian");

        call2.enqueue( new Callback<RootMeal>() {
            @Override
            public void onResponse(Call<RootMeal> call2, Response<RootMeal> response) {

                if(response.isSuccessful()){

                    ArrayList<Meal> meals = response.body().getMeals();


                    recyclerView.setHasFixedSize(true);


                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

                    recyclerView.setLayoutManager(linearLayoutManager);

                    linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

                    adapter = new MealAdapter(meals);

                    recyclerView.setAdapter(adapter);


                }
            }

            @Override
            public void onFailure(Call<RootMeal> call, Throwable t) {


            }
        });


    }
}