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
import com.example.burger42.Item.BillOrderItem;
import com.example.burger42.R;

public class BillOrderItemAdapter extends ArrayAdapter<BillOrderItem> {
    private Context context;

    public BillOrderItemAdapter(@NonNull Context context) {
        super(context, 0);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        BillOrderItem currentItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.bill_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.detailsbillItem_name);
        name.setText(currentItem.name());

        TextView money = (TextView) convertView.findViewById(R.id.detailsbillItem_money);
        money.setText(currentItem.beforeStreak() + "$");

        TextView streak = (TextView) convertView.findViewById(R.id.detailsbillItem_streak);
        streak.setText(String.format("%.2f", currentItem.streak()));

        View view = convertView.findViewById(R.id.detailsbillItem_streakdestroyed);
        view.setVisibility(currentItem.isDone() ? View.INVISIBLE : View.VISIBLE);

        return convertView;
    }
}
