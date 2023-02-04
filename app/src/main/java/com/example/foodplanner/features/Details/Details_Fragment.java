package com.example.foodplanner.features.Details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Details.Details_FragmentArgs;
import com.example.foodplanner.R;
import com.example.foodplanner.dataLayer.RetroFit.APIinterface;
import com.example.foodplanner.features.WeekMeal.ChooseWeekMealDialog;
import com.example.foodplanner.dataLayer.Repository;
import com.example.foodplanner.dataLayer.pojes.DetailMeal;
import com.example.foodplanner.dataLayer.pojes.DetailRoot;
import com.example.foodplanner.dataLayer.pojes.RandomMeal;
import com.example.foodplanner.dataLayer.pojes.WeekMeals;
import com.example.foodplanner.dataLayer.retrofitApi.APIClient;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Details_Fragment extends Fragment {

    Long id;
    ArrayList<DetailMeal> details;

    // ui
    ImageView detailImage;
    TextView detailName;
    TextView detailArea;
    TextView detailInstructions;
    YouTubePlayerView youTubePlayerView;
    Button Fav_btn;
    Button Plan_btn;

    Repository repository;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //  meal details
        id = Details_FragmentArgs.fromBundle(getArguments()).getId();

        repository = new Repository(getContext());

        Fav_btn = view.findViewById(R.id.Fav_btn);
        Plan_btn = view.findViewById(R.id.Plan_btn);
        detailName = view.findViewById(R.id.detailName);
        detailArea = view.findViewById(R.id.detailArea);
        detailImage = view.findViewById(R.id.detailImage);
        detailInstructions = view.findViewById(R.id.detailInstruction);
        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        getMealsDetails();

        Fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RandomMeal randomMeal = new RandomMeal();
                DetailMeal detailMeal = details.get(0);

                randomMeal.setIdMeal(detailMeal.getIdMeal());
                randomMeal.setStrMeal(detailMeal.getStrMeal());
                randomMeal.setStrMealThumb(detailMeal.getStrMealThumb());
                randomMeal.setStrArea(detailMeal.getStrArea());
                randomMeal.setStrInstructions(detailMeal.getStrInstructions());
                randomMeal.setStrYoutube(detailMeal.getStrYoutube());

                repository.insert(randomMeal, new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(getContext(), "Favorite saved", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
            }
        });


        Plan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeekMeals weekMeals = new WeekMeals();
                DetailMeal detailMeal = details.get(0);

                weekMeals.setIdMeal(detailMeal.getIdMeal());
                weekMeals.setStrMeal(detailMeal.getStrMeal());
                weekMeals.setStrMealThumb(detailMeal.getStrMealThumb());
                weekMeals.setStrArea(detailMeal.getStrArea());
                weekMeals.setStrInstructions(detailMeal.getStrInstructions());
                weekMeals.setStrYoutube(detailMeal.getStrYoutube());
                ChooseWeekMealDialog dialog = new ChooseWeekMealDialog(getContext(),weekMeals);
                dialog.create();
                dialog.show();
            }
        });

    }


    public void getMealsDetails() {

        Retrofit apiClient = APIClient.getClient();
        APIinterface apiInterface = apiClient.create(APIinterface.class);
        Observable<DetailRoot> MealDetail = apiInterface.getByID(id);
        MealDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    details = (ArrayList<DetailMeal>) response.getMeals();
                    String name = details.get(0).strMeal;
                    detailName.setText(name);
                    String area = details.get(0).strArea;
                    detailArea.setText(area);
                    String instractions = details.get(0).strInstructions;
                    detailInstructions.setText(instractions);
                    String imgURl = details.get(0).strMealThumb;
                    Glide.with(detailImage.getContext()).load(imgURl).into(detailImage);
                    youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            String[] videoId = details.get(0).getStrYoutube().split("=");

                            try {
                                youTubePlayer.loadVideo(videoId[1], 0);
                            } catch (ArrayIndexOutOfBoundsException exception) {
                                exception.printStackTrace();
                            }

                        }
                    });

                }, error -> {


                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        youTubePlayerView.release();
    }
}