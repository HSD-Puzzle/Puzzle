package com.example.burger42.Game.UI.ItemViews;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.Scaffolding.DragAreaSetItemAbove;
import com.example.burger42.Game.UI.Scaffolding.IngredientView;
import com.example.burger42.Ingredients.BottomBurgerBun;
import com.example.burger42.Ingredients.BurgerPatty;
import com.example.burger42.Ingredients.Ingredient;
import com.example.burger42.R;

public class PattyView extends IngredientView {

    public PattyView(Context context) {
        super(context);
    }

    public PattyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Ingredient toIngredientWrapper() {
        return new BurgerPatty();
    }

    @Override
    protected int drawableId() {
        return R.drawable.burgerpattyraw;
    }


    @Override
    public String name() {
        return "BurgerPatty";
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
        return 117f / 500f;
    }
}
