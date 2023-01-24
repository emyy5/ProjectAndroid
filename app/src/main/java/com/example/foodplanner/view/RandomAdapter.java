package com.example.foodplanner.view;

import static com.example.foodplanner.view.Register.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.dataLayer.Repository;
import com.example.foodplanner.dataLayer.room.RandomMeal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RandomAdapter extends RecyclerView.Adapter<RandomAdapter.MyViewHolder>  {

    Repository repository;
    private ArrayList<RandomMeal> meals;

    public RandomAdapter(ArrayList<RandomMeal> meals,Context context) {
        this.meals = meals;
        repository= new Repository(context);
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
       holder.favbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               repository.insert(meal);
               FirebaseFirestore db = FirebaseFirestore.getInstance();
               FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
               db
                       .collection("database")
                       .document(firebaseAuth.getCurrentUser().getEmail())
                       .collection("Favorite")
                       .document(meal.getIdMeal())
                       .set(meal);


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
        Button favbtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

           meal_image = itemView.findViewById(R.id.image);
           meal_name=itemView.findViewById(R.id.mealName);
           meal_id= itemView.findViewById(R.id.mealId);
            favbtn= itemView.findViewById(R.id.favbtn);
           itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            HomeDirections.ActionHomeToDetailsFragment action = HomeDirections.actionHomeToDetailsFragment(meals.get(this.getAdapterPosition()).getIdMeal());
            Navigation.findNavController(v).navigate(action);


        }
    }
}

