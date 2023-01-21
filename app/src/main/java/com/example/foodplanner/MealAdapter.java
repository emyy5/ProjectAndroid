package com.example.foodplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MyViewHolder>  {

    private ArrayList<Meal> meals;

    public MealAdapter(ArrayList<Meal> meals) {
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


        Meal meal = meals.get(position);
        holder.meal_id.setText(meal.getIdMeal().toString());
        holder.meal_name.setText(meal.getStrMeal());

        Glide.with(holder.meal_image.getContext()).load(meal.getStrMealThumb()).into(holder.meal_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Item clicked",Toast.LENGTH_LONG);

            }
        });

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener   {


        public ImageView meal_image;

        public TextView  meal_name,  meal_id;

        @Override
        public void onClick(View view) {





        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

           meal_image = itemView.findViewById(R.id.image);
           meal_name=itemView.findViewById(R.id.mealName);
           meal_id= itemView.findViewById(R.id.mealId);




        }




    }
}

