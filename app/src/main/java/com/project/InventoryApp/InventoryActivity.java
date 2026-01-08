///////////////////////////////////
//  Anthony Baratti
//  SNHU CS-360 Mobile Arch & Prog
//  Prof N. Shepard
//  February 23, 2025
//  InventoryActivity.java
//  This is the home scren that is just after the SignInActivity
//  It will display the inventory items a user adds to the database
//  in a grid (2 column) recycler view. It has an Add Item button
//  which will take the user to the AddItemActivity to populate the list
//  It also has an SMS Permissions button that will take the user to the
//  SmsPermissionActivity to grant or deny permissions.
///////////////////////////////////


package com.project.InventoryApp;

import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class InventoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DataBaseHelper dbHelper;
    List<Item> itemList;
    ItemAdapter itemAdapter;
    int userId;

    Button btnSmsPermission, btnAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // FIXME possible that not receiving id from intent
        // Try implementing from database
        //retrieve userId from SignInActivity
        userId = getIntent().getIntExtra("USER_ID", userId);

        setContentView(R.layout.activity_inventory);
        recyclerView = findViewById(R.id.recyclerView);

        //Sets recycler view to Grid Layout with 2 columns
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        btnSmsPermission = findViewById(R.id.btnSmsPermission);
        btnAddItem = findViewById(R.id.add_new_item);

        dbHelper = new DataBaseHelper(this);

        //Enable SMS button starts SMS permission activity
        btnSmsPermission.setOnClickListener(v -> {
            Intent intent = new Intent(InventoryActivity.this, SmsPermissionActivity.class);
            startActivity(intent);
        });


        //FIXME Clicking this button does not take user to AddItemActivity
        // It takes them back to the sign in screen
        // Attempted Solutions: Carry over userId via intent.Extra
        //

        //Start AddItemActivity screen with Add Item button
        btnAddItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventoryActivity.this, AddItemActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });

        //Populate list of items then create grid layout recycler view
        loadItems();
    }

    //populate inventory from unique user id
    private void loadItems() {
        itemList= new ArrayList<>();
        Cursor cursor = dbHelper.getItems(userId);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int quantity = cursor.getInt(2);
                String description = cursor.getString(3);
                itemList.add(new Item(id, name, quantity, description));
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        itemAdapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(itemAdapter);

    }
}