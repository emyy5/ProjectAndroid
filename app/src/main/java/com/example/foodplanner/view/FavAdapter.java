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
import com.example.foodplanner.dataLayer.Repository;
import com.example.foodplanner.dataLayer.pojes.RandomMeal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {



    private Context context;
    private List<RandomMeal> products = new ArrayList<>();
    private static final String TAG = "MyAdapter";
    private Repository repository;
    public FavAdapter(Context context, List<RandomMeal> favourite) {
        this.context = context;
        this.favourite = favourite;
        repository = new Repository(context);
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


        holder.constraintLayout.setOnClickListener(new View. OnClickListener () {
            @Override
            public void onClick(View view) {
                Toast.makeText (context, products.get(position).getStrMeal(), Toast.LENGTH_SHORT) .show();
            }
        });
        holder.deleteFaV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repository.delete(product);

                notifyItemRemoved(position);
                products.remove(position);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                if (firebaseAuth.getCurrentUser()!=null) {
                    db
                            .collection("database")
                            .document(firebaseAuth.getCurrentUser().getEmail())
                            .collection("Favorite")
                            .document(product.getIdMeal())
                            .delete();
                }
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
        Button deleteFaV;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            titleTxtView = v.findViewById(R.id.Title);
            priceTxtView = v.findViewById(R.id.price1);
            img = v.findViewById(R.id.productImg1);
            constraintLayout = v.findViewById(R.id.raw);
            deleteFaV = v.findViewById(R.id.favRowbtn1);
        }
    }
}
