package com.example.leaseland;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;


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

        if ()
    }
}