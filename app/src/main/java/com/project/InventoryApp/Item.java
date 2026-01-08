///////////////////////////////////
//  Anthony Baratti
//  SNHU CS-360 Mobile Arch & Prog
//  Prof N. Shepard
//  February 23, 2025
//  Item.java
//
//  This class is an Item object creator class
//  uses constructor: id, name, quantity, description
//  with setters and getters.
///////////////////////////////////


package com.project.InventoryApp;

public class Item {
    private int id;
    private String name;
    private int quantity;
    private String description;

    //Item constructor
    public Item(int id, String name, int quantity, String description) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
    }

    //getters
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }
    public int getQuantity(){
        return quantity;
    }

    public String getDescription(){
        return description;
    }

    //setters
    public void setQuantity(int quantity) { //used in increase/decrease buttons
        this.quantity = quantity;
    }
    public void setName(String name){
        this.name= name;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
