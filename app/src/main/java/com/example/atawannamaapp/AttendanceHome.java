package com.example.atawannamaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.List;

public class AttendanceHome extends Activity {

    private MobileServiceClient mClient;
    private MobileServiceTable<Employee> mEmpTable;
    List<Employee> empResult;

    TextView txtPresent, txtAbsent;
    int presentAmt, absentAmt;

    @Override
    public boolean onNavigateUp() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_home);



        try {
            mClient = new MobileServiceClient(
                    "https://atawannamaapp.azurewebsites.net",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        showAll();




        //getSupportActionBar().setTitle("Attendance Main");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showAll() {
        mEmpTable = mClient.getTable("Employees", Employee.class);
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    empResult = mEmpTable.select( "present_status").execute().get();

                    for(Employee emp:empResult){
                        String status = emp.getPresent_status().toUpperCase();
                        if(status == "PRESENT"){
                            presentAmt++;
                        }
                        else{
                            absentAmt++;
                        }
                    }
                    txtPresent = (TextView) findViewById(R.id.presenttext);
                    txtAbsent = (TextView)findViewById(R.id.absenttext);

                    txtAbsent.setText(String.valueOf(absentAmt));
                    txtPresent.setText(String.valueOf(presentAmt));

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
            }
        };
        runAsyncTask(task);
    }

    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }


    public void toAddEmployee(View v){
        Intent intent = new Intent(AttendanceHome.this, attendanceAddemployee.class);
        startActivity(intent);
    }

    public void toViewEmployee(View v){
        Intent intent = new Intent(AttendanceHome.this, attendanceEmployees.class);
        startActivity(intent);
    }
}
