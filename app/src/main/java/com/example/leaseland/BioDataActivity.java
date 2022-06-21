package com.example.leaseland;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BioDataActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioButton yesRadioButton, noRadioButton;
    private RadioGroup staffOrNotRadioGroup;
    private Button saveButton;
    private CheckBox maizeCheckBox, yamCheckBox, milletCheckBox, beanCheckBox, riceCheckBox, groundnutCheckBox;
    private TextView departmentTextView, placeOfWorkTextView, residentialAddressTextView, staffIdTextView;
    private EditText departmentEditText, staffIdEditText, residentialAddressEditText, placeOfWorkEditText, fullNameEditText, phoneNumberEditText;

    private String fullName, staffDepartment, staffID, phoneNumber, placeOfWork, residentialAddress;
    private ArrayList<String> listOfCrop;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_data);

        staffOrNotRadioGroup = findViewById(R.id.staffOrNot);
        yesRadioButton = findViewById(R.id.yes_staff_radio_button);
        noRadioButton = findViewById(R.id.non_staff_radio_button);

        departmentTextView = findViewById(R.id.department);
        placeOfWorkTextView = findViewById(R.id.placeOfWork);
        residentialAddressTextView = findViewById(R.id.residential_address);
        staffIdTextView = findViewById(R.id.staffId);

        fullNameEditText = findViewById(R.id.applicant_full_name);
        phoneNumberEditText = findViewById(R.id.applicant_phone_number);
        departmentEditText = findViewById(R.id.enter_department);
        staffIdEditText = findViewById(R.id.enter_staff_id);
        residentialAddressEditText = findViewById(R.id.enter_residential_address);
        placeOfWorkEditText = findViewById(R.id.enter_place_of_work);

        saveButton = findViewById(R.id.save_bio_data);

        listOfCrop = new ArrayList<>();

        yesRadioButton.setOnClickListener(this);
        noRadioButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        int selectedID = staffOrNotRadioGroup.getCheckedRadioButtonId();
        switch (view.getId()){
            case R.id.save_bio_data:
                saveUserInfo();
                break;
//            case selectedID:
//                break;
        }



        if (selectedID == R.id.yes_staff_radio_button){
            departmentTextView.setVisibility(View.VISIBLE);
            departmentEditText.setVisibility(View.VISIBLE);
            staffIdEditText.setVisibility(View.VISIBLE);
            staffIdTextView.setVisibility(View.VISIBLE);
            placeOfWorkTextView.setVisibility(View.GONE);
            residentialAddressTextView.setVisibility(View.GONE);
            residentialAddressEditText.setVisibility(View.GONE);
            placeOfWorkEditText.setVisibility(View.GONE);
        } else if (selectedID == R.id.non_staff_radio_button){
            placeOfWorkTextView.setVisibility(View.VISIBLE);
            residentialAddressTextView.setVisibility(View.VISIBLE);
            residentialAddressEditText.setVisibility(View.VISIBLE);
            placeOfWorkEditText.setVisibility(View.VISIBLE);
            departmentTextView.setVisibility(View.GONE);
            departmentEditText.setVisibility(View.GONE);
            staffIdEditText.setVisibility(View.GONE);
            staffIdTextView.setVisibility(View.GONE);
        }

//        boolean checked = ((RadioButton) view).isChecked();
//
//        switch (selectedID){
//            case R.id.yes_staff_radio_button:
//                if (checked){
//                    }
//                break;
//            case R.id.non_staff_radio_button:
//                if (checked){
//                    }
//                break;
//
//
//        }

    }

    private void saveUserInfo() {
        FirebaseUser firebaseUser = auth.getCurrentUser();

        fullName = fullNameEditText.getText().toString().trim();
        phoneNumber = phoneNumberEditText.getText().toString().trim();
        // User info
        User writeUserDetaills = new User(fullName, phoneNumber);

        DatabaseReference referenceUser = FirebaseDatabase.getInstance().getReference("registeredUser");
        referenceUser.child(firebaseUser.getUid()).setValue(writeUserDetaills).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    // TODO: Send user verification
                    Toast.makeText(BioDataActivity.this, "User profile created successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BioDataActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(BioDataActivity.this, "User profile registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}