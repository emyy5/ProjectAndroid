package com.example.foodplanner.view;

import android.annotation.SuppressLint;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.dataLayer.room.RandomMeal;
import com.example.foodplanner.dataLayer.Repository;


import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {



    private Context context;
    private List<RandomMeal> products;
    private static final String TAG = "MyAdapter";
    private Repository repository;
    public FavAdapter(Context context, List<RandomMeal> favourite) {
        this.context = context;
        this.favourite = favourite;
        notifyDataSetChanged();
    }

    private List<RandomMeal> favourite;
    public List<RandomMeal> getProducts() {
        return products;
    }

    public void setProducts(List<RandomMeal> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext ());
        View v = inflater.inflate (R.layout.row_fav, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RandomMeal product=products.get(position);
        holder.titleTxtView.setText(products.get(position).getStrMeal());
        holder.priceTxtView.setText(""+products.get(position).getIdMeal());
        Glide
                .with(holder.img.getContext())
                .load(product.getStrMealThumb())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.img);
        Log.i(TAG, "onBindViewHolder: for photo" + products.get(position).getStrMealThumb());

        repository = new Repository(context);
        holder.constraintLayout.setOnClickListener(new View. OnClickListener () {
            @Override
            public void onClick(View view) {
                Toast.makeText (context, products.get(position).getStrMeal(), Toast.LENGTH_SHORT) .show();
            }
        });
        holder.addToFave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: product" + product);
                repository.delete(product);
            }
        });

    }




    @Override
    public int getItemCount() {
        return products.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTxtView;
        public TextView priceTxtView;
        public View layout;
        public ImageView img;
        public ConstraintLayout constraintLayout;
        Button addToFave;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            titleTxtView = v.findViewById(R.id.Title);
            priceTxtView = v.findViewById(R.id.price1);
            img = v.findViewById(R.id.productImg1);
            constraintLayout = v.findViewById(R.id.raw);
            addToFave = v.findViewById(R.id.favRowbtn1);
        }
    }
}
