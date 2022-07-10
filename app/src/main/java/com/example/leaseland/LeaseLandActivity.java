package com.example.leaseland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.leaseland.utils.UserAgreement;

public class LeaseLandActivity extends AppCompatActivity implements View.OnClickListener {

    private Button paymentButton;
    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lease_land);

        new UserAgreement(this).show();

        radioGroup = findViewById(R.id.radio_group);
        paymentButton = findViewById(R.id.proceed_to_payment_button);
        paymentButton.setOnClickListener(this);

//        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        // get selected radio button from radioGroup
//        int selectedId = radiogroup .getCheckedRadioButtonId();

        // find the radio button by returned id
//        RadioButton radioButton = (RadioButton) findViewById(selectedId);
//        radioGroup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        if (view.getId() == R.id.radio_group){
//            checkIfChecked();
//        } else
            if (view.getId() == R.id.proceed_to_payment_button){
            startActivity(new Intent(this, PaymentActivity.class));
        }

    }

    private void checkIfChecked() {
        if (radioGroup.getCheckedRadioButtonId() == -1){
            paymentButton.setClickable(false);
        } else {
            paymentButton.setClickable(true);
        }
    }


//    public void onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        if (checked){
//            paymentButton.setClickable(true);
//        } else
//        {
//            paymentButton.setClickable(false);
//        }
//        // Check which radio button was clicked
////        switch(view.getId()) {
////            case R.id.categoryA:
////                if (checked)
////                    // Pirates are the best
////                    break;
////            case R.id.categoryB:
////                if (checked)
////                    // Ninjas rule
////                    break;
////            case R.id.categoryC:
////                if (checked)
////                    // Ninjas rule
////                    break;
////        }
//    }


}