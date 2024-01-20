package com.example.burger42.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.ArrayAdapter.BillItemAdapter;
import com.example.burger42.Item.BillItem;
import com.example.burger42.Item.StarItem;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

import java.util.List;

/**
 * This fragment shows the result after the end of time of a Level.
 */
public class BillFragment extends SuperFragment {
    /**
     * The totalValue is the total amount of money earned in this level. This is the score.
     */
    private final int totalValue;

    /**
     * a list of all BillItems, that should be displayed on the left side of this fragment.
     */
    private final List<BillItem> billItems;

    /**
     * the starItems, that should be shown on the right side of this fragment.
     */
    private final StarItem[] starItems;

    /**
     * true if this the player had earned an new highscore.
     */
    private boolean isNewHighscore;

    /**
     * @param mainActivity   the mainActivity, that shows this fragment
     * @param billItems      a list of all BillItems, that should be displayed on the left side of this fragment.
     * @param totalValue     the total amount of money earned in this level. This is the score.
     * @param starItems      the starItems, that should be shown on the right side of this fragment.
     * @param isNewHighscore true if this the player had earned an new highscore
     */
    public BillFragment(MainActivity mainActivity, List<BillItem> billItems, int totalValue, StarItem[] starItems, boolean isNewHighscore) {
        super(mainActivity);
        this.billItems = billItems;
        this.totalValue = totalValue;
        this.starItems = starItems;
        this.isNewHighscore = isNewHighscore;
    }

    /**
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return the View to display this Fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        ListView listView = view.findViewById(R.id.levelBill_ListOfBillItems);
        BillItemAdapter billItemAdapter = new BillItemAdapter(mainActivity);
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

        TextView highscore = view.findViewById(R.id.levelBill_Highscore);
        highscore.setVisibility(isNewHighscore ? View.VISIBLE : View.INVISIBLE);

        LinearLayout linearLayout = view.findViewById(R.id.bill_star_container);
        for (StarItem x : starItems) {
            View star = inflater.inflate(R.layout.bill_star_item, linearLayout, false);
            TextView starTitle = star.findViewById(R.id.bill_star_item_headline);
            starTitle.setText(x.title(mainActivity));
            TextView degreeOfSuccess = star.findViewById(R.id.bill_star_item_degreeOfSuccess);
            degreeOfSuccess.setText(x.degreeOfSuccess(mainActivity));
            ImageView starIcon = star.findViewById(R.id.bill_star);
            starIcon.setImageResource(x.done() ? R.drawable.twotone_star_24_filled : R.drawable.twotone_star_24);
            linearLayout.addView(star);
        }
        return view;
    }

    /**
     * onBackPressed is called by the MainActivity if this fragment is the currently displayed fragment and the back button of the device is pressed.
     * The methode switches from the BillFragment to the LevelSelectionFragment.
     */
    @Override
    public void onBackPressed() {
        mainActivity.showFragment(new LevelSelectionFragment(mainActivity), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
    }
}
