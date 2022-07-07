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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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


            String postUrl = "";
            String postBody = "{\n" + "\"amount\": \"1000\",\n" +
                    "\"payerName\": \"Arab\",\n" +
                    "\"payerEmail\": \"arabregent@gmail.com\",\n" +
                    "\"payerPhone\": \"08147847855\",\n" +
                    "\"description\": \"Payment for land leasing\"\n" + "}";


            try {
                postRequest(postUrl, postBody);
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

    private void postRequest(String postUrl, String postBody) throws IOException {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(postBody, JSON);
        Request request = new Request.Builder().url(postUrl).post(requestBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Toast.makeText(PaymentActivity.this, "+++++"+response.body(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (int i =0; i < bytes.length; i++){
            String hex = Integer.toHexString(0xff & bytes[i]);
            if (hex.length() == 1){
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    void run(String returnedHash, long orderId, String merchantId) throws IOException{
        OkHttpClient client = new OkHttpClient();

        JSONObject bodyPost = new JSONObject();
        try {
            bodyPost.put("serviceTypeId", "4430731");
            bodyPost.put("amount", "10000");
            bodyPost.put("orderId", orderId);
            bodyPost.put("payerName", "Arab");
            bodyPost.put("payerEmail", "justforlearningcode@gmail.com");
            bodyPost.put("payerPhone", "08123447855");
            bodyPost.put("description", "Rent on Land");
        } catch (JSONException e){
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(bodyPost.toString(), JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Content-Type", "application/json")
                .header("Authorization", "remitaConsumerKey="+merchantId+",remitaConsumerToken="+returnedHash+"")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();


                PaymentActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        generateRRR.setText(myResponse);
                        Toast.makeText(PaymentActivity.this, "---"+myResponse, Toast.LENGTH_SHORT).show();
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

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

}