package com.example.leaseland.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import com.example.leaseland.GuarantorActivity;
import com.example.leaseland.R;

public class UserAgreement {

    private String AGREEMENT_PREFIX = "ua_";
    private Activity mActivity;

    public UserAgreement(Activity context) {
        mActivity = context;
    }

    private PackageInfo getPackageInfo(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException exception){
            exception.printStackTrace();
        }
        return packageInfo;
    }

    public void show(){
        PackageInfo versionInfo = getPackageInfo();

        // the useragreement key changes every time you increment the version number in the Android Manifest.xml
        final String userAgreementKey = AGREEMENT_PREFIX + versionInfo.versionCode;
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
        boolean hasBeenShownAgreement = sharedPreferences.getBoolean(userAgreementKey, false);
        if (hasBeenShownAgreement == false){

            // Show the User Agreement
            String title = mActivity.getString(R.string.agreement_title) + " v" + versionInfo.versionName;

            // Show details of the User Agreement
            String message = mActivity.getString(R.string.agreement) + "\n\n" + mActivity.getString(R.string.agreement_question);

            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Mark as read
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(userAgreementKey, true);
                            editor.commit();
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mActivity.finish();
                        }
                    });
            builder.create().show();
        }
    }
}
