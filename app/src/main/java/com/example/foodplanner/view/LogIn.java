package com.example.foodplanner.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LogIn extends Fragment {

    TextView createNewaccount, asGuestTv;
    EditText inputEmailLogin, inputLoginpass;
    Button btnlogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    ImageView img_google;
    View view ;


    public LogIn() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnlogin = view.findViewById(R.id.btn_login);

        asGuestTv = view.findViewById(R.id.asGuestTv);
        inputEmailLogin = view.findViewById(R.id.inputEmailLogin);
        inputLoginpass = view.findViewById(R.id.inputLoginpass);
        img_google=view.findViewById(R.id.img_google);
        progressDialog = new ProgressDialog(requireContext());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        createNewaccount=view.findViewById(R.id.createNewaccount);
        this.view = view ;


        asGuestTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
//                navController.navigate(R.id.action_logIn_to_Home, true);
                Navigation.findNavController(view).navigate(LogInDirections.actionLogInToHome());
            }
        });


        createNewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_logIn_to_register);

            }
        });

        img_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_logIn_to_googleSignin);
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
                        Navigation.findNavController(view).navigate(R.id.action_logIn_to_Home);
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(requireContext(), "incorrect email or password", Toast.LENGTH_SHORT).show();


                    }
                }
            });
        }
    }



}
