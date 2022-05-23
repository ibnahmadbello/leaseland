package com.example.leaseland;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class BioDataActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioButton yesRadioButton, noRadioButton;
    private RadioGroup staffOrNotRadioGroup;
    private TextView departmentTextView, placeOfWorkTextView, residentialAddressTextView, staffIdTextView;
    private EditText departmentEditText, staffIdEditText, residentialAddressEditText, placeOfWorkEditText;

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

        departmentEditText = findViewById(R.id.enter_department);
        staffIdEditText = findViewById(R.id.enter_staff_id);
        residentialAddressEditText = findViewById(R.id.enter_residential_address);
        placeOfWorkEditText = findViewById(R.id.enter_place_of_work);

        yesRadioButton.setOnClickListener(this);
        noRadioButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int selectedID = staffOrNotRadioGroup.getCheckedRadioButtonId();
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
}