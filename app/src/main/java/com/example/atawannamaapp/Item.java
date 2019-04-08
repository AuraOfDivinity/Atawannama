package com.example.atawannamaapp;

public class Item {

    @com.google.gson.annotations.SerializedName("id")
    String itemID;

    @com.google.gson.annotations.SerializedName("item_name")
    String itemName;

    @com.google.gson.annotations.SerializedName("item_quanitity")
    int itemQuantity;

    @com.google.gson.annotations.SerializedName("item_link")
    String itemLink;

    @com.google.gson.annotations.SerializedName("item_supplier")
    String itemSupplier;





    public Item(){}

    public Item( String itemName, String itemID, int itemQuantity, String itemLink, String itemSupplier) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemLink = itemLink;
        this.itemSupplier = itemSupplier;

    }

    public String getItemLink() {
        return itemLink;
    }

    public void setItemLink(String itemLink) {
        this.itemLink = itemLink;
    }

    public Item(String itemName){
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemSupplier() {
        return itemSupplier;
    }

    public void setItemSupplier(String itemSupplier) {
        this.itemSupplier = itemSupplier;
    }

//    public String getItemDescription() {
//        return itemDescription;
//    }
//
//    public void setItemDescription(String itemDescription) {
//        this.itemDescription = itemDescription;
//    }
}
