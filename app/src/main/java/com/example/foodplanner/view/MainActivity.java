package com.example.foodplanner.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.foodplanner.R;
import com.example.foodplanner.WeekMeal.ChooseWeekMealDialog;
import com.example.foodplanner.dataLayer.Repository;
import com.example.foodplanner.dataLayer.pojes.RandomMeal;
import com.example.foodplanner.dataLayer.pojes.WeekMeals;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    NavController navController;
    Repository repository;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repository = new Repository(getApplicationContext());
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        BottomNavigationView bottomNavigationView = findViewById(R.id.buton_navigation);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                switch (navDestination.getId()){
                    case  R.id.splashScrren:
                    case  R.id.logIn:
                    case  R.id.register:
                        bottomNavigationView.setVisibility(View.GONE);
                        break;
                    default:
                        bottomNavigationView.setVisibility(View.VISIBLE);
                }



            }
        });
//        NavigationUI.setupActionBarWithNavController(this, navController);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_choice,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.backup:
                backupData();
                return true;
            case R.id.restore:
                restoreData();
                return true;
            case R.id.logout:
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                SharedPreferences sharedPreferences = getSharedPreferences("key",MODE_PRIVATE);
                firebaseAuth.signOut();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                navController.navigate(R.id.logIn);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void backupData() {
        repository.getStoreProduct().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new SingleObserver<List<RandomMeal>>() {
                                    @Override
                                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<RandomMeal> randomMealList) {

                                        for (RandomMeal randomMeal:randomMealList){
                                            firestore
                                                    .collection("database")
                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                    .collection("Favorite")
                                                    .document(randomMeal.getIdMeal())
                                                    .set(randomMeal);
                                        }
                                    }

                                    @Override
                                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                                    }
                                });
        repository.getAllWeekMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<WeekMeals>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<WeekMeals> weekMeals) {
                        for (WeekMeals randomMeal:weekMeals){
                            firestore
                                    .collection("database")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("WeekMeals")
                                    .document(randomMeal.getIdMeal())
                                    .set(randomMeal);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }
    private void restoreData() {
        repository.removeAllWeekMeals().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                        List<WeekMeals> weekMealsList = new ArrayList<>();
                        if (firebaseAuth.getCurrentUser()!=null){
                            firestore
                                    .collection("database")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("WeekMeals")
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                                                weekMealsList.add(documentSnapshot.toObject(WeekMeals.class));
                                            }

                                            repository.insertAllWeekMeal(weekMealsList)
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new CompletableObserver() {
                                                        @Override
                                                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                                                        }

                                                        @Override
                                                        public void onComplete() {
                                                            Toast.makeText(getApplicationContext(), "Data of Week Meal Restored", Toast.LENGTH_SHORT).show();

                                                        }

                                                        @Override
                                                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                                            Toast.makeText(getApplicationContext(), "Data of Week Meal failed to restored", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@androidx.annotation.NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "Data of Week Meal failed to restored", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        }



                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
        repository.removeAllFavoriteMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onComplete() {

                        List<RandomMeal> randomMealList = new ArrayList<>();
                        if (firebaseAuth.getCurrentUser()!=null){
                            firestore
                                    .collection("database")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("Favorite")
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                                                randomMealList.add(documentSnapshot.toObject(RandomMeal.class));
                                            }
                                            repository.insertAllFavorite(randomMealList).subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new CompletableObserver() {
                                                        @Override
                                                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                                                            Log.d(TAG, "onSubscribe: ");
                                                        }

                                                        @Override
                                                        public void onComplete() {
                                                            Toast.makeText(getApplicationContext(), "Data of favorite Restore", Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                                            Toast.makeText(getApplicationContext(), "Data of favorite is fail", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@androidx.annotation.NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "Data of favorite is fail", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                });
}





}

