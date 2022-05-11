package com.example.leaseland;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail, mPass;
    private TextView mTextView;
    private Button signInButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.login_email_text);
        mPass = findViewById(R.id.login_password_text);
        signInButton = findViewById(R.id.login_button);
        mTextView = findViewById(R.id.register_link);

        mAuth = FirebaseAuth.getInstance();

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

    }

    private void loginUser(){
        String email = mEmail.getText().toString();
        String password = mPass.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if (!password.isEmpty()){
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(LoginActivity.this, "Login Successfully !!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
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
}