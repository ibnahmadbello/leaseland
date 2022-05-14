package com.example.leaseland;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button resetButton;
    EditText resetEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        resetButton = findViewById(R.id.reset_password_button);
        resetEditText = findViewById(R.id.reset_password_edit_text);

        resetButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reset_password_button:
                sendResetLink();
                break;
        }
    }

    private void sendResetLink() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String email = resetEditText.getText().toString().trim();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ForgetPasswordActivity.this, "Password reset link has been sent to your mail", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                            }
                        }
                    });
        } else {
            resetEditText.setError("Enter a valid email!!");
            resetEditText.requestFocus();
        }
    }
}