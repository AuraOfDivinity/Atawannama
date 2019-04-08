package com.example.atawannamaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends Activity {
    private MobileServiceClient mClient;
    private MobileServiceTable<Employee> mEmpTable;

    public static String empClearance;
    public static String loggedInAs;

    Button loginBtn;

    EditText loginUser;


    EditText loginPass;

    Employee emp = new Employee();
    List<Employee> employeeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.loginEmp);

        loginUser = (EditText)findViewById(R.id.loginUser);
        loginPass = (EditText)findViewById(R.id.loginPass);

        try {
            mClient = new MobileServiceClient(
                    "https://atawannamaapp.azurewebsites.net",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredentials();
            }
        });

    }

    public void checkCredentials() {

        mEmpTable = mClient.getTable("Employees", Employee.class);

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {

                    runOnUiThread(new Runnable() {

                        String enteredUser = loginUser.getText().toString();


                        List<Employee> results = mEmpTable.where().field("Uname").eq(enteredUser).execute().get();

                        @Override
                        public void run() {

                            for (Employee emp : results) {
                                Employee returnedEmp = emp;
                                if (returnedEmp.getUsername().equals(loginUser.getText().toString()) && returnedEmp.getPassword().equals(loginPass.getText().toString())) {
                                    empClearance = emp.getClearance();
                                    loggedInAs = emp.getName();
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);

                                    runOnUiThread(new Runnable() {
                                        public void run() {

                                        }
                                    });

                                }

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
