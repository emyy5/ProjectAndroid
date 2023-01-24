package com.example.foodplanner.dataLayer;

import android.content.Context;
import android.widget.Toast;


import com.example.foodplanner.dataLayer.room.FavproductDao;
import com.example.foodplanner.dataLayer.room.MealDatabase;
import com.example.foodplanner.dataLayer.room.RandomMeal;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repository {

    private Context context;
    private FavproductDao favproductDao;
    private Single<List<RandomMeal>> storeProduct;

    public Repository(Context context) {
        this.context = context;
        MealDatabase db = MealDatabase.getInstance(context.getApplicationContext());
        favproductDao = db.favProductDao();
        storeProduct = favproductDao.getAllProducts();
    }

    public Single<List<RandomMeal>> getStoreProduct() {
        return storeProduct;
    }

    public void insert (RandomMeal product){
        favproductDao.insertProduct(product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Data Inserted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }


    public void delete (RandomMeal product){
        favproductDao
                .deleteProduct(product).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Data Removed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}