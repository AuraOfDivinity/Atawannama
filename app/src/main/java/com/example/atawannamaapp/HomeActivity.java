package com.example.atawannamaapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }



    public void changeAttendance(View v){
        if(LoginActivity.empClearance.equals("ATTENDANCE")) {
            Intent intent = new Intent(HomeActivity.this, AttendanceHome.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "You do not have clearance view this section. Please contact administration.", Toast.LENGTH_SHORT).show();
        }
    }

    public void changeExpense(View v){
        if(LoginActivity.empClearance.equals("EXPENSE")) {
            Intent intent = new Intent(HomeActivity.this, AddExpense.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "You do not have clearance view this section. Please contact administration.", Toast.LENGTH_SHORT).show();
        }
    }

    public void changeInventory(View v){
        if(LoginActivity.empClearance.equals("INVENTORY")) {
            Intent intent = new Intent(HomeActivity.this, InventoryHome.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "You do not have clearance view this section. Please contact administration.", Toast.LENGTH_SHORT).show();
        }
    }

    public void changeOperation(View v){
        if(LoginActivity.empClearance.equals("OPERATION")) {
            Intent intent = new Intent(HomeActivity.this, OperationsActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "You do not have clearance view this section. Please contact administration.", Toast.LENGTH_SHORT).show();
        }
    }
}
