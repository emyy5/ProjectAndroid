package com.example.foodplanner.dataLayer.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.dataLayer.pojes.RandomMeal;
import com.example.foodplanner.dataLayer.pojes.WeekMeals;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;


@Dao
public interface WeekproductDao {

        @Query("SELECT * FROM WeekMeals")
        public Single<List<WeekMeals>> getAllProducts();

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        public Completable insertProduct(WeekMeals product);

        @Delete
        public Completable deleteProduct(WeekMeals product);
    }


