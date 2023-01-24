package com.example.foodplanner;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIinterface {

    @GET("random.php")
    Observable<RandomRoot> getRandomMeals();


    @GET("filter.php")
    Observable<RandomRoot> getEgyptianMeals(@Query("a") String country);

    @GET("lookup.php")
    Observable<DetailRoot> getByID(@Query("i")Long id);

}
