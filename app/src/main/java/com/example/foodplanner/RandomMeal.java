package com.example.foodplanner;
/*
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;*/

//@Entity(tableName = "singleMeal")
 public class RandomMeal {
   // @ColumnInfo(name = "idMeal")
   // @PrimaryKey
   private Long idMeal;
    //@ColumnInfo(name = "strMeal")
    private String strMeal;
 //   @ColumnInfo(name = "strMealThumb")
        private String strMealThumb;


    public RandomMeal(String strMeal,
                      String strMealThumb,
                      Long idMeal) {

        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.idMeal = idMeal;
    }

    public RandomMeal() {
    }
        public String getStrMeal() {
            return strMeal;
        }

        public void setStrMeal(String strMeal) {
            this.strMeal = strMeal;
        }

        public String getStrMealThumb() {
            return strMealThumb;
        }

        public void setStrMealThumb(String strMealThumb) {
            this.strMealThumb = strMealThumb;
        }

        public Long getIdMeal() {
            return idMeal;
        }

        public void setIdMeal(Long idMeal) {
            this.idMeal = idMeal;
        }

    }