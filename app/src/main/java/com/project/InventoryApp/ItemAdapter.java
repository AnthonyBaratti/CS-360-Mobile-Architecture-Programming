///////////////////////////////////
//  Anthony Baratti
//  SNHU CS-360 Mobile Arch & Prog
//  Prof N. Shepard
//  February 23, 2025
//  ItemAdapter.java
//
//  This class is designed as the viewholder of the items list
//  that will populate the recycler view Grid layout on the InventoryActivity screen.
//  It builds an Items array, using the database, then fills in each
//  grid with the information of each item.  Each grid (card) will
//  have 2 buttons, an increase (+) and decrease (-) that will allow
//  a user to quickly adjust the quantity of the item from this screen
//
//  Also holds a method for sending a low inventory message via SMS
//  with a permissions check and Toast messages if no permissions
//  are granted.
//
//  TODO: Test after AddItemActivity works
///////////////////////////////////


package com.project.InventoryApp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.List;
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Item> itemList;
    private Context context;
    private DataBaseHelper dbHelper;
    private int userId;


    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Item item = itemList.get(position);
        holder.textName.setText(item.getName());
        holder.textQuantity.setText("Quantity: " + item.getQuantity());
        holder.textDescription.setText(item.getDescription());
        holder.textCurrentQuantity.setText(String.valueOf(item.getQuantity()));

        //Handle item click for edit and delete from inventory
        holder.itemView.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Choose an action")
                    .setMessage("Do you want to update or delete this item?")
                    .setPositiveButton("Update", (dialog, which)-> {
                        //Used to send item data to EditItemActivity
                        Intent intent = new Intent(view.getContext(), EditItemActivity.class);
                        intent.putExtra("ITEM_ID", item.getId());
                        intent.putExtra("ITEM_NAME", item.getName());
                        intent.putExtra("ITEM_QUANTITY", item.getQuantity());
                        intent.putExtra("ITEM_DESCRIPTION", item.getDescription());
                        view.getContext().startActivity(intent);
                    })
                    .setNegativeButton("Delete", (dialog, which) -> {
                        //Used to delete item from inventory
                        DataBaseHelper dbHelper= new DataBaseHelper(view.getContext());
                        dbHelper.deleteItem(item.getId());
                        itemList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(view.getContext(), "Item Deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNeutralButton("Cancel", null).show(); //Cancel action
        });

        //Increase "+" button increments the quantity of item
        holder.btnIncrease.setOnClickListener (view -> {
            DataBaseHelper dbHelper= new DataBaseHelper(view.getContext());
            int quantity = item.getQuantity() + 1;
            if(dbHelper.updateItemQuantity(item.getId(), quantity)) {
                itemList.get(position).setQuantity(quantity);
                notifyItemChanged(position);
            }
        });

        //Decrease "-" button decrements the quantity of item
        holder.btnDecrease.setOnClickListener(view -> {
            DataBaseHelper dbHelper= new DataBaseHelper(view.getContext());

            //This set of if statements ensures that the quantity is first greater than 0
            //before making any decrements
            if (item.getQuantity() > 0) {
                int newQuantity = item.getQuantity() - 1;
                if (dbHelper.updateItemQuantity(item.getId(), newQuantity)) {
                    itemList.get(position).setQuantity(newQuantity);
                    notifyItemChanged(position);
                }

                // Send SMS Alert when inventory quantity reaches 0
                // sendLowStockAlert will check for permissions
                if (newQuantity == 0) {
                    String userPhoneNumber = dbHelper.getUserPhoneNumber(userId);
                    sendLowStockAlert(item.getName(), userPhoneNumber);
                }
            }
            //Error message for user if at 0 and attempt is made to decrement.
            else {
                Toast.makeText(context, "This item can't be decreased", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Send SMS if stock reaches 0
    private void sendLowStockAlert(String itemName, String userPhone) {
        //Checks SMS permissions of the app on current device.
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                //Informs user that stock is low
                String message = "Alert: The item '" + itemName + "' is out of stock!";
                smsManager.sendTextMessage(userPhone, null, message, null, null);
                Toast.makeText(context, "Low stock alert sent!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "SMS failed to send.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        //Sends a message to alert the user that SMS permissions are not granted.
        else {
            Toast.makeText(context, "SMS permission not granted!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    //Creates the
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View btnIncrease;
        public View btnDecrease;
        public BreakIterator textCurrentQuantity;
        TextView textName, textQuantity, textDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textQuantity= itemView.findViewById(R.id.textQuantity);
            textDescription= itemView.findViewById(R.id.textDescription);
        }
    }
}
