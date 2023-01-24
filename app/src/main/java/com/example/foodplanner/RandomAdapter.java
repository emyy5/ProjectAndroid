package com.example.foodplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RandomAdapter extends RecyclerView.Adapter<RandomAdapter.MyViewHolder>  {

    private ArrayList<RandomMeal> meals;

    public RandomAdapter(ArrayList<RandomMeal> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public  MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        RandomMeal meal = meals.get(position);
        holder.meal_id.setText(meal.getIdMeal().toString());
        holder.meal_name.setText(meal.getStrMeal());

        Glide.with(holder.meal_image.getContext()).load(meal.getStrMealThumb()).into(holder.meal_image);


    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener   {


        public ImageView meal_image;

        public TextView  meal_name,  meal_id;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

           meal_image = itemView.findViewById(R.id.image);
           meal_name=itemView.findViewById(R.id.mealName);
           meal_id= itemView.findViewById(R.id.mealId);

           itemView.setOnClickListener(this);



        }


        @Override
        public void onClick(View v) {
            HomeDirections.ActionHomeToDetailsFragment action = HomeDirections.actionHomeToDetailsFragment();
            action.setId(meals.get(this.getAdapterPosition()).getIdMeal());
            Navigation.findNavController(v).navigate(action);




        }
    }
}

