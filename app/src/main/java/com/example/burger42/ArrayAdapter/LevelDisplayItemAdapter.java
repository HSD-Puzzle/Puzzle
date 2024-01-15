package com.example.burger42.ArrayAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.Item.StarItem;
import com.example.burger42.R;

/**
 * Class for an Level Display Item Adapter, an Adapter which holds Information about Level Display
 * Items.
 */
public class LevelDisplayItemAdapter extends ArrayAdapter<RestaurantFragment> {
    private final Context context;

    /**
     * Constructor for an Level Display Item Adapter.
     *
     * @param context a Context (i.a. Activity) to get Access to the View
     */
    public LevelDisplayItemAdapter(@NonNull Context context) {
        super(context, 0);
        this.context = context;
    }

    /**
     * Method to Access and modify the View of the Activity/Fragment
     *
     * @param position    an index for the Item which gets accessed.
     * @param convertView the accessed View which gets converted during the process.
     * @param parent      the parent ViewGroup.
     * @return the converted view
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        RestaurantFragment currentItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.level_item, parent, false);
        }
        currentItem.loadData();
        TextView levelHighscoreTextView = convertView.findViewById(R.id.levelHighscoreTextView);
        levelHighscoreTextView.setText("Highscore: " + currentItem.highScore());
        TextView title = convertView.findViewById(R.id.level_item_title);
        title.setText(currentItem.title());
        ImageView imageView = convertView.findViewById(R.id.level_item_thumbnail);
        ImageView[] stars = new ImageView[3];
        stars[0] = convertView.findViewById(R.id.level_item_star1);
        stars[1] = convertView.findViewById(R.id.level_item_star2);
        stars[2] = convertView.findViewById(R.id.level_item_star3);
        for (int i = 0; i < currentItem.starItems().length; i++) {
            if (currentItem.starItems()[i].done()) {
                stars[i].setImageResource(R.drawable.twotone_star_24_filled);
            } else {
                stars[i].setImageResource(R.drawable.twotone_star_24);
            }
        }
        imageView.setImageResource(currentItem.thumbnailId());


        return convertView;
    }
}
