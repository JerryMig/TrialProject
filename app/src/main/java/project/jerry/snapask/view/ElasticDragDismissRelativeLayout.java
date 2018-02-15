package project.jerry.snapask.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import project.jerry.snapask.R;
import project.jerry.snapask.util.AnimUtils;

/**
 * Created by Migme_Jerry on 2017/5/25.
 */

public class ElasticDragDismissRelativeLayout extends RelativeLayout {

    private final static String TAG = "ElasticDragLayout";

    // configurable attrs
    private float dragDismissDistance = Float.MAX_VALUE;
    private float dragDismissFraction = -1f;
    private float dragDismissScale = 1f;
    private boolean shouldScale = false;
    private float dragElacticity = 1f;

    // state
    private float totalDrag;
    private boolean draggingDown = false;
    private boolean draggingUp = false;

    private List<ElasticDragDismissCallback> callbacks;

    public ElasticDragDismissRelativeLayout(Context context) {
        this(context, null, 0, 0);
    }

    public ElasticDragDismissRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public ElasticDragDismissRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ElasticDragDismissRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ElasticDragDismissLayout, 0, 0);

        if (a.hasValue(R.styleable.ElasticDragDismissLayout_dragDismissDistance)) {
            dragDismissDistance = a.getDimensionPixelSize(R.styleable
                    .ElasticDragDismissLayout_dragDismissDistance, 0);

        } else if (a.hasValue(R.styleable.ElasticDragDismissLayout_dragDismissFraction)) {
            dragDismissFraction = a.getFloat(R.styleable
                    .ElasticDragDismissLayout_dragDismissFraction, dragDismissFraction);
        }

        if (a.hasValue(R.styleable.ElasticDragDismissLayout_dragDismissScale)) {
            dragDismissScale = a.getFloat(R.styleable
                    .ElasticDragDismissLayout_dragDismissScale, dragDismissScale);
            shouldScale = dragDismissScale != 1f;
            Log.d(TAG, "ElasticDragDismissRelativeLayout, shouldScale ? " + shouldScale);
        }

        if (a.hasValue(R.styleable.ElasticDragDismissLayout_dragDismissScale)) {
            dragElacticity = a.getFloat(R.styleable.ElasticDragDismissLayout_dragDismissScale, dragElacticity);
        }
        a.recycle();
    }

    public static abstract class ElasticDragDismissCallback {

        /**
         * Called for each drag event.
         *
         * @param elasticOffset       Indicating the drag offset with elasticity applied i.e. may
         *                            exceed 1.
         * @param elasticOffsetPixels The elastically scaled drag distance in pixels.
         * @param rawOffset           Value from [0, 1] indicating the raw drag offset i.e.
         *                            without elasticity applied. A value of 1 indicates that the
         *                            dismiss distance has been reached.
         * @param rawOffsetPixels     The raw distance the user has dragged
         */
        public void onDrag(float elasticOffset, float elasticOffsetPixels,
                    float rawOffset, float rawOffsetPixels) { }

        /**
         * Called when dragging is released and has exceeded the threshold dismiss distance.
         */
        public void onDragDismissed() { }

    }

    public void addListener(ElasticDragDismissCallback listener) {
        if (callbacks == null) {
            callbacks = new ArrayList<>();
        }
        callbacks.add(listener);
    }

    public void removeListener(ElasticDragDismissCallback listener) {
        if (callbacks != null && callbacks.size() > 0) {
            callbacks.remove(listener);
        }
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        boolean isNeeded = nestedScrollAxes == View.SCROLL_AXIS_VERTICAL;
        Log.d(TAG, "onStartNestedScroll, is needed " + isNeeded + " and nestedScrollAxes is " + nestedScrollAxes);
        return isNeeded;
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        // We're only interested in dyUnconsumed.
        dragYScroll(dyUnconsumed);
    }

    @Override
    public void onStopNestedScroll(View child) {
        Log.d(TAG, "onStopNestedScroll is called");
        if (Math.abs(totalDrag) > dragDismissDistance) {
            dispatchDismissCallback();
        } else {
            // Just animates back to original position
            animate()
                    .translationY(0f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(200L)
                    .setInterpolator(AnimUtils.getFastOutSlowInInterpolator(getContext()))
                    .setListener(null)
                    .start();
            totalDrag = 0;
            draggingDown = draggingUp = false;
            dispatchDragCallback(0f, 0f, 0f, 0f);
        }
    }

    private void dragYScroll(int scroll) {
        if (scroll == 0) return;

        totalDrag += scroll;

        /**
         * This is to decide the pivot point for scaling
         * Setting the pivot only happens once in case users reverse his scroll direction
         * */
        if (scroll < 0 && !draggingUp && !draggingDown) {
            draggingDown = true;
            if (shouldScale) setPivotY(getHeight());
        } else if (scroll > 0 && !draggingDown && !draggingUp) {
            draggingUp = true;
            if (shouldScale) setPivotY(0f);
        }

        // how far have we dragged relative to the distance to perform a dismiss
        // (0–1 where 1 = dismiss distance). Decreasing logarithmically as we approach the limit
        float dragFraction = (float) Math.log10(1 + (Math.abs(totalDrag) / dragDismissDistance));

        // calculate the desired translation given the drag fraction
        float dragTo = dragFraction * dragDismissDistance * 0.5f;

        if (draggingUp) {
            // as we use the absolute magnitude when calculating the drag fraction, need to
            // re-apply the drag direction
            dragTo *= -1;
        }

        /**
         * There's a catch in setTranslationY, when we drag view over the original point, the view goes in the opposite direction.
         * */
        setTranslationY(dragTo);
        Log.d(TAG, "dragYScroll, dragTo is : " + dragTo + " and total drag is : " + totalDrag);

        if (shouldScale) {
            final float scale = 1 - ((1 - dragDismissScale) * dragFraction);
            setScaleX(scale);
            setScaleY(scale);
        }

        // if we've reversed direction and gone past the settle point then clear the flags to
        // allow the list to get the scroll events & reset any transforms
        if ((draggingDown && totalDrag >= 0)
                || (draggingUp && totalDrag <= 0)) {
            totalDrag = dragTo = dragFraction = 0;
            draggingUp = draggingDown = false;
            setTranslationY(0f);
            setScaleX(1f);
            setScaleY(1f);
        }

//        Log.d(TAG, "dragYScroll, total drag is : " + totalDrag + " dragDismissDistance is : " + dragDismissDistance);
        dispatchDragCallback(dragFraction, dragTo,
                Math.min(1f, Math.abs(totalDrag) / dragDismissDistance), totalDrag);
    }

    private void dispatchDragCallback(float elasticOffset, float elasticOffsetPixels,
                                      float rawOffset, float rawOffsetPixels) {
        if (callbacks != null && !callbacks.isEmpty()) {
            for (ElasticDragDismissCallback callback : callbacks) {
                callback.onDrag(elasticOffset, elasticOffsetPixels,
                        rawOffset, rawOffsetPixels);
            }
        }
    }

    private void dispatchDismissCallback() {
        if (callbacks != null && !callbacks.isEmpty()) {
            for (ElasticDragDismissCallback callback : callbacks) {
                callback.onDragDismissed();
            }
        }
    }


}
