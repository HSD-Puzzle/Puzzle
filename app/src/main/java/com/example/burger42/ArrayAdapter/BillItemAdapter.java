package com.example.burger42.ArrayAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Item.BillItem;
import com.example.burger42.R;

public class BillItemAdapter extends ArrayAdapter<BillItem> {
    private Context context;

    public BillItemAdapter(@NonNull Context context) {
        super(context, 0);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        BillItem currentItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.bill_item, parent, false);
        }

        TextView amountTextView = (TextView) convertView.findViewById(R.id.billItem_amount);
        amountTextView.setText(currentItem.amount() + "x");


        TextView totalValueTextView = (TextView) convertView.findViewById(R.id.billItem_totalValue);
        totalValueTextView.setText(currentItem.totalValue() + "€");

        return convertView;
    }
}
