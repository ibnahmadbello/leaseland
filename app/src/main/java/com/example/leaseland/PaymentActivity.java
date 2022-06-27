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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
                String merchantId = "2547916";
                String apiKey = "1946";
                String serviceTypeId = "4430731";
                Date d = new Date();
                long orderId = d.getTime();
                String totalAmount = "1000";
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
                    String input = merchantId+ serviceTypeId+ orderId+totalAmount+apiKey;
                    byte[] md = messageDigest.digest(input.getBytes());
                    BigInteger no = new BigInteger(1, md);
                    String hashInput = no.toString(16);
                    while (hashInput.length() < 32){
                        hashInput = "0" + hashInput;
                    }
                } catch (NoSuchAlgorithmException e) {
                    Log.d("Hash Error", e.getMessage());
                    Toast.makeText(this, "Hash error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
//                var apiHash = CryptoJS.SHA512(merchantId+ serviceTypeId+ orderId+totalAmount+apiKey);
//            {
//                "serviceTypeId": "{{serviceTypeId}}",
//                    "amount": "{{totalAmount}}",
//                    "orderId": "{{orderId}}",
//                    "payerName": "Michelle Alozie",
//                    "payerEmail": "alozie@systemspecs.com.ng",
//                    "payerPhone": "09062067384",
//                    "description": "Payment for Donation 3",
//                    "customFields":[{
//                "name":"Matric Number",
//                        "value":"1509329285795",
//                        "type":"ALL"
//            },
//                {
//                    "name":"Invoice Number",
//                        "value":"1234",
//                        "type":"ALL"
//                }]
//            }
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