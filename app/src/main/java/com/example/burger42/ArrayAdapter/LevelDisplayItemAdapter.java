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

public class LevelDisplayItemAdapter extends ArrayAdapter<LevelDisplayItem> {
    private Context context;
    public LevelDisplayItemAdapter(@NonNull Context context){
        super(context,0);
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LevelDisplayItem currentItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.level_item, parent, false);
        }

        TextView levelIdTextView = (TextView) convertView.findViewById(R.id.levelIdTextView);
        levelIdTextView.setText("Level: " + currentItem.id());
        TextView levelHighscoreTextView = (TextView) convertView.findViewById(R.id.levelHighscoreTextView);
        levelHighscoreTextView.setText("Highscore: " + currentItem.highscore());

        return convertView;
    }
}
