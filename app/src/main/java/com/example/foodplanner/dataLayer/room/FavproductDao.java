package com.example.foodplanner.dataLayer.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.dataLayer.pojes.RandomMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;


@Dao
public interface FavproductDao {

    @Query("SELECT * FROM singleMeal")
    public Single<List<RandomMeal>> getAllProducts();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public Completable insertFavorite(RandomMeal randomMeal);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public Completable insertAllFavorite(List<RandomMeal> randomMealList);


    @Query("DELETE FROM singleMeal")
    public Completable removeAllFavoriteMeals();
    @Delete
    public Completable deleteFavorite(RandomMeal randomMeal);


}


