package project.jerry.snapask.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import project.jerry.snapask.R;
import project.jerry.snapask.controller.activity.LauncherActivity;

/**
 * Created by Jerry on 2017/11/17.
 */

public class ToolTip {


    public static final int ABOVE_ANCHOR = 1;
    public static final int BELOW_ANCHOR = 2;

    @IntDef({
            ABOVE_ANCHOR,
            BELOW_ANCHOR
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Position {}

    public static class Builder {

        private PopupWindow mPopupWindow;
//        private View.OnClickListener mClickCallbackListener;
//        private View.OnClickListener mOnclickListener;
        private @Position int mPosition = BELOW_ANCHOR;
        private Integer mDuration = null;

        private View mPopupView;
        private RelativeLayout mMainContainer;
        private TextView mTextView;
        private ImageView mArrow;

        /******************************************
         *              Public methods
         * ****************************************/

        public Builder(Context context, @Position int position) {
            inflateView(context, position);
            setupPopupWindow();
        }

//        public ToolTip.Builder setText(@StringRes int source) {
//            if (mTextView != null) {
//                mTextView.setText(SnapaskApplication.getContext().getString(source));
//                mPopupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//                mPopupWindow.setWidth(mPopupView.getMeasuredWidth());
//            }
//            return this;
//        }

        public ToolTip.Builder setText(String text) {
            if (mTextView != null) {
                mTextView.setText(text);
//                mPopupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//                mPopupWindow.setWidth(mPopupView.getMeasuredWidth());
            }
            return this;
        }

//        public ToolTip.Builder setClickListener(View.OnClickListener listener) {
//            mClickCallbackListener = listener;
//            return this;
//        }

        public ToolTip.Builder setDuration(int duration) {
            mDuration = duration;
            return this;
        }

        public void show(View anchorView) {
            if (mPopupWindow != null) {
                switch (mPosition) {
                    case ABOVE_ANCHOR:
                        mPopupWindow.setAnimationStyle(R.style.tooltipAnimation);
                        showAsRiseUp(anchorView);
                        break;
                    case BELOW_ANCHOR:
                        mPopupWindow.setAnimationStyle(R.style.tooltipAnimation);
                        showAsDropDown(anchorView);
                        break;
                }
            }
        }

        /******************************************
         *              Private methods
         * ****************************************/

        private int getViewResource(int position) {
            mPosition = position;
            switch (position) {
                case ABOVE_ANCHOR:
                    return R.layout.view_tool_tip_down_arrow;
                case BELOW_ANCHOR:
                    return R.layout.view_tool_tip_up_arrow;
            }
            return R.layout.view_tool_tip_up_arrow;
        }

        private void inflateView(Context context, int position) {
            LayoutInflater inflater = LayoutInflater.from(context);
            mPopupView = inflater.inflate(getViewResource(position), null, false);
            mMainContainer = (RelativeLayout) mPopupView.findViewById(R.id.main_container);
            mTextView = (TextView) mPopupView.findViewById(R.id.text);
            mArrow = (ImageView) mPopupView.findViewById(R.id.arrow_view);
//            mTextView.setMaxWidth(UIUtils.getScreenWidthPx(context) - (int) UIUtils.convertDpToPx(32));

//            mOnclickListener = (View v) -> {
//                if (mPopupWindow != null) {
//                    mPopupWindow.dismiss();
//                    if (mClickCallbackListener != null) {
//                        mClickCallbackListener.onClick(v);
//                    }
//                }
//            };
//            mMainContainer.setOnClickListener(mOnclickListener);
        }

        private void setupPopupWindow() {
            if (mPopupView != null) {
                mPopupWindow = new PopupWindow(mPopupView);
                mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                mPopupWindow.setOutsideTouchable(true);
            }
        }

        // There's a left margin already for the whole tool tip view,
        // so this margin is an offset from the left end of the coloured view.
        private void setArrowLeftMargin(View view, int[] coordinates, int offsetX) {
            if (mArrow != null) {
                // 24 is the left margin of tooltip (16dp) plus the width of arrow (8dp)
                float left = coordinates[0] + (view.getWidth() / 2) - convertDpToPx(24) - offsetX;
                mArrow.setX(left);
            }
        }

        private void showAsDropDown(View anchorView) {
            if (anchorView != null) {
                int[] coordinates = {0, 0};
                anchorView.getLocationOnScreen(coordinates);
                int absoluteTop = coordinates[1];
                int offsetX = computeOffsetX(anchorView);
                int offsetY = absoluteTop + anchorView.getHeight();
                setArrowLeftMargin(anchorView, coordinates, offsetX);
                mPopupWindow.showAtLocation(anchorView, Gravity.TOP | Gravity.LEFT, offsetX, offsetY);
            }
        }

        private void showAsRiseUp(View anchorView) {
            if (anchorView != null) {
                int[] coordinates = {0, 0};
                anchorView.getLocationOnScreen(coordinates);
                int absoluteTop = coordinates[1];
                int offsetX = computeOffsetX(anchorView);
                int offsetY = LauncherActivity.getScreenHeight() - absoluteTop;
                setArrowLeftMargin(anchorView, coordinates, offsetX);
                mPopupWindow.showAtLocation(anchorView, Gravity.BOTTOM | Gravity.LEFT, offsetX, offsetY);
            }
        }

        private int computeOffsetX(View anchorView) {
            mPopupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int windowWidth = LauncherActivity.getScreenWidth();
            int popupWidth = mPopupView.getMeasuredWidth();
            int offsetX = 0;
            if (popupWidth < windowWidth) { // Adjust popup location
                int[] coordinates = {0, 0};
                anchorView.getLocationOnScreen(coordinates);
                int popupCenter = popupWidth / 2;
                int anchorViewCenter = coordinates[0] + anchorView.getWidth()/2;
                if (popupCenter < anchorViewCenter && popupCenter + anchorViewCenter < windowWidth) {
                    offsetX = anchorViewCenter - popupCenter;
                } else if (popupCenter + anchorViewCenter >= windowWidth) {
                    offsetX = windowWidth - popupWidth;
                }
            }
            return offsetX;
        }

    }

    public static float convertDpToPx(int dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

}
