package com.example.leaseland;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.leaseland.utils.UserAgreement;

public class LeaseLandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lease_land);

        new UserAgreement(this).show();
    }
}