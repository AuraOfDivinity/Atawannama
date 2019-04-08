package com.example.atawannamaapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.List;

public class attendanceEmployees extends Activity {

    EmployeeAdapter empAdapter;
    ListView empListview;


    private MobileServiceClient mClient;
    private MobileServiceTable<Employee> mEmpTable;

    Button refreshBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_employees);

        try {
            mClient = new MobileServiceClient(
                    "https://atawannamaapp.azurewebsites.net",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        empAdapter = new EmployeeAdapter(this, R.layout.listrow);
        empListview = (ListView)findViewById(R.id.myListView);



        showAll();


        empListview.setAdapter(empAdapter);


    }

    public void showAll() {
        mEmpTable = mClient.getTable("Employees", Employee.class);
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final List<Employee> results = mEmpTable.select("name", "clearance", "present_status").execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            empAdapter.clear();
                            for (Employee item : results) {
                                empAdapter.add(item);
                            }
                        }
                    });
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
}
