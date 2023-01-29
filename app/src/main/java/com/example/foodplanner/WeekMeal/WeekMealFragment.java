package com.example.foodplanner.WeekMeal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;
import com.example.foodplanner.dataLayer.Repository;
import com.example.foodplanner.dataLayer.pojes.RandomMeal;
import com.example.foodplanner.dataLayer.pojes.WeekMeals;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WeekMealFragment extends Fragment {

    RecyclerView recylerViewSat;
    RecyclerView recylerViewSun;
    RecyclerView recylerViewMon;
    RecyclerView recylerViewTus;
    RecyclerView recylerViewwens;
    RecyclerView recylerViewthurs;
    RecyclerView recylerViewfriday;

    List<WeekMeals> weekMealsListSat = new ArrayList<>();
    List<WeekMeals> weekMealsListSun = new ArrayList<>();
    List<WeekMeals> weekMealsListMon = new ArrayList<>();
    List<WeekMeals> weekMealsListTus = new ArrayList<>();
    List<WeekMeals> weekMealsListWens = new ArrayList<>();
    List<WeekMeals> weekMealsListThu = new ArrayList<>();
    List<WeekMeals> weekMealsListFri = new ArrayList<>();



    public static String SAT = "sat";
    public static String SUN = "Sun";
    public static String MON = "mon";
    public static String TUE = "thu";
    public static String WEN = "wen";
    public static String THUR = "thur";
    public static String FRIDAY = "friday";

    Repository repository;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_week_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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

        repository = new Repository(requireContext());
        recylerViewSat = view.findViewById(R.id.recylerViewSat);
        recylerViewSat.setHasFixedSize(true);
        WeekMealsAdapter adapterSat = new WeekMealsAdapter(weekMealsListSat,getContext());
        recylerViewSat.setAdapter(adapterSat);


        recylerViewSun = view.findViewById(R.id.recylerViewSun);
        recylerViewSun.setHasFixedSize(true);
        WeekMealsAdapter adapterSun = new WeekMealsAdapter(weekMealsListSun,getContext());
        recylerViewSun.setAdapter(adapterSun);

        recylerViewMon = view.findViewById(R.id.recylerViewMon);
        recylerViewMon.setHasFixedSize(true);
        WeekMealsAdapter adapterMon = new WeekMealsAdapter(weekMealsListMon,getContext());
        recylerViewMon.setAdapter(adapterMon);


        recylerViewTus = view.findViewById(R.id.recylerViewTus);
        recylerViewTus.setHasFixedSize(true);
        WeekMealsAdapter adapterTus = new WeekMealsAdapter(weekMealsListTus,getContext());
        recylerViewTus.setAdapter(adapterTus);

        recylerViewwens = view.findViewById(R.id.recylerViewwens);
        recylerViewwens.setHasFixedSize(true);
        WeekMealsAdapter adapterWen = new WeekMealsAdapter(weekMealsListWens,getContext());
        recylerViewwens.setAdapter(adapterWen);

        recylerViewthurs = view.findViewById(R.id.recylerViewthurs);
        recylerViewthurs.setHasFixedSize(true);
        WeekMealsAdapter adapterThus = new WeekMealsAdapter(weekMealsListThu,getContext());
        recylerViewthurs.setAdapter(adapterThus);

        recylerViewfriday = view.findViewById(R.id.recylerViewfriday);
        recylerViewfriday.setHasFixedSize(true);
        WeekMealsAdapter adapterFri = new WeekMealsAdapter(weekMealsListFri,getContext());
        recylerViewfriday.setAdapter(adapterFri);


        repository.getAllWeekMealsByDay(SAT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<WeekMeals>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<WeekMeals> weekMeals) {
                        adapterSat.setWeekMealsList(weekMeals);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
        repository.getAllWeekMealsByDay(SUN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<WeekMeals>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<WeekMeals> weekMeals) {
                        adapterSun.setWeekMealsList(weekMeals);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

        repository.getAllWeekMealsByDay(MON)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<WeekMeals>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<WeekMeals> weekMeals) {
                        adapterMon.setWeekMealsList(weekMeals);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
        repository.getAllWeekMealsByDay(TUE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<WeekMeals>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<WeekMeals> weekMeals) {
                        adapterTus.setWeekMealsList(weekMeals);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

        repository.getAllWeekMealsByDay(WEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<WeekMeals>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<WeekMeals> weekMeals) {
                        adapterWen.setWeekMealsList(weekMeals);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

        repository.getAllWeekMealsByDay(THUR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<WeekMeals>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<WeekMeals> weekMeals) {
                        adapterThus.setWeekMealsList(weekMeals);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

        repository.getAllWeekMealsByDay(FRIDAY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<WeekMeals>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<WeekMeals> weekMeals) {
                        adapterFri.setWeekMealsList(weekMeals);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });









    }
}