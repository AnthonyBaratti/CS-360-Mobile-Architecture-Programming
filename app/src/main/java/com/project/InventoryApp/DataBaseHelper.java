///////////////////////////////////
//  Anthony Baratti
//  SNHU CS-360 Mobile Arch & Prog
//  Prof N. Shepard
//  February 23, 2025
//  DataBaseHelper.java
//
//  This class is designed to create the two tables to be stored in
//  the devices database. The first table is a tables of users
//  consisting of: id, name, email, password, phone number.
//  The second table is for the items, which use the primary key
//  of the user (creates a sub table using a foreign key)
//  The items are added to the database with the fields:
//  userId, itemId, name, quantity, description.
//
//  The methods for checking and getting users and items are built
//  into this class.
///////////////////////////////////


package com.project.InventoryApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "InventoryDB.db";
    private static final int DATABASE_VERSION = 1;

    //Tables for user info
    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_USER_NAME = "name";
    private static final String COL_USER_EMAIL = "email";
    private static final String COL_USER_PASSWORD = "password";
    private static final String COL_USER_PHONE = "phone";

    //Tables to store items
    private static final String TABLE_ITEMS = "items";
    private static final String COL_ITEM_ID = "id";
    //Foreign key that hooks inventory to user ID
    private static final String COL_ITEM_USER_ID = "user_id";
    private static final String COL_ITEM_NAME = "item_name";
    private static final String COL_ITEM_QUANTITY = "quantity";
    private static final String COL_ITEM_DESCRIPTION = "description";

    //Create Users Table
    private static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COL_USER_ID + " TEXT PRIMARY KEY AUTOINCREMENT, " +
                    COL_USER_NAME + " TEXT, " +
                    COL_USER_EMAIL + " TEXT UNIQUE, " +
                    COL_USER_PASSWORD + " TEXT, " +
                    COL_USER_PHONE + " TEXT);";

    //Creates Item Table
    // Attributes: itemId, userId (Foreign Key), itemName, itemQuantity, itemDescription
    //Puts the item into user table
    private static final String CREATE_ITEMS_TABLE =
            "CREATE TABLE " + TABLE_ITEMS + " (" +
                    COL_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_ITEM_USER_ID + " INTEGER, " +
                    COL_ITEM_NAME + " TEXT, " +
                    COL_ITEM_QUANTITY + " INTEGER, " +
                    COL_ITEM_DESCRIPTION + " TEXT, " + "FOREIGN KEY(" +
                    COL_ITEM_USER_ID + ") REFERENCES " +
                    TABLE_USERS + "(" + COL_USER_ID + "));";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    //Allows registration of new user into DB
    public boolean addUser(String name, String email, String password,
    String phone) {
        //call DB writer
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, name);
        values.put(COL_USER_EMAIL, email);
        values.put(COL_USER_PASSWORD, password);
        values.put(COL_USER_PHONE, phone);

        long result = db.insert(TABLE_USERS, null, values);

        return result != -1;
    }

    //User Validation
    public boolean checkUser(String email, String password) {
        //Get DB reader
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS +
                        " WHERE " + COL_USER_EMAIL + "=? AND " + COL_USER_PASSWORD + "=?",
                new String[]{email, password});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    //Get user ID by Email for inventory
    public int getUserId(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_USER_ID +
                        " FROM " + TABLE_USERS + " WHERE " + COL_USER_EMAIL + "=?",
                new String[]{email});

        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(0);
            cursor.close();
            return userId;
        } else {
            cursor.close();
            return -1;
        }
    }

    //Adds item to database
    public boolean addItem(int userId, String name, int quantity,
                           String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //Foreign key
        values.put(COL_ITEM_USER_ID, userId);
        values.put(COL_ITEM_NAME, name);
        values.put(COL_ITEM_QUANTITY, quantity);
        values.put(COL_ITEM_DESCRIPTION, description);

        long result = db.insert(TABLE_ITEMS, null, values);
        return result != -1;
    }

    //Get items for user ID
    public Cursor getItems(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT *FROM " + TABLE_ITEMS +
                " WHERE " + COL_ITEM_USER_ID + "=?",
            new String[]{String.valueOf(userId)});
    }

    //delete item in database
    public boolean deleteItem(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete("items", "id = ?",
                new String[]{String.valueOf(itemId)});
        return deletedRows > 0;
    }

    //Update item in database
    public boolean updateItem(int itemId, String name, int quantity, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("quantity", quantity);
        values.put("description", description);

        int updatedRows = db.update("items", values, "id = ?",
                new String[]{String.valueOf(itemId)});
        return updatedRows > 0;
    }

    //update item with "+" or "-" button on recycler view
    public boolean updateItemQuantity(int itemId, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", newQuantity);

        int updatedRows = db.update("items", values, "id = ?",
                new String[]{String.valueOf(itemId)});
        return updatedRows > 0;
    }

    //Used to get phone number to send texts
    public String getUserPhoneNumber(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT phone FROM users WHERE id = ?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        cursor.close();
        return null;
    }

    //Ensures no duplicate emails during registration of new user
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COL_USER_ID},
                COL_USER_EMAIL + "=?", new String[]{email}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
