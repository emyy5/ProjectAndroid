package com.example.foodplanner.features.Category;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.foodplanner.Category.SearchCategoryDirections;
import com.example.foodplanner.R;

import java.util.List;

public class CategorySearchAdapter extends RecyclerView.Adapter<CategorySearchAdapter.MyViewHolder> {

    private List<CategorySearchModel> meals;



    public CategorySearchAdapter(List<CategorySearchModel> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_category, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CategorySearchModel meal = meals.get(position);


        holder.mealName.setText(meal.getStrMeal());

        Glide.with(holder.mealImage.getContext()).load(meal.getStrMealThumb()).into(holder.mealImage);

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ImageView mealImage;
        public TextView mealName;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mealImage = itemView.findViewById(R.id.mealCategoryImage);
            mealName = itemView.findViewById(R.id.mealCategoryName);
            itemView.setOnClickListener(this);


        }



        @Override
        public void onClick(View v) {

            SearchCategoryDirections.ActionSearchCategoryToDetailsFragment action = SearchCategoryDirections.actionSearchCategoryToDetailsFragment();
            action.setId(Long.parseLong( meals.get(this.getAdapterPosition()).getIdMeal()));
            Navigation.findNavController(v).navigate(action);


        }
    }
}
