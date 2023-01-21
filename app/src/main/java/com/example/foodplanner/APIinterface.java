package com.example.foodplanner;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIinterface {

    @GET("random.php")
    Observable<RootMeal> getRandomMeals();


    @GET("filter.php")
    Observable<RootMeal> getEgyptianMeals(@Query("a") String country);


}
