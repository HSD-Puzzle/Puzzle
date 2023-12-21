package com.example.burger42.Game.UI.ItemViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.Scaffolding.DragAreaSetItemAbove;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.OnDragAreaListener;
import com.example.burger42.Game.UI.Scaffolding.OnTouchAreaListener;
import com.example.burger42.R;

public class BottomBreadView extends ItemView {

    @Override
    protected ItemFilterTag[] itemFilterTags() {
        return new ItemFilterTag[]{ItemFilterTag.Ingredient};
    }

    public BottomBreadView(Context context) {
        super(context);
    }

    public BottomBreadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
