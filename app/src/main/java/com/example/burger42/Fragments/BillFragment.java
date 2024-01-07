package com.example.burger42.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.ArrayAdapter.BillItemAdapter;
import com.example.burger42.Item.BillItem;
import com.example.burger42.Item.BillOrderItem;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

import java.util.LinkedList;
import java.util.List;

public class BillFragment extends ParentFragment {

    private BillItemAdapter billItemAdapter;
    private int totalValue;

    public List<BillItem> billItems;

    public BillFragment(MainActivity mainActivity, List<BillItem> billItems, int totalValue) {
        super(mainActivity);
        this.billItems = billItems;
        this.totalValue = totalValue;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        ListView listView = view.findViewById(R.id.levelBill_ListOfBillItems);
        billItemAdapter = new BillItemAdapter(mainActivity);
        listView.setAdapter(billItemAdapter);
        billItemAdapter.addAll(billItems);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                billItemAdapter.clear();
                System.out.println("Click" + i);
                billItems.get(i).setDetailView(!billItems.get(i).isDetailView());
                billItemAdapter.addAll(billItems);
            }
        });
        TextView textView = view.findViewById(R.id.levelBill_MoneyResult);
        textView.setText(totalValue + "$");
        Button button = view.findViewById(R.id.levelBill_BackToLevelSelection);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.showFragment(new LevelSelectionFragment(mainActivity), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            }
        });
        return view;
    }
}
