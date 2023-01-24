//package com.example.foodplanner;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.L;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//public class RegisterActivity extends AppCompatActivity {
//    public static final String TAG = "RegisterActivity";
//
//    TextView haveAccount;
//    EditText inputRegisterEmail, inputRegisterpass, inputConfirmpass;
//    Button btn_register;
//    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//    ProgressDialog progressDialog;
//    FirebaseAuth firebaseAuth;
//    FirebaseUser firebaseUser;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        haveAccount = findViewById(R.id.haveAccount);
//        inputRegisterEmail = findViewById(R.id.inputRegisterEmail);
//        inputRegisterpass = findViewById(R.id.inputRegisterpass);
//        inputConfirmpass = findViewById(R.id.inputConfirmpass);
//        btn_register = findViewById(R.id.btn_register);
//        progressDialog = new ProgressDialog(this);
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseUser = firebaseAuth.getCurrentUser();
//
//        haveAccount.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
//        btn_register.setOnClickListener(view -> PerforAuth());
//    }
//
//    private void PerforAuth() {
//        String email = inputRegisterEmail.getText().toString();
//        String password = inputRegisterpass.getText().toString();
//        String confirmPass = inputConfirmpass.getText().toString();
//
//        if (!password.equals(confirmPass)) {
//            inputConfirmpass.setError("password not match");
//        } else if (!email.matches(emailPattern)) {
//            inputRegisterEmail.setError("Enter valid Email");
//        } else if (password.isEmpty() || password.length() < 8) {
//            inputRegisterpass.setError("Enter valid password");
//        } else {
//
//            progressDialog.setMessage("Please wait while Registration...");
//            progressDialog.setTitle("Registration");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
//
//            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    progressDialog.dismiss();
//                    sendUserToNextActivity();
//                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
//                } else {
//                    Log.d(TAG, "PerforAuth: " + task.getException().toString());
//                    progressDialog.dismiss();
//                    Toast.makeText(RegisterActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
//                }
//
//            });
//
//            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.d(TAG, "onFailure: " + e);
//                }
//            });
//
//
//        }
//
//
//    }
//
//    private void sendUserToNextActivity() {
//        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }
//
//}