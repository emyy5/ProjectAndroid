package com.example.foodplanner.Category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private List<CategoriesModel> categories;
    String categoryname;


    public CategoryAdapter(List<CategoriesModel> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      //  viewGroup = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CategoriesModel category = categories.get(position);
        holder.categoryName.setText(category.getStrCategory());

        Glide.with(holder.categoryImage.getContext()).load(category.getStrCategoryThumb()).into(holder.categoryImage);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(viewGroup).navigate(new Category_FragmentDirections.ActionCategoryFragmentToSearchCategory(categories.get(position).getStrCategory()));
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ImageView categoryImage;
        public TextView categoryName;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.mealimage);
            categoryName = itemView.findViewById(R.id.countryname);
            itemView.setOnClickListener(this);


        }



        @Override
        public void onClick(View v) {

            Category_FragmentDirections.ActionCategoryFragmentToSearchCategory action = Category_FragmentDirections.actionCategoryFragmentToSearchCategory();
            action.setCategoryname(categories.get(this.getAdapterPosition()).getStrCategory());
           Navigation.findNavController(v).navigate(action);



        }
    }
    }



