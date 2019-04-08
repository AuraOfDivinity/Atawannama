package com.example.atawannamaapp;

public class InventoryItem {

    @com.google.gson.annotations.SerializedName("id")
    int id;

    @com.google.gson.annotations.SerializedName("item_name")
    String item_name;


    public InventoryItem(int id, String item_name) {
        this.id = id;
        this.item_name = item_name;
    }

    public InventoryItem(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
}
