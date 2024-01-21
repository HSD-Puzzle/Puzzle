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

/**
 * The counter where Recipes can spawn and placed on
 */
public class TopRecipeCounter extends CounterView implements OrderSpawn {

    @Override
    protected void onRestaurantBound(RestaurantFragment restaurantFragment) {
        restaurantFragment.addOrderSpawn(this);
    }

    public TopRecipeCounter(Context context) {
        super(context);
    }

    public TopRecipeCounter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * adds a Listener to place recipes on this counter
     *
     * @param context the context of this app, can be used to load the correct language
     * @param attrs   the AttributeSet that is used in xml
     */
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

    /**
     * true if this was already drawn
     */
    private boolean drawn = false;

    /**
     * a Queue that contains all not spawned recipes
     */
    private final Queue<ItemView> notPlaced = new LinkedList<>();

    /**
     * places all not drawn recipes
     */
    @Override
    protected void afterDraw() {
        super.afterDraw();
        drawn = true;
        while (!notPlaced.isEmpty()) {
            ItemView itemView = notPlaced.poll();
            itemView.setTranslationY(getHeight() * 0.37f);
            itemView.setTranslationX((float) (getX() + Math.random() * getWidth()));
        }
    }

    /**
     * spawns all recipes as OrderViews or adds them to Queue that will be spawned when possible
     *
     * @param recipe the {@link Recipe} the {@link com.example.burger42.Game.UI.ItemViews.OrderView} should base on.
     */
    @Override
    public void spawnAndPlaceRecipe(Recipe recipe) {
        OrderView newOrder = new OrderView(getContext(), recipe, restaurantFragment);
        restaurantFragment.addItem(newOrder);
        if (drawn) {
            newOrder.setTranslationY(getHeight() * 0.37f);
            newOrder.setTranslationX((int) Math.max(getX() + getWidth() * Math.random() - newOrder.getCustomWidth() / 2f, 0));
        } else {
            notPlaced.add(newOrder);
        }

    }
}
