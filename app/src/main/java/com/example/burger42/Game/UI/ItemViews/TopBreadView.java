package com.example.burger42.Game.UI.ItemViews;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.Scaffolding.IngredientView;
import com.example.burger42.Ingredients.Ingredient;
import com.example.burger42.Ingredients.TopBurgerBun;
import com.example.burger42.R;

public class TopBreadView extends IngredientView {

    public TopBreadView(Context context) {
        super(context);
    }

    public TopBreadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Ingredient toIngredientWrapper() {
        return new TopBurgerBun();
    }

    @Override
    protected int drawableId() {
        return R.drawable.topbread;
    }

    @Override
    public String name() {
        return "TopBreadView";
    }

    @Override
    protected float scaling() {
        return 127f / 500f;
    }

    @Override
    protected ItemAboveNode[] itemAboveSetUp() {
        return new ItemAboveNode[0];
    }
}
