package com.example.burger42.Fragments;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
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

        View view1 = view.findViewById(R.id.game_move);
        View view12 = view.findViewById(R.id.game_move2);
        View view2 = view.findViewById(R.id.game_aim);
        View view22 = view.findViewById(R.id.game_aim2);
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 1);
                    view.setVisibility(View.INVISIBLE);
                    return true;
                }
                return false;
            }
        };
        view1.setOnTouchListener(onTouchListener);
        view12.setOnTouchListener(onTouchListener);

        View.OnDragListener onDragListener = new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                View dragView = (View) dragEvent.getLocalState();
                if (dragEvent.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                    dragView.setVisibility(View.VISIBLE);
                    dragView.bringToFront();
                }
                if (dragEvent.getAction() == DragEvent.ACTION_DROP) {
                    dragView.setTranslationX(view.getTranslationX());
                    dragView.setTranslationY(view.getTranslationY());
                }
                return true;
            }
        };

        view2.setOnDragListener(onDragListener);
        root.setOnDragListener(onDragListener);
        view22.setOnDragListener(onDragListener);
        return view;
    }
}
