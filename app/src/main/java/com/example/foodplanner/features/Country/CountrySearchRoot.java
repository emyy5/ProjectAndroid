package com.example.foodplanner.features.Country;

import java.util.ArrayList;
import java.util.List;

public class CountrySearchRoot {
	private List<CountrySearchModel> meals = new ArrayList<>();

	public List<CountrySearchModel> getMeals(){
		return meals;
	}
}