package com.example.foodplanner;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {}

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        RetrofitService.apiInterface.getIngredientsList("list").enqueue(new Callback<IngredientList>() {
//            @Override
//            public void onResponse(Call<IngredientList> call, Response<IngredientList> response) {
//                if (response.isSuccessful()){
//
//                    Log.e("success", response.body().toString());
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<IngredientList> call, Throwable t) {
//              Log.e("failure", t.)
//            }
//        });
//    }
//}