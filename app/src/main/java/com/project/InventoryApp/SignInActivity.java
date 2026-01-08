///////////////////////////////////
//  Anthony Baratti
//  SNHU CS-360 Mobile Arch & Prog
//  Prof N. Shepard
//  February 23, 2025
//  SignInActivity.java
//
//  This Activity is the launch screen of the app. There are 2
//  fields: Email and password. If the user has already registered,
//  once the fields are populated they can click the log in button.
//  The class will check the user credentials to the DB and if they match
//  the user will be brought to InventoryActivity class.
//
//  If the user is new, they can click the Register New User button which
//  will take them to the RegisterActivity screen which will allow them to
//  create new user credentials to log in.
///////////////////////////////////


package com.project.InventoryApp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    EditText userEmailEditText, passwordEditText;
    Button loginButton, createAccountButton;
    DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        dbHelper = new DataBaseHelper(this);

        userEmailEditText = findViewById(R.id.user_email);
        passwordEditText = findViewById(R.id.user_password);
        loginButton = findViewById(R.id.signin_button);
        createAccountButton = findViewById(R.id.register_button);

        loginButton.setOnClickListener(view -> {
            String email = userEmailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            //Validate credentials
            if (dbHelper.checkUser(email, password)) {
                int userId = dbHelper.getUserId(email);
                //Start InventoryActivity screen with valid user ID
                Intent intent = new Intent(SignInActivity.this, InventoryActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            } else {
                //credentials not found in DB
                Toast.makeText(SignInActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
            }
        });
        createAccountButton.setOnClickListener(v -> {
            //start Registration screen
            Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

    }
}
