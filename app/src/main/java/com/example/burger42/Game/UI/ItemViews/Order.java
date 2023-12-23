package com.example.burger42.Game.UI.ItemViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.burger42.Game.Recipe;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.R;

public class Order extends ItemView {

    private Bitmap nextPage;
    private float ratioNextPage;
    private Bitmap lastPage;
    private float ratioLastPage;

    private Recipe recipeToShow = new Recipe();

    public Order(Context context) {
        super(context);
    }

    public Order(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected ItemFilterTag[] itemFilterTags() {
        return new ItemFilterTag[0];
    }

    @Override
    protected void beforeInit(Context context, @Nullable AttributeSet attrs) {
        super.beforeInit(context, attrs);
        nextPage = BitmapFactory.decodeResource(getResources(), R.drawable.arrowr);
        lastPage = BitmapFactory.decodeResource(getResources(), R.drawable.arrowl);
        ratioNextPage = (float) nextPage.getWidth() / (float) nextPage.getHeight();
        ratioLastPage = (float) lastPage.getWidth() / (float) lastPage.getHeight();
    }

    @Override
    protected int drawableId() {
        return R.drawable.order;
    }

    @Override
    protected float scaling() {
        return 457 / 500f;
    }

    @Override
    public String name() {
        return "Order";
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void drawItemOnCanvas(Canvas canvas, float xOffset, float yOffset, int referenceHeight, int width, int height) {
        super.drawItemOnCanvas(canvas, xOffset, yOffset, referenceHeight, width, height);
        drawFirstPage(canvas, width, height);
    }


    private void drawArrowsOnCanvas(Canvas canvas, int width, int height, boolean last, boolean next) {
        float arrowMargin = height / 40f;
        int size = height / 8;

        if (next) {
            Bitmap scaledNextBitmap = Bitmap.createScaledBitmap(nextPage, (int) (size * ratioNextPage), size, false);
            canvas.drawBitmap(scaledNextBitmap, width - scaledNextBitmap.getWidth() - arrowMargin, height - scaledNextBitmap.getHeight() - arrowMargin, null);
        }
        if (last) {
            Bitmap scaledLastBitmap = Bitmap.createScaledBitmap(lastPage, (int) (size * ratioLastPage), size, false);
            canvas.drawBitmap(scaledLastBitmap, arrowMargin, height - scaledLastBitmap.getHeight() - arrowMargin, null);
        }
    }

    @SuppressLint("ResourceAsColor")
    private void drawFirstPage(Canvas canvas, int width, int height) {
        Paint paint = new Paint();
        paint.setColor(R.color.black);
        paint.setTextSize(height / 10f);
        paint.setTextAlign(Paint.Align.CENTER);
        if (recipeToShow != null) {
            drawArrowsOnCanvas(canvas, width, height, false, true);

            float currentHeight = height / 10f;
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText(getContext().getString(R.string.order), width / 2f, currentHeight, paint);

            paint.setTextSize(height / 12f);

            currentHeight += height / 7f;
            canvas.drawText(getContext().getString(R.string.onsite_or_togo), width / 2f, currentHeight, paint);
            currentHeight += height / 10f;

            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
            if (recipeToShow.onSite() == Recipe.PlaceToEat.ONSITE) {
                canvas.drawText(getContext().getString(R.string.onsite), width / 2f, currentHeight, paint);
                currentHeight += height / 7f;
            } else if (recipeToShow.onSite() == Recipe.PlaceToEat.TOGO) {
                canvas.drawText(getContext().getString(R.string.togo), width / 2f, currentHeight, paint);
                currentHeight += height / 7f;
            }


            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText(getContext().getString(R.string.takenat), width / 2f, currentHeight, paint);
            currentHeight += height / 10f;

            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
            canvas.drawText(recipeToShow.orderTakenTime().timeAsText() + getContext().getString(R.string.time_extension), width / 2f, currentHeight, paint);
            currentHeight += height / 7f;

            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText(getContext().getString(R.string.time_to_deliver), width / 2f, currentHeight, paint);
            currentHeight += height / 10f;

            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
            canvas.drawText(recipeToShow.timeToDeliver().minutes() + " " + getContext().getString(R.string.minutes), width / 2f, currentHeight, paint);

        } else {
            canvas.drawText("No Recipe", width / 2f, height / 2f, paint);
        }
    }

    @Override
    protected ItemAboveNode[] itemAboveSetUp() {
        return new ItemAboveNode[0];
    }
}
