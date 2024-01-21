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
import com.example.burger42.Game.Time;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.OnDragAreaListener;
import com.example.burger42.Game.UI.Scaffolding.OnTouchAreaListener;
import com.example.burger42.Game.UI.Scaffolding.PayableItemView;
import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.Ingredients.Ingredient;
import com.example.burger42.R;

import java.util.List;

/**
 * The graphical representation of the order
 */
public class OrderView extends ItemView {

    /**
     * The image of the arrow to the next page
     */
    private Bitmap nextPage;
    /**
     * the aspect ratio of the arrow to the next page
     */
    private float ratioNextPage;
    /**
     * The image of the arrow to the last page
     */
    private Bitmap lastPage;
    /**
     * the aspect ratio of the arrow to the last page
     */
    private float ratioLastPage;
    /**
     * the currently shown page number
     */
    private int currentPage = 0;
    /**
     * The Recipe, that this order shows
     */
    private Recipe recipeToShow = new Recipe(new Time(8, 12), new Time(0, 15));
    /**
     * the restaurant fragment, where the done order needs to be served
     */
    private RestaurantFragment restaurantFragment;

    /**
     * @param recipe             the Recipe, that this order shows
     * @param restaurantFragment the restaurant fragment, where the done order needs to be served
     */
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

    /**
     * the textures of the arrows will be loaded
     *
     * @param context the context of this app, can be used to load the correct language
     * @param attrs   the AttributeSet that is used in xml
     */
    @Override
    protected void beforeInit(Context context, @Nullable AttributeSet attrs) {
        super.beforeInit(context, attrs);
        nextPage = BitmapFactory.decodeResource(getResources(), R.drawable.arrowr);
        lastPage = BitmapFactory.decodeResource(getResources(), R.drawable.arrowl);
        ratioNextPage = (float) nextPage.getWidth() / (float) nextPage.getHeight();
        ratioLastPage = (float) lastPage.getWidth() / (float) lastPage.getHeight();
    }

    /**
     * adds listeners, that allows switching between the pages
     * and adds a listener, that serves an order, if an payable will dragged on it.
     *
     * @param context the context of this app, can be used to load the correct language
     * @param attrs   the AttributeSet that is used in xml
     */
    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        // next page
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

        //last page
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

        //serve
        addOnDragAreaListener(new OnDragAreaListener() {
            @Override
            protected boolean onDrag(DragEvent event, boolean inArea) {
                if (inArea && event.getAction() == DragEvent.ACTION_DROP) {
                    serve((PayableItemView) event.getLocalState());
                    return true;
                }
                return false;
            }
        }.addFilterTag(ItemFilterTag.Payable).setUseFilter(true));
    }

    /**
     * serves this order with the given payable
     *
     * @param payableView the item that should be served
     */
    public void serve(PayableItemView payableView) {
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

    /**
     * draws the background image and the currently active page an a canvas
     *
     * @param canvas          the canvas to draw the view on.
     * @param xOffset         the x offset where to draw the view on the canvas.
     * @param yOffset         the y offset where to draw the view on the canvas.
     * @param referenceHeight the referenceheight used to draw the items the correct size.
     * @param width           the width of the view size
     * @param height          the height of the view size
     */
    @Override
    protected void drawItemOnCanvas(Canvas canvas, float xOffset, float yOffset, int referenceHeight, int width, int height) {
        super.drawItemOnCanvas(canvas, xOffset, yOffset, referenceHeight, width, height);
        drawPage(canvas, width, height);
    }

    /**
     * draws the arrows on the canvas
     *
     * @param canvas the canvas to draw on
     * @param width  the width of the canvas
     * @param height the height of the canvas
     * @param last   true if the last arrow should be drawn
     * @param next   true if the next arrow should be drawn
     */
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

    /**
     * draws the first page of the order
     *
     * @param canvas the canvas to draw on
     * @param width  the width of the canvas
     * @param height the height of the canvas
     */
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

    /**
     * draws the page information on the canvas
     *
     * @param canvas the canvas to draw on
     * @param width  the width of the canvas
     * @param height the height of the canvas
     */
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
