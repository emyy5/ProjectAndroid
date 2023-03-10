package com.example.foodplanner.features.Favorite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.dataLayer.Repository;
import com.example.foodplanner.dataLayer.pojes.RandomMeal;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FavMealFragment extends Fragment {

    RecyclerView recyclerView;
    List<RandomMeal> myList = new ArrayList<>();
    FavAdapter myAdapter;
    Repository repository;

    @Nullable
    @Override
    public View onCreateView(@androidx.annotation.NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav_meal,container,false);
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("key", Context.MODE_PRIVATE);
        boolean isUser = sharedPreferences.getBoolean("isUser",false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Login Essential");
        builder.setMessage("You should to login first plz ):");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Navigation.findNavController(view).navigate(R.id.Home);
            }
        });
        builder.setCancelable(false);
        builder.create();
        if (!isUser) {
            builder.show();
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.fav_recycler);
        recyclerView.setHasFixedSize(true);

        myAdapter = new FavAdapter(requireContext(), myList);
        repository = new Repository(requireContext());
        repository.getStoreProduct().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<RandomMeal>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull List<RandomMeal> products) {
                        List<RandomMeal> data = products;
                        myAdapter.setProducts(data);
                        myAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });

        recyclerView.setAdapter(myAdapter);

    }

}
