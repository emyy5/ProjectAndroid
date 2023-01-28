package com.example.foodplanner.WeekMeal;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.dataLayer.Repository;
import com.example.foodplanner.dataLayer.pojes.WeekMeals;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class WeekMealsAdapter extends RecyclerView.Adapter<WeekMealsAdapter.MyViewHolder>  {

    Repository repository;
    private List<WeekMeals> weekMealsList;
    private Context context;
    public WeekMealsAdapter(List<WeekMeals> weekMealsList, Context context) {
        this.weekMealsList = weekMealsList;
        this.context  = context;
    }

    @NonNull
    @Override
    public  MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_plan, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WeekMeals weekMeals = weekMealsList.get(position);
        holder.title.setText(weekMeals.getStrMeal());
        holder.subtilte.setText(weekMeals.getStrCategory());
        String imageUrl = weekMeals.getStrMealThumb();
        Glide.with(context).load(imageUrl).into(holder.imagePlan);

holder.removePlan.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        repository.deleteplan(weekMeals,new CompletableObserver() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                notifyItemRemoved(position);
                weekMealsList.remove(position);


                Toast.makeText(context, "Data Removed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }
        });



    }
});
    }


    @Override
    public int getItemCount() {
        return weekMealsList.size();
    }


    public void setWeekMealsList(List<WeekMeals> weekMealsList){
        this.weekMealsList = weekMealsList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder   {
        public ImageView imagePlan;
        public TextView title;
        public TextView subtilte;
        public Button removePlan;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePlan = itemView.findViewById(R.id.imagePlan);
            title = itemView.findViewById(R.id.title);
            subtilte = itemView.findViewById(R.id.subtilte);
            removePlan = itemView.findViewById(R.id.removePlan);

        }

    }
}
