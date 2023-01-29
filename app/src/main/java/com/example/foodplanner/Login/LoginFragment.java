package com.example.foodplanner.Login;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodplanner.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginFragment extends Fragment {

    private static final int RC_SIGN_IN = 101;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    GoogleSignInClient googleSignInClient;
    ProgressDialog progressDialog;
    View view;
    SharedPreferences sharedPreferences;


    TextView createNewaccount;
    EditText inputEmailLogin, inputLoginpass;
    Button btnlogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ImageView img_google;
    ImageView img_facebook;
    TextView guest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        sharedPreferences = getContext().getSharedPreferences("key", Context.MODE_PRIVATE);

        guest = view.findViewById(R.id.guest);

        btnlogin = view.findViewById(R.id.btn_login);
        inputEmailLogin = view.findViewById(R.id.inputEmailLogin);
        inputLoginpass = view.findViewById(R.id.inputLoginpass);
        img_google=view.findViewById(R.id.img_google);
        img_facebook= view.findViewById(R.id.img_facebook);
        createNewaccount=view.findViewById(R.id.createNewaccount);

        progressDialog = new ProgressDialog(requireContext());



        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();



        if (firebaseAuth.getCurrentUser()!=null){
            Navigation.findNavController(view).navigate(R.id.Home);
        }




        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isUser",false);
                editor.apply();
                Navigation.findNavController(view).navigate(R.id.Home);
            }
        });

        this.view = view ;


        createNewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.register);
            }
        });


        img_facebook.setVisibility(View.GONE);

        img_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(requireContext());
                progressDialog.setMessage("Google sign in...");
                progressDialog.show();
                googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions);
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfarLogin();

            }
        });

    }


    private void perfarLogin() {
        String email = inputEmailLogin.getText().toString();
        String password = inputLoginpass.getText().toString();

        if (!email.matches((emailPattern))) {
            inputEmailLogin.setError("enter context email");
        } else if (password.isEmpty() || password.length() < 8) {
            inputLoginpass.setError("enter proper password");
        } else {
            progressDialog.setMessage("wait while login");
            progressDialog.setMessage("Login");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();

                        Toast.makeText(requireContext(), "Login Sucessful", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isUser",true);
                        editor.apply();
                        Navigation.findNavController(view).navigate(R.id.Home);
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(requireContext(), "incorrect email or password", Toast.LENGTH_SHORT).show();


                    }
                }
            });
        }
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                //finish();
                Toast.makeText(requireContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Log.d(TAG, "signInWithCredential: success");
                    progressDialog.dismiss();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isUser",true);
                    editor.apply();
                    Navigation.findNavController(view).navigate(R.id.Home);

                } else {
                    //finish();
                    progressDialog.dismiss();
                    Toast.makeText(requireContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();


                }
            }
        });
    }


}

