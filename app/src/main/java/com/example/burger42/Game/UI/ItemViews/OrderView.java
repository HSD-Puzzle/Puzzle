package com.example.burger42.Game.UI.ItemViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.Recipe;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.OnDragAreaListener;
import com.example.burger42.Game.UI.Scaffolding.OnTouchAreaListener;
import com.example.burger42.Game.UI.Scaffolding.PayableView;
import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.Ingredients.Ingredient;
import com.example.burger42.R;

import java.util.List;

public class OrderView extends ItemView {

    private Bitmap nextPage;
    private float ratioNextPage;
    private Bitmap lastPage;
    private float ratioLastPage;

    private int currentPage = 0;

    private Recipe recipeToShow = new Recipe();

    private RestaurantFragment restaurantFragment;

    public OrderView(Context context, Recipe recipe, RestaurantFragment restaurantFragment) {
        super(context);
        this.recipeToShow = recipe;
        this.restaurantFragment = restaurantFragment;
    }

    public OrderView(Context context) {
        super(context);
    }

    public OrderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected List<ItemFilterTag> itemFilterTags() {
        return super.itemFilterTags();
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
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        addOnTouchAreaListener(new OnTouchAreaListener() {
            @Override
            protected boolean onTouch(MotionEvent event) {
                if (recipeToShow.list().size() > currentPage) {
                    currentPage++;
                    invalidate();
                    return true;
                }
                return false;
            }
        }.setRelativeTop(0.15f).setRelativeLeft(0.8f));

        addOnTouchAreaListener(new OnTouchAreaListener() {
            @Override
            protected boolean onTouch(MotionEvent event) {
                if (currentPage > 0) {
                    currentPage--;
                    invalidate();
                    return true;
                }
                return false;
            }
        }.setRelativeTop(0.15f).setRelativeRight(0.2f));

        addOnDragAreaListener(new OnDragAreaListener() {
            @Override
            protected boolean onDrag(DragEvent event, boolean inArea) {
                if (inArea && event.getAction() == DragEvent.ACTION_DROP) {
                    serve((PayableView) event.getLocalState());
                    return true;
                }
                return false;
            }
        }.addFilterTag(ItemFilterTag.Payable).setUseFilter(true));
    }

    public void serve(PayableView payableView) {
        restaurantFragment.serve(recipeToShow, payableView.createRecipe());
        payableView.removeFromParent();
        removeFromParent();
    }


    @Override
    protected int drawableId() {
        return R.drawable.order;
    }

    @Override
    protected float scaling() {
        return 1;//457 / 500f;
    }

    @Override
    public String name() {
        return "Order";
    }

    @Override
    protected void drawItemOnCanvas(Canvas canvas, float xOffset, float yOffset, int referenceHeight, int width, int height) {
        super.drawItemOnCanvas(canvas, xOffset, yOffset, referenceHeight, width, height);
        drawPage(canvas, width, height);
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

    private void drawFirstPage(Canvas canvas, int width, int height) {
        Paint paint = new Paint();
        paint.setColor(0xFF000000);
        paint.setTextSize(height / 10f);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint paintHandWritten = new Paint();
        paintHandWritten.setColor(0xFF000055);
        paintHandWritten.setTextSize(height / 10f);
        paintHandWritten.setTextAlign(Paint.Align.CENTER);
        paintHandWritten.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));

        if (recipeToShow != null) {
            drawArrowsOnCanvas(canvas, width, height, false, recipeToShow.list().size() > 0);

            float currentHeight = height / 10f;
            canvas.drawText(getContext().getString(R.string.order), width / 2f, currentHeight, paint);

            currentHeight += height / 7f;
            canvas.drawText(getContext().getString(R.string.onsite_or_togo), width / 2f, currentHeight, paint);
            currentHeight += height / 10f;


            if (recipeToShow.onSite() == Recipe.PlaceToEat.ONSITE) {
                canvas.drawText(getContext().getString(R.string.onsite), width / 2f, currentHeight, paintHandWritten);
                currentHeight += height / 7f;
            } else if (recipeToShow.onSite() == Recipe.PlaceToEat.TOGO) {
                canvas.drawText(getContext().getString(R.string.togo), width / 2f, currentHeight, paintHandWritten);
                currentHeight += height / 7f;
            }

            canvas.drawText(getContext().getString(R.string.takenat), width / 2f, currentHeight, paint);
            currentHeight += height / 10f;

            canvas.drawText(recipeToShow.orderTakenTime().timeAsText() + getContext().getString(R.string.time_extension), width / 2f, currentHeight, paintHandWritten);
            currentHeight += height / 7f;

            canvas.drawText(getContext().getString(R.string.time_to_deliver), width / 2f, currentHeight, paint);
            currentHeight += height / 10f;

            canvas.drawText(recipeToShow.timeToDeliver().minutes() + " " + getContext().getString(R.string.minutes), width / 2f, currentHeight, paintHandWritten);

        } else {
            canvas.drawText("No Recipe", width / 2f, height / 2f, paint);
        }
    }

    private void drawPage(Canvas canvas, int width, int height) {
        if (currentPage == 0)
            drawFirstPage(canvas, width, height);
        else {

            if (recipeToShow != null) {
                float currentHeight = height / 10f;

                drawArrowsOnCanvas(canvas, width, height, true, recipeToShow.list().size() > currentPage);

                Paint pageNumber = new Paint();
                pageNumber.setColor(0x88000000);
                pageNumber.setTextSize(height / 12f);
                pageNumber.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(currentPage + "/" + (recipeToShow.list().size()), width / 2f, height - height / 20f, pageNumber);

                //Headline
                Paint paint = new Paint();
                paint.setColor(0xFF000000);
                paint.setTextSize(height / 10f);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                canvas.drawText(getContext().getString(R.string.item) + currentPage, width / 2f, currentHeight, paint);
                currentHeight += height / 11f;

                //DrawIngredients
                Paint paintHandWritten = new Paint();
                paintHandWritten.setColor(0xFF000055);
                paintHandWritten.setTextSize(height / 10f);
                paintHandWritten.setTextAlign(Paint.Align.CENTER);
                paintHandWritten.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                List<Ingredient> currentRecipe = recipeToShow.list().get(currentPage - 1);
                for (int i = currentRecipe.size() - 1; i >= 0; i--) {
                    canvas.drawText(currentRecipe.get(i).name(getContext()), width / 2f, currentHeight, paintHandWritten);
                    currentHeight += height / 10f;
                }
            } else {
                Paint paint = new Paint();
                paint.setColor(0xFF000000);
                canvas.drawText("No Recipe", width / 2f, height / 2f, paint);
            }

        }
    }

    @Override
    protected ItemAboveNode[] itemAboveSetUp() {
        return new ItemAboveNode[0];
    }
}
