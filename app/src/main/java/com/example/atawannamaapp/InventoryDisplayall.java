package com.example.atawannamaapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.List;

public class InventoryDisplayall extends Activity {

    ItemAdapter itemAdapter;
    ListView itemListview;


    private MobileServiceClient mClient;
    private MobileServiceTable<Item> mItemTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_displayall);

        try {
            mClient = new MobileServiceClient(
                    "https://atawannamaapp.azurewebsites.net",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        itemAdapter = new ItemAdapter(this, R.layout.listrowitems);
        itemListview = (ListView)findViewById(R.id.itemsListView);


        showAll();

        itemListview.setAdapter(itemAdapter);
    }

    public void showAll() {
        mItemTable = mClient.getTable("MobileItems", Item.class);
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final List<Item> results = mItemTable.select("item_name", "item_quanitity", "item_supplier").execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            itemAdapter.clear();
                            for (Item item : results) {
                                itemAdapter.add(item);
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
