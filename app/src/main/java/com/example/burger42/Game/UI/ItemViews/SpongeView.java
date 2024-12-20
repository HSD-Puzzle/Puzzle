package com.example.burger42.Game.UI.ItemViews;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.Scaffolding.DragAreaSetItemAbove;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.R;

import java.util.List;

/**
 * The graphical representation of the sponge
 */
public class SpongeView extends ItemView {

    @Override
    protected List<ItemFilterTag> itemFilterTags() {
        List<ItemFilterTag> list = super.itemFilterTags();
        list.add(ItemFilterTag.Tool);
        return list;
    }

    public SpongeView(Context context) {
        super(context);
    }

    public SpongeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int drawableId() {
        return R.drawable.sponge;
    }

    @Override
    public String name() {
        return "Sponge";
    }

    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
    }

    @Override
    protected float scaling() {
        return 170f / 500;
    }
}
