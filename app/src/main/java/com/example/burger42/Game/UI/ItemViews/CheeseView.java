package com.example.burger42.Game.UI.ItemViews;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.Scaffolding.DragAreaSetItemAbove;
import com.example.burger42.Game.UI.Scaffolding.IngredientView;
import com.example.burger42.Ingredients.Chesse;
import com.example.burger42.Ingredients.Ingredient;
import com.example.burger42.R;

/**
 * The graphical representation of the cheese
 */
public class CheeseView extends IngredientView {

    public CheeseView(Context context) {
        super(context);
    }

    public CheeseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Ingredient toIngredientWrapper() {
        return new Chesse();
    }

    @Override
    protected int drawableId() {
        return R.drawable.cheese;
    }


    @Override
    public String name() {
        return "Cheese";
    }

    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        addOnDragAreaListener(new DragAreaSetItemAbove(this).setUseFilter(true).addFilterTag(ItemFilterTag.Ingredient));
    }

    @Override
    protected ItemAboveNode[] itemAboveSetUp() {
        return new ItemAboveNode[]{new ItemAboveNode(0, 0.045f) {
        }};
    }

    @Override
    protected float scaling() {
        return 117f / 500f;
    }
}
