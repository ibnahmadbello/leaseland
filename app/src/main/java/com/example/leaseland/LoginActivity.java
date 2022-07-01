package com.example.leaseland;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText mEmail, mPass;
    private TextView mTextView, forgetPasswordTextView;
    private Button signInButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.login_email_text);
        mPass = findViewById(R.id.login_password_text);
        signInButton = findViewById(R.id.login_button);
        mTextView = findViewById(R.id.register_link);
        forgetPasswordTextView = findViewById(R.id.forget_password);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progress_bar);

        forgetPasswordTextView.setOnClickListener(this);
        mTextView.setOnClickListener(this);
        signInButton.setOnClickListener(this);

    }

    private void loginUser(){
        String email = mEmail.getText().toString().trim();
        String password = mPass.getText().toString();


        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if (!password.isEmpty()){
                signInButton.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Login Successfully !!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        signInButton.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Login Failed !!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                mPass.setError("Empty Fields are not Allowed");
            }
        } else if (email.isEmpty()){
            mEmail.setError("Empty fields are not allowed");
        } else {
            mEmail.setError("Please enter a valid email address");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forget_password:
                forgetPassword();
                break;
            case R.id.register_link:
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                break;
            case R.id.login_button:
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if (firebaseUser != null) {
//                    checkIfEmailVerified(firebaseUser);
                }
                loginUser();
                break;
        }
    }

    private void forgetPassword() {
        startActivity(new Intent(this, ForgetPasswordActivity.class));
        //todo back press goes to register activity. shouldn't be
        finish();
    }

    private void checkIfEmailVerified(FirebaseUser firebaseUser) {
        if (!firebaseUser.isEmailVerified()){
            showAlertDialog();
        } else {
            loginUser();
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Email not verified");
        builder.setMessage("Please verify your email now. You can not login without email verification.");

        // Open email app if user clicks/taps continue button
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //TODO handle what happen after user goes to verify
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}