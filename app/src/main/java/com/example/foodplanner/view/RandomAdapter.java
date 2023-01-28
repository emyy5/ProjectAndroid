package com.example.foodplanner.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.dataLayer.Repository;
import com.example.foodplanner.dataLayer.pojes.RandomMeal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RandomAdapter extends RecyclerView.Adapter<RandomAdapter.MyViewHolder>  {

    Repository repository;
    private ArrayList<RandomMeal> meals;
    long id ;

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
//       holder.favbtn.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               repository.insert(meal);
//               FirebaseFirestore db = FirebaseFirestore.getInstance();
//               FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//               if (firebaseAuth.getCurrentUser()!=null){
//                   db
//                           .collection("database")
//                           .document(firebaseAuth.getCurrentUser().getEmail())
//                           .collection("Favorite")
//                           .document(meal.getIdMeal())
//                           .set(meal);
//               }
//
//           }
//       });

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public ImageView meal_image;

        public TextView  meal_name,  meal_id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

           meal_image = itemView.findViewById(R.id.image);
           meal_name=itemView.findViewById(R.id.mealName);
           meal_id= itemView.findViewById(R.id.mealId);

        }


        @Override
        public void onClick(View v) {
            HomeFragmentDirections.ActionHomeToDetailsFragment action = HomeFragmentDirections.actionHomeToDetailsFragment();
            action.setId(Long.parseLong(meals.get(this.getAdapterPosition()).getIdMeal()));
            Navigation.findNavController(v).navigate(action);


        }
    }
}

