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

public class ItemAdapter extends ArrayAdapter<Item> {
    /**
     * Adapter context
     */
    Context mContext;

    /**
     * Adapter View layout
     */
    int mLayoutResourceId;

    public ItemAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        final Item currentItem = getItem(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.listrowitems, parent, false);
        }
        row.setTag(currentItem);

        final TextView nameText = (TextView)row.findViewById(R.id.itemlName);
        final TextView quantityText = (TextView)row.findViewById(R.id.itemlQuantity);
        final TextView supplierText = (TextView)row.findViewById(R.id.itemlSupplier);

        nameText.setText(currentItem.getItemName());
        quantityText.setText(String.valueOf(currentItem.getItemQuantity()) + "pcs");
        supplierText.setText(currentItem.getItemSupplier());

        return row;
    }
}