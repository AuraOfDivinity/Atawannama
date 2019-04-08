package com.example.atawannamaapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class Inventorysearch extends Activity implements Validator.ValidationListener {

    private MobileServiceClient mClient;
    private MobileServiceTable<Item> mItemTable;
    private MobileServiceTable<Employee> mEmpTable;

    LinearLayout searchResult;
    Button btnSearchItem;

    InventoryItem returnedItem;

    Validator validator;

    @Order(1)
    @Length(min = 4, message = "Item name cannot be kept empty")
    @Pattern(regex = "[A-Za-z ]+", message = "Should contain only Letters")
    EditText searchTerm;

    ImageView returnedImage;

    TextView nameText, quantityText, supplierText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventorysearch);

        validator = new Validator(this);
        validator.setValidationListener(this);

        searchResult = (LinearLayout) findViewById(R.id.searchResultLayout);
        searchResult.setVisibility(View.INVISIBLE);

        searchTerm = (EditText) findViewById(R.id.searchEditText);

        nameText = (TextView)findViewById(R.id.sName);
        quantityText = (TextView)findViewById(R.id.sQuantity);
        supplierText = (TextView)findViewById(R.id.sSupplier);


        returnedImage = (ImageView)findViewById(R.id.sImage);

        btnSearchItem = (Button) findViewById(R.id.btnSearchItem);
        btnSearchItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();

            }
        });

        try {
            mClient = new MobileServiceClient(
                    "https://atawannamaapp.azurewebsites.net",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public void findItemAndSetView() {
        mItemTable = mClient.getTable("MobileItems", Item.class);
        mEmpTable = mClient.getTable("Employees",Employee.class);




        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {

                    runOnUiThread(new Runnable() {

                        String name = searchTerm.getText().toString();
                        List<Item> results = mItemTable.where().field("item_name").eq(name).execute().get();
                        @Override
                        public void run() {
                            for(Item item: results){
                                Item returnedItem = item;

                                nameText.setText(returnedItem.getItemName());
                                quantityText.setText(String.valueOf(returnedItem.getItemQuantity()));
                                supplierText.setText( returnedItem.getItemSupplier());


                                Picasso.with(getApplicationContext()).load(returnedItem.getItemLink()).into(returnedImage);
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

    @Override
    public void onValidationSucceeded() {
        searchResult.setVisibility(View.VISIBLE);
        findItemAndSetView();
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
