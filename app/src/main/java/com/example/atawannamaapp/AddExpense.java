package com.example.atawannamaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddExpense extends Activity {

    private MobileServiceClient mClient;
    private MobileServiceTable<Expense> mExpTable;

    TextView txtExpAmount, txtExpID, txtExpComments, txtExpAuth;
    Button expSubmit, expCancel, expAuth, expGetID;

    Boolean Auth = false;
    Boolean ID = false;

    Expense returnedExpense;

    int oldID = 0;
    int newID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        txtExpAmount = (TextView) findViewById(R.id.expAmount);
        txtExpID = (TextView) findViewById(R.id.expID);
        txtExpComments = (TextView) findViewById(R.id.expComments);
        txtExpAuth = (TextView) findViewById(R.id.expAuth);

        expSubmit = (Button) findViewById(R.id.btnExpSubmit);
        expCancel = (Button) findViewById(R.id.btnExpCancel9);
        expAuth = (Button) findViewById(R.id.btnExpAuthUser);
        expGetID = (Button) findViewById(R.id.btnExpGetID);

        try {
            mClient = new MobileServiceClient(
                    "https://atawannamaapp.azurewebsites.net",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        expCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 =  new Intent(AddExpense.this, HomeActivity.class);
                startActivity(intent1);
            }
        });

        expAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtExpAuth.setText(LoginActivity.loggedInAs);
            }
        });

        expGetID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastExpense(view);

            }
        });

        expSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExpense(view);
            }
        });

        expCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddExpense.this, ExpenseHome.class);
                startActivity(intent);
            }
        });
    }

    public void getLastExpense(View view) {
        mExpTable = mClient.getTable("Expense", Expense.class);
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final List<Expense> results = mExpTable.orderBy("ID", QueryOrder.Descending).top(1).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            for (Expense exp : results) {
                                returnedExpense = exp;
                                oldID = Integer.parseInt(returnedExpense.getExpenseID());
                                newID = oldID + 1;
                                txtExpID.setText(String.valueOf(newID));
                                ID = true;
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


    public void addExpense(View view) {
        if (mClient == null) {
            return;
        }

        // Create a new item
        final Expense exp = new Expense();

        exp.setExpenseAmount(Double.parseDouble(txtExpAmount.getText().toString()));
        exp.setExpenseComments(txtExpComments.getText().toString());
        exp.setExpenseID(txtExpID.getText().toString());
        exp.setExpenseAuth(txtExpAuth.getText().toString());

        // Insert the new item
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final Expense entity = addExpenseInTable(exp);


                } catch (final Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Toast.makeText(this, "Employee successfully added!", Toast.LENGTH_SHORT).show();
        runAsyncTask(task);

    }


    public Expense addExpenseInTable(Expense exp) throws ExecutionException, InterruptedException {
        mExpTable = mClient.getTable("Expense", Expense.class);
        Expense returned = mExpTable.insert(exp).get();

        return returned;
    }


    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }
}
