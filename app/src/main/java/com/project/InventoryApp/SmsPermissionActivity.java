///////////////////////////////////
//  Anthony Baratti
//  SNHU CS-360 Mobile Arch & Prog
//  Prof N. Shepard
//  February 23, 2025
//  SMSPermissionActivity.java
//
//  This activity is accessed via SMS Permissions button from
//  the home screen (InventoryActivity). It will allow the user to
//  grant permission to use SMS text alert or remove permissions.
///////////////////////////////////


package com.project.InventoryApp;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SmsPermissionActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 1;
    private TextView txtPermissionStatus;
    private Button btnRequestPermission, btnRevokePermission, btnBackSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_permission);

        txtPermissionStatus = findViewById(R.id.txtPermissionStatus);
        btnRequestPermission = findViewById(R.id.btnRequestPermission);
        btnRevokePermission = findViewById(R.id.btnRevokePermission);
        btnBackSMS = findViewById(R.id.btnSMSBack);
        checkPermissionStatus();

        btnRevokePermission.setOnClickListener(v -> revokeSmsPermission());


        btnRequestPermission.setOnClickListener(v -> requestSmsPermission());

        btnBackSMS.setOnClickListener(v -> {
            //start Registration screen
            Intent intent = new Intent(SmsPermissionActivity.this, InventoryActivity.class);
            startActivity(intent);
        });
    }

    private void checkPermissionStatus() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            txtPermissionStatus.setText("SMS Permission: GRANTED");
            btnRequestPermission.setEnabled(false);
        } else {
            txtPermissionStatus.setText("SMS Permission: NOT GRANTED");
            btnRequestPermission.setEnabled(true);
        }
    }

    private void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
        } else {
            Toast.makeText(this, "Permission already granted!", Toast.LENGTH_SHORT).show();
        }
    }

    private void revokeSmsPermission() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
        Toast.makeText(this, "Go to Permissions and Disable SMS", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                txtPermissionStatus.setText("SMS Permission: GRANTED");
                btnRequestPermission.setEnabled(false);
                Toast.makeText(this, "SMS Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                txtPermissionStatus.setText("SMS Permission: DENIED");
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}