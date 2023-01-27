package com.example.foodplanner.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.foodplanner.R;
import com.example.foodplanner.dataLayer.pojes.RandomMeal;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {



    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

}

