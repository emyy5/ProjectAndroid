package com.example.foodplanner.WeekMeal;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.foodplanner.R;
import com.example.foodplanner.dataLayer.Repository;
import com.example.foodplanner.dataLayer.pojes.WeekMeals;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class ChooseWeekMealDialog extends Dialog {

    Context context;
    WeekMeals weekMeals;
    Repository repository;
    String dayResult;
    public ChooseWeekMealDialog(@NonNull Context context, WeekMeals weekMeals) {
        super(context);
        this.context  =context;
        this.weekMeals = weekMeals;
        repository = new Repository(context);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_weekplan_choice);
        RadioGroup radioGroup  = findViewById(R.id.weekChoiceGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int button) {
                switch (button){
                    case R.id.sat_choice:
                        dayResult = WeekMealFragment.SAT;
                        break;
                    case R.id.sun_choice:
                        dayResult = WeekMealFragment.SUN;
                        break;
                    case R.id.mon_choice:
                        dayResult = WeekMealFragment.MON;
                        break;
                    case R.id.Tus_choice:
                        dayResult = WeekMealFragment.TUE;
                        break;
                    case R.id.wen_choice:
                        dayResult = WeekMealFragment.WEN;
                        break;
                    case R.id.th_choice:
                        dayResult = WeekMealFragment.THUR;
                        break;
                    case R.id.fir_choice:
                        dayResult = WeekMealFragment.FRIDAY;
                        break;
                }


                weekMeals.setDay(dayResult);
                repository.insertplan(weekMeals, new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Week Plan Inserted", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

            }
        });

    }
}
