package project.jerry.snapask.view.banner;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Jerry on 2018/1/29.
 */

public class WormAnimationView extends LinearLayout {

    private final int ANIMATION_DURATION = 150;
    private final int RADIUS = 20;
    private final int DIAMETER = RADIUS * 2;
    private Paint mPaint = new Paint();

    // For calling drawRoundRect in onDraw().
    private final RectF mRectf = new RectF(0, 0, 0, DIAMETER);

    // For calling drawCircle in onDraw().
    private boolean mIsDrawOne;
    private int mOneCircleX;

    public WormAnimationView(Context context) {
        super(context);
        setWillNotDraw(false);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.YELLOW);
        mPaint.setAntiAlias(true);
    }

    // This is forward moving animation like from index 0 to in index 1.
    public void forward(ImageView previousImage, ImageView nextImage) {
        playAnimation(previousImage, nextImage, true);
    }

    // This is backward moving animation like from index 1 to in index 0.
    public void backward(ImageView previousImage, ImageView nextImage) {
        playAnimation(previousImage, nextImage, false);
    }

    private int getViewCenter(ImageView imageView) {
        if (imageView != null) {
            return imageView.getLeft() + RADIUS;
        }
        return -1;
    }

    private void playAnimation(ImageView previousImage, ImageView nextImage, boolean leftToRight) {
        int previousCenter = getViewCenter(previousImage);
        int nextCenter = getViewCenter(nextImage);
        if (previousCenter != -1 && nextCenter != -1) {
            ValueAnimator forwardAnimation = getValueAnimator(previousCenter, nextCenter, leftToRight, false);
            ValueAnimator reverseAnimator = getValueAnimator(previousCenter, nextCenter, leftToRight, true);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(forwardAnimation, reverseAnimator);
            animatorSet.start();
        }
    }

    private ValueAnimator getValueAnimator(final int previousCenter, final int nextCenter,
                                           final boolean leftToRight, final boolean reverse) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(previousCenter, nextCenter);
        valueAnimator.setDuration(ANIMATION_DURATION);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                setRectLeft(previousCenter, nextCenter, animatedValue, leftToRight, reverse);
                invalidate();
            }
        });
        return valueAnimator;
    }

    /**
     * This is to prepare rectF for the OnDraw() method.
     *
     * @param previousCenter - coordinate x of the circle's center Animation is moving from
     * @param nextCenter - coordinate x of the circle's center Animation is moving to
     * @param animatedValue - value onAnimationUpdate() provides
     * @param leftToRight - true if Animation is moving from left to right.
     *                    For example, its moving from index 0 to index 1.
     * @param reverse - true if Animation Worm is extending; false when its shrinking.
     */
    private void setRectLeft(int previousCenter, int nextCenter, int animatedValue,
                             boolean leftToRight, boolean reverse) {
        if (leftToRight) {
            if (!reverse) {
                mRectf.left = previousCenter - RADIUS;
                mRectf.right = animatedValue + RADIUS;
            } else {
                mRectf.left = animatedValue - RADIUS;
                mRectf.right = nextCenter + RADIUS;
            }
        } else {
            if (!reverse) {
                mRectf.left = animatedValue - RADIUS;
                mRectf.right = previousCenter + RADIUS;
            } else {
                mRectf.left = nextCenter - RADIUS;
                mRectf.right = animatedValue + RADIUS;
            }
        }
    }

    /**
     * This is to force drawing only one circle.
     *
     * @param imageView - coordinate x of the circle's center Animation is moving from
     */
    public void drawOneCircle(ImageView imageView) {
        if (imageView != null) {
            mIsDrawOne = true;
            mOneCircleX = imageView.getLeft() + RADIUS;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIsDrawOne) {
            canvas.drawCircle(mOneCircleX, RADIUS, RADIUS, mPaint);
            mIsDrawOne = false;
        } else {
            canvas.drawRoundRect(mRectf, RADIUS, RADIUS, mPaint);
        }
    }

}
