package com.example.leaseland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.leaseland.utils.UserAgreement;

public class LeaseLandActivity extends AppCompatActivity implements View.OnClickListener {

    private Button paymentButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lease_land);

        new UserAgreement(this).show();

        paymentButton = findViewById(R.id.proceed_to_payment_button);
        paymentButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.proceed_to_payment_button){
            startActivity(new Intent(this, PaymentActivity.class));
        }
    }
}