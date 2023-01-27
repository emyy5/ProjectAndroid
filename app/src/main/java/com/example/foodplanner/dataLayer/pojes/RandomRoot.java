package com.example.foodplanner.dataLayer.pojes;

import java.util.ArrayList;

public class RandomRoot {

       private ArrayList<RandomMeal> meals;


        public RandomRoot(ArrayList<RandomMeal> meals) {
            this.meals = meals;
        }


        public ArrayList<RandomMeal> getMeals() {
            return meals;
        }



        public void setMeals(ArrayList<RandomMeal> meals) {
            this.meals = meals;
        }


}