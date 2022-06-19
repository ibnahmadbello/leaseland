package com.example.leaseland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class GuarantorActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText guarantorName, guarantorPhone, guarantorEmail, guarantorAddress;
    private ProgressBar progressBar;
    private Button saveButton;

    private String getGuarantorName, getGuarantorPhone, getGuarantorEmail, getGuarantorAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarantor);

        guarantorName = findViewById(R.id.guarantor_full_name_et);
        guarantorPhone = findViewById(R.id.guarantor_phone_number_et);
        guarantorEmail = findViewById(R.id.guarantor_email_et);
        guarantorAddress = findViewById(R.id.guarantor_home_address_et);

        progressBar = findViewById(R.id.progress_bar);
        saveButton = findViewById(R.id.save_guarantor_info);

        saveButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.save_guarantor_info) {
            saveGuarantorInfo();
        }
    }

    private void saveGuarantorInfo() {
        progressBar.setVisibility(View.VISIBLE);
        getGuarantorName = guarantorName.getText().toString().trim();
        getGuarantorAddress = guarantorAddress.getText().toString().trim();
        getGuarantorEmail = guarantorEmail.getText().toString().trim();
        getGuarantorPhone = guarantorPhone.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(getGuarantorEmail).matches()){
            guarantorEmail.setError("Enter a valid email address.");
            guarantorEmail.requestFocus();
        } else if (TextUtils.isEmpty(getGuarantorEmail)){
            guarantorEmail.setError("Enter Guarantor email address.");
            guarantorEmail.requestFocus();
        } else if (TextUtils.isEmpty(getGuarantorName)){
            guarantorName.setError("Enter Guarantor full name.");
            guarantorName.requestFocus();
        } else if (TextUtils.isEmpty(getGuarantorAddress)){
            guarantorAddress.setError("Enter Guarantor address.");
            guarantorAddress.requestFocus();
        } else if (TextUtils.isEmpty(getGuarantorPhone)){
            guarantorPhone.setError("Enter a Guarantor phone number.");
            guarantorPhone.requestFocus();
        } else if (!TextUtils.isDigitsOnly(getGuarantorPhone)){
            guarantorPhone.setError("Enter a valid phone number.");
            guarantorPhone.requestFocus();
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Guarantor Info saved successfully...", Toast.LENGTH_SHORT).show();
            //TODO Save Guarantor to Firebase
            Intent intent = new Intent(GuarantorActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}