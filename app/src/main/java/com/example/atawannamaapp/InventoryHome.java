package com.example.atawannamaapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InventoryHome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_home);
    }

    public void changeInventorySpecific(View v){
        Intent intent =  new Intent(InventoryHome.this, Inventorysearch.class);
        startActivity(intent);
    }

    public void changeInventoryDisplayall(View v){
        Intent intent =  new Intent(InventoryHome.this, InventoryDisplayall.class);
        startActivity(intent);
    }
}
