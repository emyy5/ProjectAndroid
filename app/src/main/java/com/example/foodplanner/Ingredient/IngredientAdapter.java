package com.example.foodplanner.Ingredient;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.Search_FragmentDirections;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> {

    private List<IngredientModel> ingredients;
   String ingredientname;


    public IngredientAdapter(List<IngredientModel> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        IngredientModel ingredient = ingredients.get(position);
        holder.ingredient.setText(ingredient.getStrIngredient());


    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView ingredient;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ingredient = itemView.findViewById(R.id.ingredient);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

//            Ingredient_FragmentDirections.ActionIngredientFragmentToIngredientSearch action = Ingredient_FragmentDirections.actionIngredientFragmentToIngredientSearch();
//            action.setIngredientname(ingredients.get(this.getAdapterPosition()).getStrIngredient());
//            Navigation.findNavController(v).navigate(action);

           Ingredient_FragmentDirections.ActionIngredientFragmentToIngredientSearch action = Ingredient_FragmentDirections.actionIngredientFragmentToIngredientSearch();
           action.setIngredientname(ingredients.get(this.getAdapterPosition()).getStrIngredient());
            Navigation.findNavController(v).navigate(action);

        }
    }
}
