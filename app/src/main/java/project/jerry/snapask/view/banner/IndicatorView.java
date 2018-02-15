package project.jerry.snapask.view.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import project.jerry.snapask.R;

/**
 * Created by Jerry on 2018/1/25.
 */

public class IndicatorView extends RelativeLayout {

    private ImageView[] mDots;
    private WormAnimationView mWormAnimationView;

    private int mCurrentAdjustedIndex = 0;
    private int radius = 20;
    private int mMargin = radius * 3;

    public IndicatorView(Context context) {
        super(context);
    }

    public void addDots(int count) {
        if (mDots == null) {
            mDots = new ImageView[count];
        }

        for (int i = 0; i < count; i++) {
            ImageView imageView = getCircle(i);
            addView(imageView);
            mDots[i] = imageView;
        }

        addAnimationView();
        if (count >= 1) {
            mWormAnimationView.drawOneCircle(mDots[0]);
        }
    }

    private ImageView getCircle(int index) {
        ImageView dot = new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.height = radius * 2;
        lp.width = radius * 2;
        lp.leftMargin = mMargin * index;
        dot.setBackground(getResources().getDrawable(R.drawable.grey_dot));
        dot.setLayoutParams(lp);
        return dot;
    }

    private void addAnimationView() {
        if (mWormAnimationView == null) {
            mWormAnimationView = new WormAnimationView(getContext());
        } else {
            removeView(mWormAnimationView);
        }
        RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mWormAnimationView, params);
    }

    public void toggleOn(int position) {
        if (mDots != null) {
            int adjusted = adjustIndex(position);
            if (mCurrentAdjustedIndex == mDots.length - 1 && adjusted == 0) {
                // from last index to first, no animation needed
                mWormAnimationView.drawOneCircle(mDots[0]);
            } else if (mCurrentAdjustedIndex == 0 && adjusted == mDots.length - 1) {
                // from first index to last, no animation needed
                mWormAnimationView.drawOneCircle(mDots[mDots.length - 1]);
            } else {
                // apply animation
                scaleToNext(adjusted, adjusted > mCurrentAdjustedIndex);
            }
            mCurrentAdjustedIndex = adjustIndex(position);
        }
    }

    private int adjustIndex(int position) {
        if (mDots != null && mDots.length > 1) {
            if (position >= mDots.length) {
                return position % mDots.length;
            } else {
                return position;
            }
        }
        return 0;
    }

    private void scaleToNext(int next, boolean forward) {
        ImageView previousImage = mDots[mCurrentAdjustedIndex];
        ImageView nextImage = mDots[next];
        if (forward) {
            mWormAnimationView.forward(previousImage, nextImage);
        } else {
            mWormAnimationView.backward(previousImage, nextImage);
        }
    }

}
