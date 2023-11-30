package com.example.burger42.Game.Restaurant.Counter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.example.burger42.Game.Restaurant.Item.BottomBreadItem;
import com.example.burger42.R;

public class BreadCounterPart extends CounterPartView {

    public BreadCounterPart(Context context) {
        super(context);
    }

    public BreadCounterPart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BreadCounterPart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BreadCounterPart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void init(@Nullable AttributeSet attrs, Context context) {
        super.init(attrs, context);
    }

    @Override
    protected int drawableId() {
        return R.drawable.burgerbreadcorner;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println(event.getRawX() + "   " + event.getRawY());
                System.out.println(event.getX() + "   " + event.getY());
                BottomBreadItem b = new BottomBreadItem(getContext());
                b.setTranslationY(300);
                addView(b);
                System.out.println("ACTION_DOWN");
                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println("ACTION_CANCEL");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("ACTION_MOVE");
                break;
            default:
                System.out.println("Other " + event.getAction());
        }
        return true;
    }

}
