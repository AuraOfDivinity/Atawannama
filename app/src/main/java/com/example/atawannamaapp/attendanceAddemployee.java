package com.example.atawannamaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class attendanceAddemployee extends Activity implements Validator.ValidationListener{

    private MobileServiceClient mClient;
    private MobileServiceTable<Employee> mEmpTable;

    Validator validator;


    @Order(1)
    @Length(min = 4, message = "Name field cannot be kept empty")
    @Pattern(regex = "[A-Za-z]+", message = "Should contain only alphabets")
    TextView txtName;

    @Order(2)
    @Length(min = 1, message = "Username field cannot be kept empty")
    @Pattern(regex = "[A-Za-z0-9]+", message = "Should contain only letters and numbers")
    TextView txtUsername;

    @Order(3)
    @Length(min = 7, message = "Password field cannot be kept empty and must be at least 7 characters long")
    @Pattern(regex = "[A-Za-z0-9]+", message = "Should contain only letters and numbers")
    TextView txtPassword;

    @Order(4)
    TextView txtComments;

    @Order(5)
    @Length(min = 7, message = "Clearance field cannot be kept empty and must be at least 7 characters long")
    @Pattern(regex = "ATTENDANCE|OPERATION|EXPENSE|INVENTORY|WORKER+", message = "Should contain one of the following: ATTENDANCE, OPERATION, EXPENSE, INVENTORY, WORKER")
    TextView txtClearance;


    Button submitBtn, cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_addemployee);


        validator = new Validator(this);
        validator.setValidationListener(this);

        txtName = (EditText)findViewById(R.id.empName);
        txtUsername = (EditText)findViewById(R.id.empuser);
        txtPassword = (EditText)findViewById(R.id.emppass);
        txtComments = (EditText)findViewById(R.id.empcomments);
        txtClearance = (EditText)findViewById(R.id.empclearance);

        submitBtn = (Button)findViewById(R.id.btnSubmit);
        cancelBtn = (Button)findViewById(R.id.btnCancel);

        try {
            mClient = new MobileServiceClient(
                    "https://atawannamaapp.azurewebsites.net",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(attendanceAddemployee.this, AttendanceHome.class);
                startActivity(intent);
            }
        });
    }

    public void addEmployee() {
        if (mClient == null) {
            return;
        }

        // Create a new item
        final Employee emp = new Employee();

        emp.setName(txtName.getText().toString());
        emp.setUsername(txtUsername.getText().toString());
        emp.setPassword(txtPassword.getText().toString());
        emp.setClearance(txtClearance.getText().toString());
        emp.setComments(txtComments.getText().toString());

        // Insert the new item
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final Employee entity = addEmployeeInTable(emp);


                } catch (final Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Toast.makeText(this, "Employee successfully added!", Toast.LENGTH_SHORT).show();
        runAsyncTask(task);

    }

    public Employee addEmployeeInTable(Employee emp) throws ExecutionException, InterruptedException {
        mEmpTable = mClient.getTable("Employees", Employee.class);
        Employee returned = mEmpTable.insert(emp).get();

        return returned;
    }


    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }


    @Override
    public void onValidationSucceeded() {
        addEmployee();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
