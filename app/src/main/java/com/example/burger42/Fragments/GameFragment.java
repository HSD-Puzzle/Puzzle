package com.example.burger42.Fragments;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.burger42.Game.OnTouchListenerMove;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

public class GameFragment extends ParentFragment {
    private FrameLayout root;

    public GameFragment(MainActivity mainActivity) {
        super(mainActivity);
    }

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        root = view.findViewById(R.id.game_root);
        View view1 = new View(mainActivity);
        view1.setLayoutParams(new FrameLayout.LayoutParams(100, 100));
        view1.setBackgroundColor(R.color.orange700);


        view1.setOnTouchListener(new OnTouchListenerMove());

/*
        view1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                System.out.println("MotionEvent" + motionEvent.getAction());
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    view.setVisibility(View.INVISIBLE);
                    return true;
                }
                return false;
            }
        });

        root.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                System.out.println("Drag " + dragEvent.getAction());
                if (dragEvent.getAction() == DragEvent.ACTION_DRAG_ENDED)
                    view1.setVisibility(View.VISIBLE);
                return true;
            }
        });*/

        root.addView(view1);
        return view;
    }

}
