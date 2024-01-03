package com.example.burger42.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.ArrayAdapter.BillItemAdapter;
import com.example.burger42.Item.BillItem;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

import java.util.List;

public class BillFragment extends ParentFragment {

    private ListView listView;
    private BillItemAdapter billItemAdapter;

    public BillFragment(MainActivity mainActivity, List<BillItem> billItems) {
        super(mainActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        listView = view.findViewById(R.id.levelBill_ListOfBillItems);
        billItemAdapter = new BillItemAdapter(mainActivity);
        listView.setAdapter(billItemAdapter);
        billItemAdapter.add(new BillItem(10, "Pommes", 100));
        return view;
    }
}
