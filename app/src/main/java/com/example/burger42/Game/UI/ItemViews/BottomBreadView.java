package com.example.burger42.Game.UI.ItemViews;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.Scaffolding.DragAreaSetItemAbove;
import com.example.burger42.Game.UI.Scaffolding.IngredientView;
import com.example.burger42.Ingredients.BottomBurgerBun;
import com.example.burger42.Ingredients.Ingredient;
import com.example.burger42.R;

/**
 * The graphical representation of the bottom bread
 */
public class BottomBreadView extends IngredientView {

    public BottomBreadView(Context context) {
        super(context);
    }

    public BottomBreadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Ingredient toIngredientWrapper() {
        return new BottomBurgerBun();
    }

    @Override
    protected int drawableId() {
        return R.drawable.bottombread;
    }


    @Override
    public String name() {
        return "BottomBread";
    }

    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        addOnDragAreaListener(new DragAreaSetItemAbove(this).setUseFilter(true).addFilterTag(ItemFilterTag.Ingredient));
    }

    @Override
    protected ItemAboveNode[] itemAboveSetUp() {
        return new ItemAboveNode[]{new ItemAboveNode(0, 0.075f) {
        }};
    }

    @Override
    protected float scaling() {
        return 121f / 500f;
    }
}
