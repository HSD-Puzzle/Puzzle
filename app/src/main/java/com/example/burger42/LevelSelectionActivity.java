package com.example.burger42;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class LevelSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);
        createAndDisplayLevelItems();
    }
    private void createAndDisplayLevelItems() {
        LinearLayout levelItemsLayout = findViewById(R.id.level_items_layout);

        for (int i = 1; i <= 10; i++) {
            Button levelButton = new Button(this);
            levelButton.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            LevelDisplayItem item = new LevelDisplayItem(i);
            levelButton.setText("Level: " + item.levelId + " Stars: " + item.stars + " Highscore: " + item.highscore);
            levelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

            // Füge das Level-Display-Item zum Layout hinzu.
            levelItemsLayout.addView(levelButton);
        }
    }
}