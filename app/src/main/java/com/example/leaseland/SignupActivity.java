package com.example.leaseland;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText mEmail, mPass, confirmPasswordEditText;
    private TextView mTextView;
    private Button signUpButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mEmail = findViewById(R.id.email_edit_text);
        mPass = findViewById(R.id.password_edit_text);
        mTextView = findViewById(R.id.login_link);
        signUpButton = findViewById(R.id.register_button);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progress_bar);

        signUpButton.setOnClickListener(this);

        mTextView.setOnClickListener(this);
    }

    private void createUser(){
        String email = mEmail.getText().toString().trim();
        String password = mPass.getText().toString();
        String secondPassword = confirmPasswordEditText.getText().toString();

        if (!password.equals(secondPassword)){
            confirmPasswordEditText.setError("Password does not match...");
            confirmPasswordEditText.requestFocus();
        } else if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if (!password.isEmpty()){
                signUpButton.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SignupActivity.this, "Registered successfully !!", Toast.LENGTH_SHORT).show();
                                //FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                // TODO
                                //firebaseUser.sendEmailVerification();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        signUpButton.setVisibility(View.VISIBLE);
                        Toast.makeText(SignupActivity.this, "Registration Failed !!", Toast.LENGTH_SHORT).show();
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
            case R.id.login_link:
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                break;
            case R.id.register_button:
                createUser();
                break;
        }
    }
}