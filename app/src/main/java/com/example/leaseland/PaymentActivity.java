package com.example.leaseland;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.remita.paymentsdk.core.RemitaInlinePaymentSDK;
import com.remita.paymentsdk.data.PaymentResponse;
import com.remita.paymentsdk.listener.RemitaGatewayPaymentResponseListener;
import com.remita.paymentsdk.util.JsonUtil;
import com.remita.paymentsdk.util.RIPGateway;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener, RemitaGatewayPaymentResponseListener {

    private TextView generateRRR, webviewRRR;
    RemitaInlinePaymentSDK remitaInlinePaymentSDK;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    String url = "https://remitademo.net/remita/exapp/api/v1/send/api/echannelsvc/merchant/api/paymentinit";

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
            String postUrl = "https://aqueous-waters-90678.herokuapp.com/";

                JSONObject bodyPost = new JSONObject();
                try {
                    bodyPost.put("amount", "10000");
                    bodyPost.put("payerName", "Arab");
                    bodyPost.put("payerEmail", "justforlearningcode@gmail.com");
                    bodyPost.put("payerPhone", "08123447855");
                    bodyPost.put("description", "Rent on Land");
                } catch (JSONException e){
                    e.printStackTrace();
                }

            try {
                postRequest(postUrl, bodyPost);
            } catch (IOException e){
                e.printStackTrace();
            }
                break;
            case R.id.webviewRRR:
                //TODO
                String url = RIPGateway.Endpoint.DEMO;
                String api_key = "QzAwMDAxNjMwNzl8NDA4NDEyMjQ0MHw0ODZkYTZkOTE4NTVhNzMzZmIzZTM5MTU2ZDBjZDYxY2Y4MzY4ODQ1NzRkYzIyOTI2OWQzMTU1M2NlNzdkNGZkZGIyNjI1MzA1ZjZkNzkzYzM2NTE4NzUxNTI0OWVjYjAxODUyNGZmYTM3NjY3M2IwZWNjYTU3OWEwYjE5NGMyNQ==";
                String rrr = "310008394238";


                remitaInlinePaymentSDK.setRemitaGatewayPaymentResponseListener(PaymentActivity.this);
                remitaInlinePaymentSDK.initiatePayment(PaymentActivity.this, url, api_key, rrr);
                break;
        }
    }

    private void postRequest(String postUrl, JSONObject postBody) throws IOException {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(postBody.toString(), JSON);
        Request request = new Request.Builder()
                .url(postUrl)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                final String myResponse = response.body().string();
                String[] responseArray = myResponse.split(",");
                String rrr = responseArray[1];
                String userRRR = rrr.substring(6);

                PaymentActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PaymentActivity.this, "---"+myResponse+"\n\n"+userRRR, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public void onPaymentCompleted(PaymentResponse paymentResponse) {
        Log.v("+++ Response: ", JsonUtil.toJson(paymentResponse));
        Toast.makeText(this, JsonUtil.toJson(paymentResponse), Toast.LENGTH_SHORT).show();
        if (paymentResponse.getResponseMessage()=="SUCCESS")
            startActivity(new Intent(this, HomeActivity.class));
    }

}