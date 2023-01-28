package com.example.foodplanner.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Category.CategorySearchModel;
import com.example.foodplanner.R;
import com.example.foodplanner.RetroFit.APIinterface;
import com.example.foodplanner.dataLayer.pojes.DetailMeal;
import com.example.foodplanner.dataLayer.pojes.DetailRoot;
import com.example.foodplanner.dataLayer.retrofitApi.APIClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Details_Fragment extends Fragment {

    Long id ;;
    ArrayList<DetailMeal> details;
    List<CategorySearchModel> categoryMealDetails;

    // ui
    VideoView mealVideo;
    ImageView detailImage;
    TextView detailName;
    TextView detailArea;
    TextView detailInstructions ;
    YouTubePlayerView youTubePlayerView;



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

        //  meal details
        id = Details_FragmentArgs.fromBundle(getArguments()).getId();



        detailName = view.findViewById(R.id.detailName);
        detailArea = view.findViewById(R.id.detailArea);
        detailImage= view.findViewById(R.id.detailImage);
        detailInstructions=view.findViewById(R.id.detailInstruction);
        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);



//        detailName.setText(id.toString());
//        detailName.setText(categorymealId.toString());
        getMealsDetails();

//                repository.insertplan(detailMeal.convertToWeakMeal(detailMeal,"saturday"));
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
                            }catch (ArrayIndexOutOfBoundsException exception){
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