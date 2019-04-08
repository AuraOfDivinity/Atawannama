package com.example.atawannamaapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;



public class OperationsActivity extends Activity implements Validator.ValidationListener{


    @Order(1)
    @Length(min = 4, message = "Please enter a start time.")
    @Pattern(regex = "[0-9]{2}[.][0-9]{2}", message = "Please enter the time in the HH.MM format")
    EditText startTime;

    @Order(2)
    @Length(min = 4, message = "Please enter an end time.")
    @Pattern(regex = "[0-9]{2}[.][0-9]{2}", message = "Please enter the time in the HH.MM format")
    EditText endTime;


    EditText opEmpName;

    @Order(3)
    @Length(min = 3, message = "Please enter a location")
    @Pattern(regex = "[A-Za-z]+", message = "Should contain only alphabets")
    EditText location;

    Button opSubmit, opSearch;

    String returnedEmpName;

    Validator validator;


    private MobileServiceClient mClient;
    private MobileServiceTable<Operation> mOpTable;
    private MobileServiceTable<Employee> mEmpTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        validator = new Validator(this);
        validator.setValidationListener(this);

        try {
            mClient = new MobileServiceClient(
                    "https://atawannamaapp.azurewebsites.net",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        startTime = (EditText) findViewById(R.id.editStartTime);
        endTime = (EditText) findViewById(R.id.editEndTime);
        opEmpName = (EditText) findViewById(R.id.editEmpName);
        location = (EditText) findViewById(R.id.editLocation);

        opSearch = (Button) findViewById(R.id.opSearch);
        opSubmit = (Button) findViewById(R.id.opSubmit);




        opSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findEmployeeAndNotify();
            }
        });

        opSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
    }

    public void addOperation() {
        if (mClient == null) {
            return;
        }

        // Create a new item
        final Operation op = new Operation();

//        exp.setExpenseAmount(Double.parseDouble(txtExpAmount.getText().toString()));
//        exp.setExpenseComments(txtExpComments.getText().toString());
//        exp.setExpenseID(txtExpID.getText().toString());
//        exp.setExpenseAuth(txtExpAuth.getText().toString());

        op.setOpEmployee(returnedEmpName);
        op.setOpStartTime(startTime.getText().toString());
        op.setOpEndTime(endTime.getText().toString());
        op.setOpLocation(location.getText().toString());

        // Insert the new item
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final Operation fOp = addOperationInTable(op);


                } catch (final Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Toast.makeText(this, "Operation successfully added!", Toast.LENGTH_SHORT).show();
        runAsyncTask(task);

    }


    public Operation addOperationInTable(Operation operation) throws ExecutionException, InterruptedException {
        mOpTable = mClient.getTable("Operations", Operation.class);
        Operation returned = mOpTable.insert(operation).get();

        return returned;
    }

    public void findEmployeeAndNotify() {

        mEmpTable = mClient.getTable("Employees", Employee.class);
        mOpTable = mClient.getTable("Operations", Operation.class);


        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {

                    runOnUiThread(new Runnable() {

                        String employee = opEmpName.getText().toString();
                        List<Employee> results = mEmpTable.where().field("name").eq(employee).execute().get();

                        @Override
                        public void run() {


                            for (Employee emp : results) {
                                Employee returnedEmp = emp;
                                if (returnedEmp.getName() != null) {
                                    returnedEmpName = returnedEmp.getName();
                                    runOnUiThread(new Runnable() {
                                        public void run() {

                                            Toast.makeText(OperationsActivity.this, "Employee successfully found!", Toast.LENGTH_SHORT).show();
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

    public void outputToast() {
        Toast.makeText(this, "Employee found successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationSucceeded() {
        addOperation();
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
