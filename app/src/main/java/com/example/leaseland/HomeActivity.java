package com.example.leaseland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private Button signOutButton, biodataButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        biodataButton = findViewById(R.id.biodata_button);
        biodataButton.setOnClickListener(this);

        signOutButton = findViewById(R.id.sign_out_button);
        mAuth = FirebaseAuth.getInstance();

        signOutButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.biodata_button:
                startActivity(new Intent(this, BioDataActivity.class));
                break;
            case R.id.sign_out_button:
                mAuth.signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                break;
        }
    }
}