package com.example.burger42.Game.UI.Counter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.CustomView;
import com.example.burger42.Game.UI.ItemViews.ItemView;
import com.example.burger42.Game.UI.RestaurantLevel.RestaurantFragment;

import java.util.LinkedList;
import java.util.List;
import java.util.prefs.PreferencesFactory;

public abstract class CounterView extends CustomView {
    private List<DragArea> dragAreas;

    public CounterView(Context context, RestaurantFragment restaurantFragment) {
        super(context, restaurantFragment);
    }

    public CounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public List<DragArea> dragAreas() {
        if (dragAreas == null) {
            dragAreas = super.dragAreas();
            dragAreas.add(new DragArea(0.4f, 0.6f, 0, 1) {
                @Override
                protected boolean onDragEvent(DragEvent event) {
                    if (event.getAction() == DragEvent.ACTION_DROP) {
                        ItemView itemView = (ItemView) event.getLocalState();
                        restaurantFragment.addItem(itemView);
                        itemView.setTranslationY(getHeight() - getHeight() * 0.41f);
                        itemView.setTranslationX(Math.max(getX() + event.getX() - itemView.getWidth() / 2, 0));

                    }
                    return true;
                }
            });
        }
        return dragAreas;
    }
}
