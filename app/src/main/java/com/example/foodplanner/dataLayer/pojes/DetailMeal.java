package com.example.foodplanner.dataLayer.pojes;
//Model -- POJO
public class DetailMeal {
    public String idMeal;
    public String strMeal;
    public String strArea;
    public String strInstructions;
    public String strMealThumb;
    public String strYoutube;


    public DetailMeal(String idMeal, String strMeal, String strArea, String strInstructions, String strMealThumb, String strYoutube) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strArea = strArea;
        this.strInstructions = strInstructions;
        this.strMealThumb = strMealThumb;
        this.strYoutube = strYoutube;

    }

    public String getIdMeal() {
        return idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public String getStrArea() {
        return strArea;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public String getStrYoutube() {
        return strYoutube;
    }
    public RandomMeal convertoRandomMeal(DetailMeal detailMeal)

    {   RandomMeal randomMeal=new RandomMeal();
        randomMeal.setIdMeal(detailMeal.getIdMeal());
        randomMeal.setStrArea(detailMeal.getStrArea());
        randomMeal.setStrMeal(detailMeal.getStrMeal());
        randomMeal.setStrMealThumb(detailMeal.getStrMealThumb());
        randomMeal.setStrInstructions(detailMeal.getStrInstructions());
        return randomMeal;
    }
    public WeekMeals convertToWeakMeal(DetailMeal detailMeal,String day){
        WeekMeals weekMeals=new WeekMeals();
        weekMeals.setDay(day);
        weekMeals.setIdMeal(detailMeal.getIdMeal());
        weekMeals.setStrArea(detailMeal.getStrArea());
        weekMeals.setStrMealThumb(detailMeal.getStrMealThumb());
        weekMeals.setStrMeal(detailMeal.getStrMeal());
        return weekMeals;
    }
}
