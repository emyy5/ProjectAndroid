package com.example.foodplanner.Country;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder>  {
    private List<CountryModel> countries;
    String countryName;
    public CountryAdapter(List<CountryModel> countries) {
        this.countries = countries;
    }

    @NonNull
    @Override
    public  MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CountryModel country = countries.get(position);
         holder.country.setText(country.getStrArea());

    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener   {
        public TextView country;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            country = itemView.findViewById(R.id.countryname);
            itemView.setOnClickListener(this);

        }

        @Override
       public void onClick(View v) {
          Country_FragmentDirections.ActionCountryFragmentToCountrySearch action = Country_FragmentDirections.actionCountryFragmentToCountrySearch();
          action.setCountryname(countries.get(this.getAdapterPosition()).getStrArea());
          Navigation.findNavController(v).navigate(action);
        }
    }
}
