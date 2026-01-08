///////////////////////////////////
//  Anthony Baratti
//  SNHU CS-360 Mobile Arch & Prog
//  Prof N. Shepard
//  February 23, 2025
//  EditItemActivity.java
//
//  This Activity is designed to allow a user to update
//  an existing item in the database.
//  It has all fields an inventory item contains in the database that
//  can be updated: Item name, quantity, & description
//  and has a button that will update those fields of the items id.
//
//  As of the current moment there is no button to access this activity.
//  TODO Create a way from the InventoryActivity to access
//  EditItemActivity screen.
///////////////////////////////////


package com.project.InventoryApp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditItemActivity extends AppCompatActivity {

    EditText editItemName, editItemQuantity, editItemDescription;
    Button btnUpdateItem, btnBackEditItem;
    DataBaseHelper dbHelper;
    int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper= new DataBaseHelper(this);

        editItemName = findViewById(R.id.editItemName);
        editItemQuantity = findViewById(R.id.editItemQuantity);
        editItemDescription = findViewById(R.id.editItemDescription);
        btnUpdateItem = findViewById(R.id.btnUpdateItem);
        btnBackEditItem = findViewById(R.id.btnBackEditItem);

        //Retrieve Item from intent by userId
        Intent intent = getIntent();
        itemId = intent.getIntExtra("ITEM_ID", -1);
        editItemName.setText(intent.getStringExtra("ITEM_NAME"));
        editItemQuantity.setText(String.valueOf(intent.getIntExtra("ITEM_QUANTITY", 0)));
        editItemDescription.setText(intent.getStringExtra("ITEM_DESCRIPTION"));

        //Back button leads to InventoryActivity
        btnBackEditItem.setOnClickListener(v -> {
            Intent intent1 = new Intent(EditItemActivity.this, InventoryActivity.class);
            startActivity(intent1);
        });

        //update on click
        btnUpdateItem.setOnClickListener(v -> {
            String name = editItemName.getText().toString().trim();
            int quantity = Integer.parseInt(editItemQuantity.getText().toString().trim());
            String description = editItemDescription.getText().toString().trim();

            //updates item in database and notifies user
            if(dbHelper.updateItem(itemId, name, quantity, description)) {
                Toast.makeText(EditItemActivity.this, "Item Updated.", Toast.LENGTH_SHORT).show();
                finish();
            }
            //notifies user if unsuccessful
            else {
                Toast.makeText(EditItemActivity.this, "Item Not Updated.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}