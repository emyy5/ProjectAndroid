package com.example.foodplanner.dataLayer.RetroFit;

//import com.example.foodplanner.Category.CategoryRoot;
//import com.example.foodplanner.Category.CategorySearchRoot;
//import com.example.foodplanner.Country.CountryRoot;
//import com.example.foodplanner.Country.CountrySearchRoot;
//import com.example.foodplanner.Ingredient.IngredientRoot;
//import com.example.foodplanner.Ingredient.IngredientSearchRoot;
import com.example.foodplanner.features.Category.CategoryRoot;
import com.example.foodplanner.features.Category.CategorySearchRoot;
import com.example.foodplanner.features.Country.CountryRoot;
import com.example.foodplanner.features.Country.CountrySearchRoot;
import com.example.foodplanner.features.Ingredient.IngredientRoot;
import com.example.foodplanner.features.Ingredient.IngredientSearchRoot;
import com.example.foodplanner.dataLayer.pojes.DetailRoot;
import com.example.foodplanner.dataLayer.pojes.RandomRoot;

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


    @GET("categories.php")
    Observable<CategoryRoot> getCategories();

    @GET("filter.php")
    Observable<CategorySearchRoot> getMealsByCategory(@Query("c")String categoryname);

    @GET("list.php")
    Observable<CountryRoot> getCountry(@Query("a") String list);


    @GET("filter.php")
    Observable<CountrySearchRoot> getMealsByCountry(@Query("a") String countryname);

    @GET("list.php")
    Observable<IngredientRoot> getIngredients(@Query("i") String list);

    @GET("filter.php")
    Observable<IngredientSearchRoot>getIngredientMeals(@Query("i") String ingredientname);


}