package com.example.burger42.ArrayAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Item.BillItem;
import com.example.burger42.Item.BillDetailItem;
import com.example.burger42.R;

/**
 * The BillItemAdapter is an adapter class, that is used to convert a list of BillItem in a view, that shows the information of the BillItems.
 */
public class BillItemAdapter extends ArrayAdapter<BillItem> {
    /**
     * This context contains information about this application, that is needed to get Text in the correct language.
     */
    private final Context context;

    /**
     * @param context this context contains information about this application, that is needed to get Text in the correct language.
     */
    public BillItemAdapter(@NonNull Context context) {
        super(context, 0);
        this.context = context;
    }

    /**
     * Method to create and setup the individual items of this list.
     *
     * @param position    an index for the Item which gets accessed.
     * @param convertView the accessed View which gets converted during the process.
     * @param parent      the parent ViewGroup.
     * @return the converted view
     */
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        BillItem currentItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.bill_item, parent, false);
        }

        if (currentItem != null) {
            TextView amountTextView = convertView.findViewById(R.id.billItem_amount);
            amountTextView.setText(currentItem.orderNumber() + ". " + getContext().getString(R.string.order));


            TextView totalValueTextView = convertView.findViewById(R.id.billItem_totalValue);
            totalValueTextView.setText(currentItem.totalValue() + "$");

            LinearLayout linearLayout = convertView.findViewById(R.id.billItem_detaillist);
            linearLayout.removeAllViews();
            if (currentItem.isDetailView()) {
                for (BillDetailItem x : currentItem.billOrderItems()) {
                    View view = LayoutInflater.from(context).inflate(R.layout.detailsbill_item, parent, false);

                    TextView name = view.findViewById(R.id.detailsbillItem_name);
                    name.setText(x.name());

                    TextView money = view.findViewById(R.id.detailsbillItem_money);
                    money.setText(x.beforeStreak() + "$");

                    TextView streak = view.findViewById(R.id.detailsbillItem_streak);
                    streak.setText(String.format("%.2f", x.streak()));

                    View view1 = view.findViewById(R.id.detailsbillItem_streakdestroyed);
                    view1.setVisibility(x.isDone() ? View.INVISIBLE : View.VISIBLE);

                    linearLayout.addView(view);
                }
            }
        }

        return convertView;
    }
}
