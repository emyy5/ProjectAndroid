package com.example.foodplanner.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.foodplanner.dataLayer.Repository;
import com.example.foodplanner.dataLayer.pojes.RandomMeal;
import com.example.foodplanner.dataLayer.pojes.RandomRoot;
import com.example.foodplanner.dataLayer.retrofitApi.APIClient;
import com.example.foodplanner.dataLayer.retrofitApi.APIinterface;
import com.example.foodplanner.dataLayer.pojes.DetailMeal;
import com.example.foodplanner.dataLayer.pojes.DetailRoot;
import com.example.foodplanner.R;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Details_Fragment extends Fragment {

    String id ;
    ArrayList<DetailMeal> details;

    // ui
    VideoView mealVideo;
    ImageView detailImage;
    TextView detailName;
    TextView detailArea;
    TextView detailInstructions ;
    Button addToFav;
    Repository repository;
    DetailMeal detailMeal;
    Button addToPlan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        id = Details_FragmentArgs.fromBundle(getArguments()).getId();

        repository=new Repository(requireContext());
        detailName = view.findViewById(R.id.detailName);
        detailArea = view.findViewById(R.id.detailArea);
        detailImage= view.findViewById(R.id.detailImage);
        detailInstructions=view.findViewById(R.id.detailInstruction);
        mealVideo = view.findViewById(R.id.mealVideo);
        addToFav=view.findViewById(R.id.addToFav);
        addToPlan=view.findViewById(R.id.addToPlan);

        detailName.setText(id.toString());
        getMealsDetails();
        addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repository.insert(detailMeal.convertoRandomMeal(detailMeal));
            }
        });
        addToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                repository.insertplan(detailMeal.convertToWeakMeal(detailMeal,"saturday"));
            }
        });
    }
    public void getMealsDetails() {
        Retrofit apiClient = APIClient.getClient();
        APIinterface apiInterface = apiClient.create(APIinterface.class);
        Observable<DetailRoot> MealDetail = apiInterface.getByID(id);
        Observable<DetailRoot> MealDetailObservable = apiInterface.getByID(id);
        MealDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    details = (ArrayList<DetailMeal>) response.getMeals();
                    String name = details.get(0).getStrMeal();
                    detailName.setText(name);
                detailMeal=response.getMeals().get(0);

                }, error -> {


       });
}

}