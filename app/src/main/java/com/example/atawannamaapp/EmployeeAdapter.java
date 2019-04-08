package com.example.atawannamaapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EmployeeAdapter extends ArrayAdapter<Employee> {
    /**
     * Adapter context
     */
    Context mContext;

    /**
     * Adapter View layout
     */
    int mLayoutResourceId;

    public EmployeeAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        final Employee currentItem = getItem(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.listrow, parent, false);
        }
        row.setTag(currentItem);

        final TextView nameText = (TextView)row.findViewById(R.id.luName);
        final TextView clearanceText = (TextView)row.findViewById(R.id.luClearance);
        final TextView statusText = (TextView)row.findViewById(R.id.luPresentstatus);

        nameText.setText(currentItem.getName());
        clearanceText.setText(currentItem.getClearance());
        statusText.setText(currentItem.getPresent_status());

        return row;
    }
}
