///////////////////////////////////
//  Anthony Baratti
//  SNHU CS-360 Mobile Arch & Prog
//  Prof N. Shepard
//  February 23, 2025
//  RegisterActivity.java
//
//  This Activity is designed to allow a new user to register an account
//  From the launch screen (SignInActivity), clicking Register New User button
//  will start this activity.  There are four fields to populate:
//  username, email, password, phonenumber.
//  Once these fields are populated, Clicking the register user button
//  will add the credentials to the database, then return the user to the
//  SignInActivity where they can use those credentials to log in.
///////////////////////////////////


package com.project.InventoryApp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextName, editTextEmail, editTextPassword, editTextPhone;
    Button btnRegister, btnBack;
    DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.edit_new_name);
        editTextEmail = findViewById(R.id.edit_new_email);
        editTextPassword = findViewById(R.id.edit_new_password);
        editTextPhone = findViewById(R.id.edit_new_phone);
        btnRegister = findViewById(R.id.register_new_btn);
        btnBack = findViewById(R.id.register_back_btn);

        dbHelper = new DataBaseHelper(this);

        //Back button goes to log in screen
        btnBack.setOnClickListener(v -> {
            //start Registration screen
            Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
            startActivity(intent);
        });


        // Register User
        btnRegister.setOnClickListener(v ->
                registerNewUser());


    }

    private void registerNewUser() {
        //collect input fields
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        //Ensure fields are not empty
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        //Ensure no duplicate email
        if (dbHelper.isEmailExists(email)) {
            Toast.makeText(RegisterActivity.this, "Email already registered.", Toast.LENGTH_SHORT).show();
            return;
        }

        //Add new user
        if (dbHelper.addUser(name, email, password, phone)) {
            Toast.makeText(RegisterActivity.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
            //Return user to sign in screen to log in
            Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(RegisterActivity.this, "Registration Failed.", Toast.LENGTH_SHORT).show();
        }
    }
}