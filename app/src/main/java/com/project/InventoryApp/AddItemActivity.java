///////////////////////////////////
//  Anthony Baratti
//  SNHU CS-360 Mobile Arch & Prog
//  Prof N. Shepard
//  February 23, 2025
//  AddItemActivity.java
//
//  This activity is accessed from InventoryActivity
//  via Add Item click. It contains the fields necessary
//  to create an inventory item: name, quantity, description.
//
//  Once the user has populated all 3 fields and clicks Add Item
//  The Activity will add that item to the sub database under the userId
//  It also contains a back button in case the user needs to navigate away
//  without adding an item
//
//  As of this moment, there is no field validation
//  TODO: Create field validation under //FIXME tag.
//
//  FIXME: When Add Item button in InventoryActivity is clicked,
//  the program sends the user back to SignInActivity rather than to
//  AddItemActivity.  This effectively makes it so no items can be added
//  Until this is fixed, the app can not be tested for full functionality.
///////////////////////////////////


package com.project.InventoryApp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    EditText editItemName, editItemQuantity, editItemDescription;
    Button btnAddItem, btnBackAddItem;
    DataBaseHelper dbHelper;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        dbHelper = new DataBaseHelper(this);

        //Retrieves userId from InventoryActivity
        userId = getIntent().getIntExtra("USER_ID", userId);

        editItemName = findViewById(R.id.editItemName);
        editItemQuantity = findViewById(R.id.editItemQuantity);
        editItemDescription = findViewById(R.id.editItemDescription);
        btnBackAddItem = findViewById(R.id.btnBackAddItem);

        //Go back to InventoryActivity
        btnBackAddItem.setOnClickListener(v -> {
            //start InventoryActivity screen
            Intent intent = new Intent(AddItemActivity.this, InventoryActivity.class);
            startActivity(intent);
        });

        //FIXME Create field validation
        //Adds item after fields have been filled
        btnAddItem.setOnClickListener(v -> {
            //Collects fields
            String name = editItemName.getText().toString().trim();
            int quantity = Integer.parseInt(editItemQuantity.getText().toString().trim());
            String description = editItemDescription.getText().toString().trim();

            //Creates item in database and populates attributes with fields
            if (dbHelper.addItem(userId, name, quantity, description)) {
                Toast.makeText(AddItemActivity.this, "Item Added", Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                Toast.makeText(AddItemActivity.this, "Failed to add Item", Toast.LENGTH_SHORT).show();
            }
        });
    }
}