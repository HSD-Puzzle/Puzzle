package com.example.burger42.Game.UI.CounterViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.Recipe;
import com.example.burger42.Game.UI.ItemViews.OrderView;
import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.OnDragAreaListener;
import com.example.burger42.Game.UI.Scaffolding.OrderSpawn;
import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.R;

import java.util.LinkedList;
import java.util.Queue;

public class TopRecepieCounter extends CounterView implements OrderSpawn {

    @Override
    protected void onRestaurantBound(RestaurantFragment restaurantFragment) {
        restaurantFragment.addOrderSpawn(this);
    }

    public TopRecepieCounter(Context context) {
        super(context);
    }

    public TopRecepieCounter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        addOnDragAreaListener(new OnDragAreaListener() {
            @Override
            protected boolean onDrag(DragEvent event, boolean inArea) {
                if (inArea && event.getAction() == DragEvent.ACTION_DROP) {
                    ItemView itemView = (ItemView) event.getLocalState();
                    itemView.removeFromParent();
                    restaurantFragment.addItem(itemView);
                    itemView.setTranslationY(getHeight() * 0.37f);
                    itemView.setTranslationX(Math.max(getX() + event.getX() - itemView.getCustomWidth() / 2f, 0));
                    return true;
                }
                return false;
            }
        }.setRelativeTop(0.9f).setRelativeBottom(0.6f).addFilterTag("Order").setUseFilter(true));
    }

    @Override
    protected float scaling() {
        if (isInEditMode()) return 1;
        return 4;
    }

    @Override
    protected int drawableId() {
        return R.drawable.topcorner;
    }

    private boolean drawed = false;

    private Queue<ItemView> notPlaced = new LinkedList<>();

    @Override
    protected void afterDraw() {
        super.afterDraw();
        drawed = true;
        while (!notPlaced.isEmpty()) {
            ItemView itemView = notPlaced.poll();
            itemView.setTranslationY(getHeight() * 0.37f);
            itemView.setTranslationX((float) (getX() + Math.random() * getWidth()));
        }
    }

    @Override
    public void spawnAndPlaceRecipe(Recipe recipe) {
        OrderView newOrder = new OrderView(getContext(), recipe);
        restaurantFragment.addItem(newOrder);
        if (drawed) {
            newOrder.setTranslationY(getHeight() * 0.37f);
            newOrder.setTranslationX((float) (getX() + Math.random() * getWidth()));
        } else {
            notPlaced.add(newOrder);
        }

    }
}
