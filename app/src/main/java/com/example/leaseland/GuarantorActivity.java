package com.example.leaseland;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.leaseland.utils.UserAgreement;

public class GuarantorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarantor);

        new UserAgreement(this).show();
    }
}