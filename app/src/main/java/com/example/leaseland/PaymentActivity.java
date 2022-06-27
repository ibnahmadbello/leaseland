package com.example.leaseland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.remita.paymentsdk.core.RemitaInlinePaymentSDK;
import com.remita.paymentsdk.data.PaymentResponse;
import com.remita.paymentsdk.listener.RemitaGatewayPaymentResponseListener;
import com.remita.paymentsdk.module.MainActivity;
import com.remita.paymentsdk.util.JsonUtil;
import com.remita.paymentsdk.util.RIPGateway;

import java.util.Date;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener, RemitaGatewayPaymentResponseListener {

    private TextView generateRRR, webviewRRR;
    RemitaInlinePaymentSDK remitaInlinePaymentSDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        generateRRR = findViewById(R.id.generateRRR);
        webviewRRR = findViewById(R.id.webviewRRR);

        generateRRR.setOnClickListener(this);
        webviewRRR.setOnClickListener(this);

        remitaInlinePaymentSDK = RemitaInlinePaymentSDK.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.generateRRR:
                //TODO
                break;
            case R.id.webviewRRR:
                //TODO
                String url = RIPGateway.Endpoint.DEMO;
                String api_key = "QzAwMDAxNjMwNzl8NDA4NDEyMjQ0MHw0ODZkYTZkOTE4NTVhNzMzZmIzZTM5MTU2ZDBjZDYxY2Y4MzY4ODQ1NzRkYzIyOTI2OWQzMTU1M2NlNzdkNGZkZGIyNjI1MzA1ZjZkNzkzYzM2NTE4NzUxNTI0OWVjYjAxODUyNGZmYTM3NjY3M2IwZWNjYTU3OWEwYjE5NGMyNQ==";
                String rrr = "280008392172";


                remitaInlinePaymentSDK.setRemitaGatewayPaymentResponseListener(PaymentActivity.this);
                remitaInlinePaymentSDK.initiatePayment(PaymentActivity.this, url, api_key, rrr);
                break;
        }
    }

    @Override
    public void onPaymentCompleted(PaymentResponse paymentResponse) {
        Log.v("+++ Response: ", JsonUtil.toJson(paymentResponse));
        Toast.makeText(this, JsonUtil.toJson(paymentResponse), Toast.LENGTH_SHORT).show();
        if (paymentResponse.getResponseMessage()=="SUCCESS")
            startActivity(new Intent(this, HomeActivity.class));
    }
}