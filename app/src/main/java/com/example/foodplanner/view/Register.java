package com.example.foodplanner.view;

import android.app.ProgressDialog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Register extends Fragment {


    public static final String TAG = "RegisterActivity";

    TextView haveAccount;
    EditText inputRegisterEmail, inputRegisterpass, inputConfirmpass;
    Button btn_register;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    View view;

    public Register() {
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        haveAccount = view.findViewById(R.id.haveAccount);
        inputRegisterEmail = view.findViewById(R.id.inputRegisterEmail);
        inputRegisterpass = view.findViewById(R.id.inputRegisterpass);
        inputConfirmpass = view.findViewById(R.id.inputConfirmpass);
        btn_register = view.findViewById(R.id.btn_register);
        progressDialog = new ProgressDialog(requireContext());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        this.view = view;

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_register_to_logIn);
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_register_to_Home);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerforAuth();
            }
        });
    }

    private void PerforAuth() {
        String email = inputRegisterEmail.getText().toString();
        String password = inputRegisterpass.getText().toString();
        String confirmPass = inputConfirmpass.getText().toString();

        if (!password.equals(confirmPass)) {
            inputConfirmpass.setError("password not match");
        } else if (!email.matches(emailPattern)) {
            inputRegisterEmail.setError("Enter valid Email");
        } else if (password.isEmpty() || password.length() < 8) {
            inputRegisterpass.setError("Enter valid password");
        } else {

            progressDialog.setMessage("Please wait while Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Navigation.findNavController(view).navigate(R.id.action_register_to_Home);
                    Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "PerforAuth: " + task.getException().toString());
                    progressDialog.dismiss();
                    Toast.makeText(requireContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                }

            });

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: " + e);
                }
            });


        }


    }

    private void sendUserToNextActivity() {
//        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);

        Navigation.findNavController(view).navigate(R.id.action_register_to_Home);

    }
}