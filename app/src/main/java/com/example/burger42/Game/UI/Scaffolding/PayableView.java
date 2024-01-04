package com.example.burger42.Game.UI.Scaffolding;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.burger42.Game.Recipe;
import com.example.burger42.Game.UI.ItemViews.OrderView;
import com.example.burger42.Ingredients.Ingredient;

import java.util.LinkedList;
import java.util.List;

public abstract class PayableView extends ItemView {
    public PayableView(Context context) {
        super(context);
    }

    public PayableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        addOnDragAreaListener(new OnDragAreaListener() {
            @Override
            protected boolean onDrag(DragEvent event, boolean inArea) {
                if (inArea && event.getAction() == DragEvent.ACTION_DROP) {
                    ((OrderView) event.getLocalState()).serve(self());
                    return true;
                }
                return false;
            }
        }.setUseFilter(true).addFilterTag("Order"));
    }

    private PayableView self() {
        return this;
    }

    @Override
    protected List<ItemFilterTag> itemFilterTags() {
        List<ItemFilterTag> list = super.itemFilterTags();
        list.add(ItemFilterTag.Payable);
        return list;
    }

    public Recipe createRecipe(int itemAboveIndex) {
        Recipe recipe = new Recipe();
        if (hasItemAbove(itemAboveIndex)) {
            ItemView itemView = getItemAbove(itemAboveIndex);
            if (itemView.hasAtLeastOneEqualFilterTag(new BasicDragFilter().addFilterTag(ItemFilterTag.Ingredient.name()))) {
                IngredientView item = (IngredientView) itemView;
                List<Ingredient> list = new LinkedList<>();
                item.addToList(list);
                recipe.list().add(list);
            } else if (itemView.hasAtLeastOneEqualFilterTag(new BasicDragFilter().addFilterTag(ItemFilterTag.Payable.name()))) {
                PayableView item = (PayableView) itemView;
                recipe.addRecipe(item.createRecipe());
            }
        }
        return recipe;
    }

    public abstract Recipe createRecipe();
}
