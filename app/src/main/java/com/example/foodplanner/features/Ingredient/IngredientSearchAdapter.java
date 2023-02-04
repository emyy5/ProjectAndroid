package com.example.foodplanner.features.Ingredient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Category.Category_FragmentDirections;
import com.example.foodplanner.Ingredient.IngredientSearchDirections;
import com.example.foodplanner.R;

import java.util.List;

public class IngredientSearchAdapter extends RecyclerView.Adapter<IngredientSearchAdapter.MyViewHolder> {

    private List<IngredientSearchModel> meals;


    public IngredientSearchAdapter(List<IngredientSearchModel> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_ingredient, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        IngredientSearchModel meal = meals.get(position);

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

            mealImage = itemView.findViewById(R.id.mealimagee);
            mealName = itemView.findViewById(R.id.mealnamee);
            itemView.setOnClickListener(this);

        }



        @Override
        public void onClick(View v) {


            IngredientSearchDirections.ActionIngredientSearchToDetailsFragment action = IngredientSearchDirections.actionIngredientSearchToDetailsFragment();
            action.setId(meals.get(this.getAdapterPosition()).getIdMeal());
            Navigation.findNavController(v).navigate(action);

        }
    }
}
