package com.example.foodplanner.dataLayer;

import android.content.Context;
import android.widget.Toast;

import androidx.room.Query;

import com.example.foodplanner.dataLayer.pojes.RandomMeal;
import com.example.foodplanner.dataLayer.pojes.WeekMeals;
import com.example.foodplanner.dataLayer.room.FavproductDao;
import com.example.foodplanner.dataLayer.room.MealDatabase;
import com.example.foodplanner.dataLayer.room.WeekproductDao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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
  private  WeekproductDao weekproductDao;
    private Single<List<RandomMeal>> storeProduct;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fdb;

    public Repository(Context context) {
        this.context = context;
        MealDatabase db = MealDatabase.getInstance(context.getApplicationContext());
        favproductDao = db.favProductDao();
        weekproductDao= db.weekproductDao();


        firebaseAuth = FirebaseAuth.getInstance();
        fdb  = FirebaseFirestore.getInstance();
    }

    public Single<List<RandomMeal>> getStoreProduct() {
        return favproductDao.getAllProducts();
    }


    public Single<List<WeekMeals>> getAllWeekMeals(){
        return weekproductDao.getAllWeekMeals();
    }

    public Single<List<WeekMeals>> getAllWeekMealsByDay(String day){
        return weekproductDao.getAllWeekMealsByDay(day);
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
    public void insertplan (WeekMeals product,CompletableObserver completableObserver){
       weekproductDao.insertProduct(product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completableObserver);
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
                        fdb
                                .collection("database")
                                .document(firebaseAuth.getCurrentUser().getEmail())
                                .collection("Favorite")
                                .document(product.getIdMeal())
                                .delete();

                        Toast.makeText(context, "Data Removed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void deleteplan (WeekMeals weekMeals,CompletableObserver completableObserver){
        weekproductDao
                .deleteProduct(weekMeals).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completableObserver);

    }
}
