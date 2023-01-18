package com.example.foodplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    TextView createNewaccount;
    EditText inputEmailLogin, inputLoginpass;
    Button btnlogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    ImageView img_google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnlogin = findViewById(R.id.btn_login);

        inputEmailLogin = findViewById(R.id.inputEmailLogin);
        inputLoginpass = findViewById(R.id.inputLoginpass);
        img_google=findViewById(R.id.img_google);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        createNewaccount=findViewById(R.id.createNewaccount);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        createNewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent= new Intent(LoginActivity.this, RegisterActivity.class);
                 startActivity(intent);
            }
        });

        img_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, GoogleSignin.class);
                startActivity(intent);
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
                        sendUserToNextActivity();
                        Toast.makeText(LoginActivity.this, "Login Sucessfull", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Login Sucessfull", Toast.LENGTH_SHORT).show();


                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {

        Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

