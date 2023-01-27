package com.example.foodplanner.dataLayer.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodplanner.dataLayer.pojes.RandomMeal;
import com.example.foodplanner.dataLayer.pojes.WeekMeals;


@Database(entities = {RandomMeal.class , WeekMeals.class},version = 3,exportSchema = false)
public abstract class MealDatabase extends RoomDatabase{

    private static MealDatabase instance = null;

    public abstract FavproductDao favProductDao();
    public abstract WeekproductDao weekproductDao();

    public static synchronized MealDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MealDatabase.class,
                    "singleMeal").build();
        }
        return instance;
    }
}
