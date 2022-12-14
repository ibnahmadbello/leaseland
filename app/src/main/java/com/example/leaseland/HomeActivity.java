package com.example.leaseland;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leaseland.utils.UserAgreement;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button biodataButton, guarantorButton, leaseLandButton;
    private FirebaseAuth mAuth;
    private SharedPreferences preferences;
    private TextView rrrTextView;
    private CardView rrrCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rrrTextView = findViewById(R.id.rrrTextView);
        rrrCardView = findViewById(R.id.rrrCardView);

        biodataButton = findViewById(R.id.biodata_button);
        biodataButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        guarantorButton = findViewById(R.id.guarantor_button);
        guarantorButton.setOnClickListener(this);

        leaseLandButton = findViewById(R.id.lease_land_button);
        leaseLandButton.setOnClickListener(this);

        preferences = getSharedPreferences(BioDataActivity.BIO_PREF, MODE_PRIVATE);

//        showUserName();
        setupRRR();

    }

    private void setupRRR() {
        Intent intent = getIntent();
        String rrr = intent.getStringExtra("rrr");
//        String data = getIntent().getExtras().getString("keyName");
        if (rrr != null){
            rrrTextView.setText("Generated RRR is: "+rrr);

        } else{
            rrrTextView.setText("No pending RRR generated.");
        }
        rrrCardView.setVisibility(View.VISIBLE);
        rrrTextView.setVisibility(View.VISIBLE);

//        String getRRRState = preferences.getString("rrrStatus", null);
//        if (getRRRState != null){
//            rrrTextView.setText("Generated RRR is: "+getRRRState);
//
//        } else{
//            rrrTextView.setText("No RRR generated.");
//        }
//        rrrCardView.setVisibility(View.VISIBLE);
//        rrrTextView.setVisibility(View.VISIBLE);
    }

    // user after successful registration
    private void showUserName() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null){
            Toast.makeText(this, "Sorry! Your profile is not fully set up.", Toast.LENGTH_SHORT).show();
        } else {
            checkIfEmailVerified(firebaseUser);
        }
    }

    private void checkIfEmailVerified(FirebaseUser firebaseUser) {
        if (!firebaseUser.isEmailVerified()){
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Email not verified");
        builder.setMessage("Please verify your email now. You can not login without email verification.");

        // Open email app if user clicks/taps continue button
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.biodata_button:
                //TODO Check if user has biodata first
                boolean getBioDataState = preferences.getBoolean("savedBioData", false);
                if (getBioDataState) {
                    new UserAgreement(this).showBioDataQuestion();
                } else {
                    startActivity(new Intent(this, BioDataActivity.class));
                }
                break;
            case R.id.guarantor_button:
                //TODO Check if user has guarantor first
                boolean getGuarantorState = preferences.getBoolean("savedGuarantorData", false);
                if (getGuarantorState) {
                    new UserAgreement(this).showGuarantorQuestion();
                } else{
                    Intent intent = new Intent(this, GuarantorActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.lease_land_button:
                startActivity(new Intent(this, LeaseLandActivity.class));
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.layout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_switch_logout:
                mAuth.signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}