package com.example.leaseland;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView generateRRR, webviewRRR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        generateRRR = findViewById(R.id.generateRRR);
        webviewRRR = findViewById(R.id.webviewRRR);

        generateRRR.setOnClickListener(this);
        webviewRRR.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
    }
}