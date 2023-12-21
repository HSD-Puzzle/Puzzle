package com.example.burger42.Game.UI.Scaffolding;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

public class HorizontalScrollContentView extends RelativeLayout {

    public HorizontalScrollContentView(Context context) {
        super(context);
    }

    public HorizontalScrollContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalScrollContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HorizontalScrollContentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ((HorizontalScrollView) getParent()).scrollTo((w - ((HorizontalScrollView) getParent()).getWidth()) / 2, 0);
    }
}
