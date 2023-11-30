package com.example.burger42.ArrayAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Item.LevelDisplayItem;
import com.example.burger42.R;

import org.w3c.dom.Text;

/**
 * Class for an Level Display Item Adapter, an Adapter which holds Information about Level Display
 * Items.
 */
public class LevelDisplayItemAdapter extends ArrayAdapter<LevelDisplayItem> {
    private Context context;
    /**
     * Constructor for an Level Display Item Adapter.
     * @param context a Context (i.a. Activity) to get Access to the View
     */
    public LevelDisplayItemAdapter(@NonNull Context context){
        super(context,0);
        this.context = context;
    }
    /**
     * Method to Access and modify the View of the Activity/Fragment
     * @param position an index for the Item which gets accessed.
     * @param convertView the accessed View which gets converted during the process.
     * @param parent the parent ViewGroup.
     * @return the converted view
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LevelDisplayItem currentItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.level_item, parent, false);
        }

        TextView levelIdTextView = (TextView) convertView.findViewById(R.id.levelIdTextView);
        levelIdTextView.setText("Level: " + currentItem.id());
        TextView levelDifficultyTextView = (TextView) convertView.findViewById(R.id.levelDifficultyTextView);
        levelDifficultyTextView.setText("Difficulty: " + currentItem.difficulty() );
        TextView levelHighscoreTextView = (TextView) convertView.findViewById(R.id.levelHighscoreTextView);
        levelHighscoreTextView.setText("Highscore: " + currentItem.highscore());

        return convertView;
    }
}
