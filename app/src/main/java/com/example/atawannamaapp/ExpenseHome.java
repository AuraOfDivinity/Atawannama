package com.example.atawannamaapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExpenseHome extends Activity {

    Button backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_home);

       backbtn = (Button)findViewById(R.id.expenseBack);

       backbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent =  new Intent(ExpenseHome.this, HomeActivity.class);
               startActivity(intent);
           }
       });



    }

    public void changeAddExpense(View v){
        Intent intent =  new Intent(ExpenseHome.this, AddExpense.class);
        startActivity(intent);
    }
}
