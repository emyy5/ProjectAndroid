package com.example.foodplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class GoogleSignin extends LoginActivity {
        private static final int RC_SIGN_IN = 101;
  GoogleSignInClient googleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       GoogleSignInOptions googleSignInOptions= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
     googleSignInClient=GoogleSignin.getClient(this, googleSignInOptions);
        Intent signInIntent= googleSignInClient.getSignInIntent();
       startActivityForResult(signInIntent, RC_SIGN_IN);


   }
    }


