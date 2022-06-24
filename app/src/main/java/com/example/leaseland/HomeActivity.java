package com.example.leaseland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signOutButton, biodataButton, guarantorButton, leaseLandButton;
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

        guarantorButton = findViewById(R.id.guarantor_button);
        guarantorButton.setOnClickListener(this);

        leaseLandButton = findViewById(R.id.lease_land_button);
        leaseLandButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.biodata_button:
                //TODO Check if user has biodata first
                SharedPreferences preferences = getSharedPreferences(BioDataActivity.BIO_PREF, MODE_PRIVATE);
                boolean getSavedState = preferences.getBoolean("savedBioData", false);
                if (getSavedState) {

                } else {
                    startActivity(new Intent(this, BioDataActivity.class));
                }
                break;
            case R.id.sign_out_button:
                mAuth.signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                break;
            case R.id.guarantor_button:
                //TODO Check if user has guarantor first
                Intent intent = new Intent(this, GuarantorActivity.class);
                startActivity(intent);
                break;
            case R.id.lease_land_button:
                startActivity(new Intent(this, LeaseLandActivity.class));
                break;

        }
    }
}