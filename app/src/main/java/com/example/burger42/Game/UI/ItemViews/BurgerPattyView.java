package com.example.burger42.Game.UI.ItemViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.Scaffolding.DragAreaSetItemAbove;
import com.example.burger42.Game.UI.Scaffolding.IngredientView;
import com.example.burger42.Ingredients.BurgerPattyMedium;
import com.example.burger42.Ingredients.BurgerPattyRare;
import com.example.burger42.Ingredients.BurgerPattyRawOrBurned;
import com.example.burger42.Ingredients.Ingredient;
import com.example.burger42.R;

import java.util.List;

public class BurgerPattyView extends IngredientView {

    private int currentRoastLevel = 0;
    private int currentRoastTimes = 0;
    private static final int[] roastLevelSteps = {8, 16, 22};

    private List<BurgerPattyView> currentlyUsedHeadPlate;

    public BurgerPattyView(Context context) {
        super(context);
    }

    public BurgerPattyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Ingredient toIngredientWrapper() {
        if (currentRoastLevel == 1)
            return new BurgerPattyRare();
        if (currentRoastLevel == 2)
            return new BurgerPattyMedium();
        return new BurgerPattyRawOrBurned();
    }

    @Override
    protected int drawableId() {
        switch (currentRoastLevel) {
            case 0:
                return R.drawable.burgerpattyraw;
            case 1:
                return R.drawable.burgerpattyrare;
            case 2:
                return R.drawable.burgerpattymedium;
            default:
                return R.drawable.burgerpattyburn;
        }
    }

    public void roast() {
        currentRoastTimes++;
        if (roastLevelSteps.length > currentRoastLevel && roastLevelSteps[currentRoastLevel] < currentRoastTimes) {
            System.out.println("NextStep");
            currentRoastLevel++;
            loadTexture();
            invalidate();
        }
    }

    @Override
    public String name() {
        return "BurgerPatty";
    }

    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        addOnDragAreaListener(new DragAreaSetItemAbove(this) {
            @Override
            protected boolean onDrag(DragEvent event, boolean inArea) {
                if (currentlyUsedHeadPlate != null)
                    return false;
                return super.onDrag(event, inArea);
            }
        }.setUseFilter(true).addFilterTag(ItemFilterTag.Ingredient));
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

    @Override
    public void removeFromParent() {
        super.removeFromParent();
        if (currentlyUsedHeadPlate != null) {
            currentlyUsedHeadPlate.remove(this);
            currentlyUsedHeadPlate = null;
        }
    }

    public void putOnHeadPlate(List<BurgerPattyView> currentlyUsedHeadPlate) {
        this.currentlyUsedHeadPlate = currentlyUsedHeadPlate;
    }
}
