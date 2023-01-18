package com.example.foodplanner;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIinterface {

    @GET("random.php")
    Call<RootMeal> getRandomMeals();


    @GET("filter.php")
    Call<RootMeal> getFilteredMeals(@Query("a") String country);


}
